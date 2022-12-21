package latte.backend.program.global;

import latte.Absyn.Type;
import latte.backend.quadruple.Register;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Scope{
    public Variable(String contextName, Type type_, Scope parent) {
        super(contextName, parent, type_);
    }

    int count = 0;

    Register lastRegister = null;


    public Register getLastRegister() {
        if (lastRegister == null) {
            return new Register(this.contextName + (count == 0 ? "" : count), this.getType());
        } else {
            return lastRegister;
        }
    }

    public Register getNewRegister() {
//        todo zmienne z nazwami konczacymi sie na liczbe beda bledne (np. x1)
            count++;
            lastRegister = new Register(this.contextName + count, this.getType());
            return lastRegister;
    }

    public void setLastRegister(Register result) {
        lastRegister = result;
    }
}
