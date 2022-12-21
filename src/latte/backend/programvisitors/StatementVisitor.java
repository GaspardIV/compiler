package latte.backend.programvisitors;

//import latte.Absyn

import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static latte.utils.Utils.getLLVMType;

public class StatementVisitor implements Stmt.Visitor<String, Scope> {
    @Override
    public String visit(Empty p, Scope arg) {
        return "";
    }

    @Override
    public String visit(BStmt p, Scope arg) {
        StringBuilder stringBuilder = new StringBuilder();
        ((Blk) p.block_).liststmt_.forEach(stmt -> stringBuilder.append(stmt.accept(this, arg)));
        return stringBuilder.toString();
    }

    @Override
    public String visit(Decl p, Scope arg) {
        StringBuilder stringBuilder = new StringBuilder();
        p.listitem_.forEach(item -> stringBuilder.append(item.accept(new DeclVisitor(p.type_), arg)));
        return stringBuilder.toString();
    }

    @Override
    public String visit(Ass p, Scope arg) {
        List<Quadruple> res = new ArrayList<>();
        List<Quadruple> left =  p.expr_1.accept(new RegisterExprVisitor(), arg);
        List<Quadruple> right = p.expr_2.accept(new RegisterExprVisitor(), arg);
//        System.out.println("left.get(0).result = " + left.get(0).result);
//        System.out.println("right.get(0).result = " + right.get(0).result);

        res.addAll(left);
        res.addAll(right);
        if (left.size()== 1 && left.get(0).op == null) {
//            if (right.size() == 1 && right.get(0).op == null) {
//            } else {
//                left.get(0).result = arg.getVariable(left.get(0).result).getNewRegister();
//            System.out.println("left.get(0).result = " + left.get(0).result);
//            System.out.println("right.get(0).result = " + right.get(right.size()-1).result);

//            System.out.println("arg.getVariable(left.get(0).result).getLastRegister() = " + arg.getVariable(left.get(0).result).getLastRegister());
            arg.getVariable(left.get(0).result).setLastRegister(right.get(right.size()-1).result);
//            System.out.println("arg.getVariable(left.get(0).result).getLastRegister() = " + arg.getVariable(left.get(0).result).getLastRegister());
//            left.get(0).result = right.get(right.size()-1).result;
//            return "hahahah";
//            }
        } else {
            res.add(new Quadruple(left.get(left.size() - 1).getRegister(), new Quadruple.LLVMOperation.ASSIGN(right.get(right.size() - 1).getRegister())));

        }
        StringBuilder stringBuilder = new StringBuilder();
        res.forEach(quadruple -> stringBuilder.append(quadruple.toString()));
        return stringBuilder.toString();
    }

    @Override
    public String visit(Incr p, Scope arg) {
        // todo w wyrazeniu np x= y[i++]; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Plus(), new ELitInt(1))).accept(this, arg);
    }

    @Override
    public String visit(Decr p, Scope arg) {
        // todo w wyrazeniu np x= y--; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Minus(), new ELitInt(1))).accept(this, arg);
    }

    @Override
    public String visit(Ret p, Scope arg) {
        RegisterExprVisitor registerExprVisitor = new RegisterExprVisitor(/*environment*/);
        List<Quadruple> quadruples = p.expr_.accept(registerExprVisitor, arg);
        StringBuilder stringBuilder = new StringBuilder();
        quadruples.forEach(quadruple -> stringBuilder.append(quadruple.toString()));
        stringBuilder.append(MessageFormat.format("ret {0} {1}", getLLVMType(arg.getType()), quadruples.get(quadruples.size() - 1).getRegister()));
        return stringBuilder.toString();
    }

    @Override
    public String visit(VRet p, Scope arg) {
        return "ret void";
    }

    @Override
    public String visit(Cond p, Scope arg) {
        // conditional
        return " ; conditional";
    }

    @Override
    public String visit(CondElse p, Scope arg) {
        return " ; conditional else";
//        return null;
    }

    @Override
    public String visit(While p, Scope arg) {
        return " ; while";
//        return null;
    }

    @Override
    public String visit(For p, Scope arg) {
        return " ; for";
//        return null;
    }

    @Override
    public String visit(SExp p, Scope arg) {
        List<Quadruple> i = p.expr_.accept(new RegisterExprVisitor(), arg);
        StringBuilder stringBuilder = new StringBuilder();
        i.forEach(quadruple -> stringBuilder.append(quadruple.toString()));
        return stringBuilder.toString();
    }
}
