package latte.backend.program.global.classes;

import latte.Absyn.Type;
import latte.Internal.ClField;
import latte.Internal.LatteClass;
import latte.backend.program.global.Scope;

import java.util.HashMap;
import java.util.List;

public class LLVMClass extends Scope {

    private final LatteClass frontendClass;
    private final List<ClField> fields;
    private LLVMClassType classType;
    private LLVMClassConstructor constructor;

    public LLVMClass(String contextName, Scope parent, LatteClass latteClass) {
        super(contextName, parent);
        this.frontendClass = latteClass;
        this.fields = frontendClass.getFields();
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
        this.classType = new LLVMClassType(this.getName(), frontendClass.getFields());
        this.constructor = new LLVMClassConstructor(this.getName(), frontendClass.getFields());
//        this.methods = new HashMap<>();
//        createLLVMClass();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(classType);
        sb.append(constructor);
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
}

