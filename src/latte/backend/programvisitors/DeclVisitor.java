package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;

import java.util.List;

public class DeclVisitor implements Item.Visitor<List<Quadruple>, Block> {
    private final Type type;

    public DeclVisitor(Type type_) {
        type = type_;
    }

    @Override
    public List<Quadruple> visit(NoInit p, Block block) {
        block.getScope().add(new Variable(p.ident_, type, block.getScope()));
        Expr defaultValue = type.equals(new Int()) ?  new ELitInt(0) : type.equals(new Bool()) ? new ELitFalse() : new EString("");

        return new Ass(new EVar(p.ident_), defaultValue).accept(new StatementVisitor(), block);
    }

    @Override
    public List<Quadruple> visit(Init p, Block block) {
        block.getScope().add(new Variable(p.ident_, type, block.getScope()));
        return new Ass(new EVar(p.ident_), p.expr_).accept(new StatementVisitor(), block);
    }
}
