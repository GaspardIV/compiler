package latte.Internal;

import latte.Absyn.*;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.util.*;
import java.util.stream.Collectors;

public class LatteClass {
    public ClDefExt classDef;

    public final ListClMember flatMembers;
    public Set<String> inheritedClasses = new HashSet<>();
    public boolean inheritanceInitialized;

    private final List<String> classesTopDownOrderedList = new ArrayList<>();
    private final Map<String, List<ClMethod>> classToMethods = new HashMap<>();
    private final Map<String, List<ClField>> classToFields = new HashMap<>();

    public LatteClass(ClDefExt p) {
        classDef = p;
        flatMembers = new ListClMember();
        flatMembers.addAll(((ClBlk) classDef.clblock_).listclmember_);
    }

    public void initInheristance(Environment avaibleClasses) {
        if (this.inheritanceInitialized) {
            return;
        }
        Set<String> visited = new HashSet<>();
        String extendsCl = classDef.ident_2;
        visited.add(classDef.ident_1);
        while (extendsCl != null) {
            LatteClass next = avaibleClasses.getClassDef(extendsCl);
            if (next == null) {
                throw new SemanticError.ClassNotDeclared(classDef.line_num, extendsCl);
            }
            if (visited.contains(next.classDef.ident_1)) {
                throw new SemanticError.InheritanceLoop(classDef.line_num, next.classDef.ident_1);
            }

            extendsCl = next.classDef.ident_2;
            visited.add(next.classDef.ident_1);
        }
        this.inheritedClasses = visited;

        if (classDef.ident_2 != null) {
            LatteClass superClass = avaibleClasses.getClassDef(classDef.ident_2);
            superClass.initInheristance(avaibleClasses);
            this.classesTopDownOrderedList.addAll(superClass.classesTopDownOrderedList);
            this.classToFields.putAll(superClass.classToFields);
            this.classToMethods.putAll(superClass.classToMethods);
            ((ClBlk) classDef.clblock_).listclmember_.addAll(((ClBlk) superClass.classDef.clblock_).listclmember_);
        }
        this.classesTopDownOrderedList.add(classDef.ident_1);
        this.classToFields.put(classDef.ident_1, this.flatMembers.stream().filter(x -> x instanceof ClField).map(x -> (ClField) x).collect(Collectors.toList()));
        this.classToFields.get(classDef.ident_1).addAll(this.flatMembers.stream().filter(x -> x instanceof ClFields).map(x -> (ClFields) x).flatMap(x -> x.listclfielditem_.stream().map(f -> new ClField(x.type_, ((ClFieldItemNoInit) f).ident_))).collect(Collectors.toList()));
        this.classToMethods.put(classDef.ident_1, this.flatMembers.stream().filter(x -> x instanceof ClMethod).map(x -> (ClMethod) x).collect(Collectors.toList()));
        this.inheritanceInitialized = true;
    }

    public boolean doesExtends(LatteClass latteClass) {
        return inheritedClasses.contains(latteClass.classDef.ident_1);
    }

    public ClMethod getMethod(String ident_) {
        ListClMember listClMember = ((ClBlk) classDef.clblock_).listclmember_;
        for (ClMember clMember : listClMember) {
            if (clMember.getClass() == ClMethod.class) {
                ClMethod method = (ClMethod) clMember;
                if (Objects.equals(method.ident_, ident_)) {
                    return method;
                }
            }
        }
        return null;
    }

    public ClField getField(String ident_) {
        ListClMember listClMember = ((ClBlk) classDef.clblock_).listclmember_;

        for (ClMember clMember : listClMember) {
            if (clMember.getClass() == ClField.class) {
                ClField field = (ClField) clMember;
                if (Objects.equals(field.ident_, ident_)) {
                    return field;
                }
            }
            if (clMember.getClass() == ClFields.class) {
                ClFields field = (ClFields) clMember;
                for (int j = 0; j < field.listclfielditem_.size(); j++) {
                    ClFieldItemNoInit fieldItem = (ClFieldItemNoInit) field.listclfielditem_.get(j);
                    if (Objects.equals(fieldItem.ident_, ident_)) {
                        ClField clField = new ClField(field.type_, fieldItem.ident_);
                        clField.col_num = field.col_num;
                        clField.line_num = field.line_num;
                        clField.offset = field.offset;
                        return clField;
                    }
                }
            }
        }
        return null;
    }

    public List<String> getClassesTopDownOrderedList() {
        return classesTopDownOrderedList;
    }

    public Map<String, List<ClMethod>> getClassToMethods() {
        return classToMethods;
    }

    public Map<String, List<ClField>> getClassToFields() {
        return classToFields;
    }

}
