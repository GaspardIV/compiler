package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.util.ArrayList;
import java.util.List;

public class DeclVisitor implements Item.Visitor<List<Quadruple>, Block> {
    private final Type type;

    public DeclVisitor(Type type_) {
        type = type_;
    }

    @Override
    public List<Quadruple> visit(NoInit p, Block block) {
//        block.getScope().addVariable(p.ident_, type);
        Expr defaultValue = type.equals(new Int()) ?  new ELitInt(0) : type.equals(new Bool()) ? new ELitFalse() : new EString("");
//        new Ass(new EVar(p.ident_), defaultValue).accept(new StatementVisitor(), block);
        // call visit with init
//        ListItem item = new ListItem();
//        item.add(
                return new Init(p.ident_, defaultValue).accept(this, block);
//        return new Decl(type, item).accept(this, block);
//        return null;
    }

    @Override
    public List<Quadruple> visit(Init p, Block block) {
        List<Quadruple> res = new ArrayList<>();
        List<Quadruple> right = p.expr_.accept(new RegisterExprVisitor(), block);
        res.addAll(right);
        Variable variable = new Variable(p.ident_, type, block.getScope());
        Register rightLastRegister = right.get(right.size() - 1).result;
        block.getScope().addVariable(variable);
        rightLastRegister.setVariable(variable);
        block.getScope().setLastVariableRegister(variable, rightLastRegister);
//        block.setLastRegisterOfVariable(variable.getName(), rightLastRegister);
        block.addQuadruples(res);
        return null;
    }
}
