package latte.backend.quadruple;

import latte.Absyn.Type;
import latte.utils.Utils;

public class Register  {
    public String name;
    public Type type;
    private ConstValue constValue = null;
    public Register(String name_, Type type_) {
        name = name_;
        type = type_;
    }

    // Constructor for empty registers holding const value
    //
    public Register(String name_, Type type_, ConstValue constValue) {
        name = name_;
        type = type_;
        if (constValue != null) {
            this.constValue = constValue;
        }
    }

    public String getLLVMType() {
        return Utils.getLLVMType(type);
    }

    @Override
    public String toString() {
        if (constValue != null) {
//            if ()
            return constValue.toString();
        }
        return "%" + name;
    }


}
