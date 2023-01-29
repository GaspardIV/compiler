package latte.backend.programvisitors;

import latte.Absyn.Type;
import latte.backend.program.global.classes.MethodPointerPointer;

public class MethodPointerType extends Type {

    public final String llvmType;

    public MethodPointerType() {
        this.llvmType = null;
    }
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
