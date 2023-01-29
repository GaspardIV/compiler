package latte.backend.program.global.classes;

import latte.Absyn.Type;

public class MethodPointerPointer extends Type {

    public final String llvmType;

    public MethodPointerPointer() {
        this.llvmType = null;
    }
    public MethodPointerPointer(String llvmType) {
        this.llvmType = llvmType;
    }
    @Override
    public <R, A> R accept(Visitor<R, A> v, A arg) {
        return null;
    }
}
