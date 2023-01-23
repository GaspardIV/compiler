package latte.backend.program.global.classes;

import latte.Internal.LatteClass;
import latte.backend.program.global.Scope;

public class LLVMClass extends Scope {

    private final LatteClass frontendClass;
    private LLVMClassType classType;

    public LLVMClass(String contextName, Scope parent, LatteClass latteClass) {
        super(contextName, parent);
        this.frontendClass = latteClass;
    }

    public void convertToLLVM() {
        this.classType = new LLVMClassType(this.getName(), frontendClass.getFields());
//        createLLVMClass();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(classType);
        return sb.toString();
    }
}

