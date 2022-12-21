package latte.backend.program.global;

import latte.Absyn.Type;
import latte.backend.quadruple.Register;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Scope{
//    TODO LOC?????
//    OR EXPR?????
    public Variable(String contextName, Type type_, Scope parent) {
        super(contextName, parent, type_);
    }

    int count = 0;


    public Register getLastRegister() {
        return new Register(this.contextName + (count == 0 ? "" : count), this.getType());
    }

    public Register getNewRegister() {
        if (count == 0) {
            count++;
            return new Register(this.contextName, this.getType());
        } else {
            count++;
            return new Register(this.contextName + count, this.getType());
        }
//        count++;
//        return new Register(this.contextName + count, this.getType());
    }
}
