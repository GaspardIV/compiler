package latte.backend.program.global.classes;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.Internal.ClField;
import latte.Internal.LatteClass;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLVMClass extends Scope {

    private final List<ClField> fields;
    private final LLVMClassType classType;
    private final LLVMClassConstructor constructor;
    private final HashMap<String, LLVMClassMethod> methods;

    public LLVMClass(String contextName, Scope parent, LatteClass latteClass) {
        super(contextName, parent);
        this.fields = latteClass.getFields();
        this.classType = new LLVMClassType(this.getName(), latteClass.getFields());
        this.constructor = new LLVMClassConstructor(this.getName(), latteClass.getFields());
        this.methods = new HashMap<>();
        for (ClMethod method : latteClass.getMethods()) {
            List<Variable> variables = new ArrayList<>();
            variables.add(new Variable("self", new Class(getName()), this));
            for (Arg ar : method.listarg_) {
                Ar arg = (Ar) ar;
                variables.add(new Variable(arg.ident_, arg.type_, this));
            }
            LLVMClassMethod llvmMethod = new LLVMClassMethod(classMethodLabel(getName(), method.ident_), method.type_, variables, ((Blk) method.block_).liststmt_, this);
            methods.put(method.ident_, llvmMethod);
        }

    }

    public int getFieldIndex(String name) {
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).ident_.equals(name)) {
                return i;
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
        sb.append(classType);
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
        return classType.getSize();
    }

    public static String classMethodLabel(String className, String methodName) {
        return className + "." + methodName;
    }

    public Function getMethod(String method) {
        return methods.get(method);
    }
}

