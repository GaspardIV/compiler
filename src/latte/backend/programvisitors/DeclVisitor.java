package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Quadruple;

import java.util.ArrayList;
import java.util.List;

public class DeclVisitor implements Item.Visitor<List<Quadruple>, Scope> {
    private final Type type;

    public DeclVisitor(Type type_) {
        type = type_;
    }

    @Override
    public List<Quadruple> visit(NoInit p, Scope arg) {
        arg.add(new Variable(p.ident_, type, arg));
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(Init p, Scope arg) {
        arg.add(new Variable(p.ident_, type, arg));
        return new Ass(new EVar(p.ident_), p.expr_).accept(new StatementVisitor(), arg);
    }
}
