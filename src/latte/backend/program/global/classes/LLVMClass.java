package latte.backend.program.global.classes;

import latte.Internal.LatteClass;
import latte.backend.program.global.Scope;

import java.util.HashMap;

public class LLVMClass extends Scope {

    private final LatteClass frontendClass;
    private LLVMClassType classType;
    private LLVMClassConstructor constructor;

    public LLVMClass(String contextName, Scope parent, LatteClass latteClass) {
        super(contextName, parent);
        this.frontendClass = latteClass;
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
}

