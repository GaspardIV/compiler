package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

public class DeclVisitor implements Item.Visitor<String, Scope> {
    private final Type type;

    public DeclVisitor(Type type_) {
        type = type_;
    }

    @Override
    public String visit(NoInit p, Scope arg) {
        arg.add(new Variable(p.ident_, type, arg));
        return "";
    }

    @Override
    public String visit(Init p, Scope arg) {
        arg.add(new Variable(p.ident_, type, arg));
        return new Ass(new EVar(p.ident_), p.expr_).accept(new StatementVisitor(), arg);
    }
}
