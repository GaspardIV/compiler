package latte.backend.quadruple;

import latte.Absyn.EString;
import latte.Absyn.Int;
import latte.Absyn.Str;
import latte.Absyn.Type;

public class Value{
    Type type;
    String value;
    public Value(String name_) {
        value = name_;
        type = new Str();
    }

    // int value
    public Value(int value) {
//        super(Integer.toString(value));
        type = new Int();
        this.value = Integer.toString(value);
    }

    // bool value
    public Value(boolean value) {

        type = new latte.Absyn.Bool();
        this.value = Boolean.toString(value);
    }

    @Override
    public String toString() {
        if (type instanceof Str) {
            return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r").replace("\b", "\\b").replace("\f", "\\f") + "\"";
        }
        return value;
    }
}
