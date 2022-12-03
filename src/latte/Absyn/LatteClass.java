package latte.Absyn;

import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LatteClass {
    public ClDefExt classDef;
    public Set<String> inheritedClasses = new HashSet<>();
    public boolean inheritanceInitialized;

    public LatteClass(ClDefExt p) {
        classDef = p;
    }

    public void initInheristance(Environment avaibleClasses)  {
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
            ((ClBlk) classDef.clblock_).listclmember_.addAll(((ClBlk) superClass.classDef.clblock_).listclmember_);
        }
        this.inheritanceInitialized = true;
    }

    public boolean doesExtends(LatteClass latteClass) {
        return inheritedClasses.contains(latteClass.classDef.ident_1);
    }

    public ClMethod getMethod(String ident_) {
        ListClMember listClMember = ((ClBlk)classDef.clblock_).listclmember_;
        for (int i = 0; i < listClMember.size(); i++) {
            if (listClMember.get(i).getClass() == ClMethod.class) {
                ClMethod method = (ClMethod) listClMember.get(i);
                if (Objects.equals(method.ident_, ident_)) {
                    return method;
                }
            }
        }
        return null;
    }

    public ClField getField(String ident_) {
        ListClMember listClMember = ((ClBlk)classDef.clblock_).listclmember_;

        for (int i = 0; i < listClMember.size(); i++) {
            if (listClMember.get(i).getClass() == ClField.class) {
                ClField field = (ClField) listClMember.get(i);
                if (Objects.equals(field.ident_, ident_)) {
                    return field;
                }
            }
            if (listClMember.get(i).getClass() == ClFields.class) {
                ClFields field = (ClFields) listClMember.get(i);
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

}
