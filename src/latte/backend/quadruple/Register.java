package latte.backend.quadruple;

import jdk.jfr.consumer.RecordedObject;
import latte.Absyn.Type;
import latte.utils.Utils;

public class Register  {
    public String name;
    public Type type;
    public Register phiRegister = null;
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

    public boolean isConst() {
        return constValue != null;
    }
    @Override
    public String toString() {
        if (constValue != null) {
//            if ()
            return constValue.toString();
        }
        return "%" + name;
    }


    public ConstValue getConstValue() {
        return constValue;
    }
}
