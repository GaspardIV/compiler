package latte.backend.program.global.classes;

import latte.Absyn.Class;
import latte.Absyn.*;
import latte.Internal.ClField;
import latte.Internal.LatteClass;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.Internal.MethodPointerType;
import latte.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLVMClass extends Scope {

    private final List<ClField> fields;

    private final LLVMClassVTable classVTable;
    private final LLVMClassType classType;
    private final LLVMClassConstructor constructor;
    private final HashMap<String, LLVMClassMethod> methods;

    public LLVMClass(String contextName, Scope parent, LatteClass latteClass) {
        super(contextName, parent);
        List<String> classesTopDownOrderedList = latteClass.getClassesTopDownOrderedList();
        Map<String, List<ClField>> classToFields = latteClass.getClassToFields();
        Map<String, List<ClMethod>> classToMethod = latteClass.getClassToMethods();

        this.fields = new ArrayList<>();
        for (String className : classesTopDownOrderedList) {
            fields.addAll(classToFields.get(className));
        }

        this.classType = new LLVMClassType(this.getName(), this.fields);
        this.methods = new HashMap<>();
        HashMap<String, LLVMClassMethod> methodPointers = new HashMap<>();
        List<String> orderedMethods = new ArrayList<>();
        for (String className : classesTopDownOrderedList) {
            for (ClMethod method : classToMethod.get(className)) {
                if (!orderedMethods.contains(method.ident_)) {
                    orderedMethods.add(method.ident_);
                }

                List<Variable> variables = new ArrayList<>();
                variables.add(new Variable("self", new Class(className), this));

                for (Arg ar : method.listarg_) {
                    Ar arg = (Ar) ar;
                    variables.add(new Variable(arg.ident_, arg.type_, this));
                }

                LLVMClassMethod llvmMethod = new LLVMClassMethod(classMethodLabel(className, method.ident_), method.type_, variables, ((Blk) method.block_).liststmt_, this);
                if (className.equals(this.getName())) { // not inherited
                    methods.put(method.ident_, llvmMethod);
                }

                methodPointers.put(method.ident_, llvmMethod);
            }
        }
        this.classVTable = new LLVMClassVTable(getName(), orderedMethods, methodPointers);
        this.constructor = new LLVMClassConstructor(this.getName(), this.fields, orderedMethods.size());

    }

    public int getFieldIndex(String name) {
        for (int i = fields.size() - 1; i >= 0; i--) {
            if (fields.get(i).ident_.equals(name)) {
                return i + 1; // +1 for vtable
            }
        }
        throw new RuntimeException("Field " + name + " not found in class " + this.getName());
    }

    public void convertToLLVM() {
        for (Map.Entry<String, LLVMClassMethod> method : this.methods.entrySet()) {
            methods.get(method.getKey()).convertToQuadruples();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(constructor);
        for (LLVMClassMethod method : methods.values()) {
            sb.append(method);
        }
        return sb.toString();
    }
    public Type getFieldType(String ident_) {
        for (ClField field : fields) {
            if (field.ident_.equals(ident_)) {
                return field.type_;
            }
        }
        throw new RuntimeException("Field " + ident_ + " not found in class " + this.getName());
    }

    public int getSize() {
        int sum = 0;
        for (ClField field : fields) {
            sum += Utils.getLLVMTypeSize(field.type_);
        }
        return sum;
    }

    public static String classMethodLabel(String className, String methodName) {
        return className + "." + methodName;
    }

    public Function getMethod(String method) {
        return classVTable.getMethod(method);
    }

    public int getMethodIndex(String methodName) {
        return classVTable.getMethodIndex(methodName);
    }

    public MethodPointerType getMethodType(String methodName) {
        return classVTable.getMethodType(methodName);
    }

    public LLVMClassVTable getClassVTable() {
        return classVTable;
    }

    public LLVMClassType getClassType() {
        return classType;
    }
}

