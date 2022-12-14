package latte.backend.program.global;

import latte.Absyn.Type;

public class Variable extends Scope{
//    TODO LOC?????
//    OR EXPR?????
    public Variable(String contextName, Type type_, Scope parent) {
        super(contextName, parent, type_);
    }
}
