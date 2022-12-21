package latte.backend.quadruple;

import latte.Absyn.Int;
import latte.Absyn.Type;

public class ConstValue {
    Type type;
    String value;

//    Register register;
    // int value
    public ConstValue(int value) {
//        super(Integer.toString(value));
        type = new Int();
        this.value = Integer.toString(value);
    }

    // bool value
    public ConstValue(boolean value) {
        type = new latte.Absyn.Bool();
        this.value = Boolean.toString(value);
    }

    public ConstValue(Register register) {
        this.type = register.type;
        this.value = register.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
