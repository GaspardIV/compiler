package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatementVisitor implements Stmt.Visitor<List<Quadruple>, Scope> {
    @Override
    public List<Quadruple> visit(Empty p, Scope arg) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(BStmt p, Scope arg) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        ((Blk) p.block_).liststmt_.forEach(item -> quadruples.addAll(item.accept(this, arg)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Decl p, Scope arg) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        p.listitem_.forEach(item -> quadruples.addAll(item.accept(new DeclVisitor(p.type_), arg)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Ass p, Scope arg) {
        List<Quadruple> res = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(new RegisterExprVisitor(), arg);
        List<Quadruple> right = p.expr_2.accept(new RegisterExprVisitor(), arg);
        res.addAll(left);
        res.addAll(right);
        if (left.size() == 1 && left.get(0).op == null) {
            arg.getVariable(left.get(0).result).setLastRegister(right.get(right.size() - 1).result);
        } else {
            res.add(new Quadruple(left.get(left.size() - 1).getRegister(), new Quadruple.LLVMOperation.ASSIGN(right.get(right.size() - 1).getRegister())));
        }
        return res;
    }

    @Override
    public List<Quadruple> visit(Incr p, Scope arg) {
        // todo w wyrazeniu np x= y[i++]; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Plus(), new ELitInt(1))).accept(this, arg);
    }

    @Override
    public List<Quadruple> visit(Decr p, Scope arg) {
        // todo w wyrazeniu np x= y--; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Minus(), new ELitInt(1))).accept(this, arg);
    }

    @Override
    public List<Quadruple> visit(Ret p, Scope arg) {
        RegisterExprVisitor registerExprVisitor = new RegisterExprVisitor(/*environment*/);
        List<Quadruple> expr = p.expr_.accept(registerExprVisitor, arg);
        List<Quadruple> quadruples = new ArrayList<>(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.VRET(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(VRet p, Scope arg) {
        Quadruple quadruple = new Quadruple(null, new Quadruple.LLVMOperation.VRET());
        return Collections.singletonList(quadruple);
    }

    @Override
    public List<Quadruple> visit(Cond p, Scope arg) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(CondElse p, Scope arg) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(While p, Scope arg) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(For p, Scope arg) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(SExp p, Scope arg) {
        return p.expr_.accept(new RegisterExprVisitor(), arg);
    }
}
