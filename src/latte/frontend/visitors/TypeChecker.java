package latte.frontend.visitors;

import latte.Absyn.*;
import latte.frontend.environment.Environment;

import java.lang.Void;

public class TypeChecker implements Type.Visitor<Void, Environment> {
    @Override
    public Void visit(Int p, Environment arg) {
        return null;
    }

    @Override
    public Void visit(Str p, Environment arg) {
        return null;
    }

    @Override
    public Void visit(Bool p, Environment arg) {
        return null;
    }

    @Override
    public Void visit(latte.Absyn.Void p, Environment arg) {
        return null;
    }

    @Override
    public Void visit(latte.Absyn.Class p, Environment arg) {
        return null;
    }

    @Override
    public Void visit(Array p, Environment arg) {
        return null;
    }
}
