package latte.Internal;

import latte.Absyn.Type;

public class MethodPointerType extends Type {

    public final String llvmType;

    public MethodPointerType(String llvmType) {
        this.llvmType = llvmType;
    }

    @Override
    public <R, A> R accept(Visitor<R, A> v, A arg) {
        return null;
    }

    public Type toPointer() {
        return new MethodPointerPointer(llvmType);
    }
}
