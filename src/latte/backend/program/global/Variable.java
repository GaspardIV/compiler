package latte.backend.program.global;

import latte.Absyn.Type;

public class Variable extends Scope {
    public Variable(String contextName, Type type_, Scope parent) {
        super(contextName, parent, type_);
    }
}
