package latte.backend.quadruple;

import latte.Absyn.EString;
import latte.Absyn.Int;
import latte.Absyn.Str;
import latte.Absyn.Type;

public class Value extends QuadrupleArg {
    Type type;
    public Value(String name_) {
        super(name_);
        type = new Str();
    }
    // int value
    public Value(int value) {
        super(Integer.toString(value));
        type = new Int();
    }

    // bool value
    public Value(boolean value) {
        super(Boolean.toString(value));
        type = new latte.Absyn.Bool();
    }

    // void value
//    public Value() {
//        super("void");
//        type = new latte.Absyn.Void();
//    }
}
