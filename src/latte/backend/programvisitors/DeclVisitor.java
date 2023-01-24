package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeclVisitor implements Item.Visitor<List<Quadruple>, Block> {
    private final Type type;

    public DeclVisitor(Type type_) {
        type = type_;
    }

    @Override
    public List<Quadruple> visit(NoInit p, Block block) {
        return new Init(p.ident_, Utils.defaultValue(type)).accept(this, block);

    }

    @Override
    public List<Quadruple> visit(Init p, Block block) {
        List<Quadruple> res = new ArrayList<>();
        List<Quadruple> right = new RegisterExprVisitor().generateExprCode(p.expr_, block);
        res.addAll(right);
        Variable variable = new Variable(p.ident_, type, block.getScope());
        Register rightLastRegister = right.get(right.size() - 1).result;
        block.getScope().addVariable(variable);
        rightLastRegister.setVariable(variable);
        block.getScope().setLastVariableRegister(variable, rightLastRegister);
        block.addQuadruples(res);
        return null;
    }
}
