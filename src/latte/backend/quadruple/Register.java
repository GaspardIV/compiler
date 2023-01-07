package latte.backend.quadruple;

import latte.Absyn.Type;
import latte.backend.program.global.Variable;
import latte.utils.Utils;

public class Register  {
    public String name;
    public Type type;
    private ConstValue constValue = null;
    private Variable variable = null;
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
            return constValue.toString();
        }
        return "%" + name;
    }

    public Variable getVariable() {
        return variable;
    }

    public ConstValue getConstValue() {
        return constValue;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }
}
