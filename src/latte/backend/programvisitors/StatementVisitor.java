package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.Block;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

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
//        res.addAll(left);
        res.addAll(right);
        Register leftLastRegister = left.get(left.size() - 1).result;
        Variable variable = leftLastRegister.getVariable();
        Register rightLastRegister = right.get(right.size() - 1).result;
//        if (false && left.size() == 1 && left.get(0).op == null) {
//            todo check if left is lvalue
//            todo tu dzieje sie cos podejrzanego

            if (arg.getCurrentBlock().markPhiVariables) {
                rightLastRegister.phiRegister = variable.getLastRegister();
            }
//            if ()
        rightLastRegister.setVariable(variable);
        variable.setLastRegister(rightLastRegister);
//        } else {
//            if (arg.getCurrentBlock().markPhiVariables) {
//                leftLastRegister.phiRegister = variable.getLastRegister();
//            }
//            variable.setLastRegister(leftLastRegister);
//            res.add(new Quadruple(leftLastRegister, new Quadruple.LLVMOperation.ASSIGN(rightLastRegister)));
//        }
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
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(VRet p, Scope arg) {
        Quadruple quadruple = new Quadruple(null, new Quadruple.LLVMOperation.RET());
        return Collections.singletonList(quadruple);
    }

    @Override
    public List<Quadruple> visit(Cond p, Scope arg) {

        List<Quadruple> quadruples = new ArrayList<>();
        Block entry = arg.getCurrentBlock();
        Block btrue = new Block(arg.nextBlockName(), arg);
        Block bend = new Block(arg.nextBlockName(), arg);

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), arg);
        quadruples.addAll(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(expr.get(expr.size() - 1).getRegister(), btrue.getName(), bend.getName())));
        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);
        entry.addSuccessor(btrue);
        entry.addSuccessor(bend);
        btrue.addPredecessors(entry);

        btrue.setMarkPhiVariables(true);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue.getName()))));
        List<Quadruple> stmts = p.stmt_.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(stmts);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getName()))));
//        quadruples.addAll(stmts);
        btrue.addLastBlock(bend);
        btrue.addSuccessor(bend);
        bend.addPredecessors(btrue);
        bend.addPredecessors(entry);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend.getName()))));
        bend.addQuadruplesToLastBlock(btrue.getPhiVariables(entry, btrue));
//        quadruples.addAll(btrue.getPhiVariables(entry, btrue));


//        quadruples.addAll(expr);
//        Block bfalse = new Block(arg.nextBlockName(), arg);
        // generate quadruples for condition in ssa block form with phi nodes
        // if condition is true then jump to stmts
        // if condition is false then jump to end of block
        // add phi nodes to stmts
        // add jump to end of block
        // add phi nodes to end of block
        // add end of block



        // do kazdej quadrupla w stylu var = register, mozna zrobic phi register (jak jest w srodku if)
//        i pozniej przy kolejnym odwolaniu sie do tego np ostatniej wartosci zmiennej a, to jezeli ona miala phi registera to trzeba dodac phi na poczatek aktualnego bloku

//        uogulniajac jezeli w visitorze ktory jest w bloku i ma przekazane aby dodawal phi node to dodaje
//        w cond wzystko normalnie
// w stmts dodaje phi node, jezeli ustawia jaka wartosc zmiennej (lewostronnie)
//        po end jezeli kiedykowiek uzywam rejestru ktory ma phi node to dodaje phi node na poczaek aktualnego bloku (phi jest zaznaczony jako phi node, z nazwa blokow obu)





        // dla kazdej zmiennej czy tam registru w bloku zaznaczyc jakich zmiennych uzywa -> (var, register)
        // jezeli uzywa zmiennej ktora jest zmodifikowana w bloku to dodac phi node na koniec bloku - ze albo zmienna



//        return quadruples;
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(CondElse p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        Block entry = arg.getCurrentBlock();
        Block btrue = new Block(arg.nextBlockName(), arg);
        Block bfalse = new Block(arg.nextBlockName(), arg);
        Block bend = new Block(arg.nextBlockName(), arg);

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), arg);
        quadruples.addAll(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(expr.get(expr.size() - 1).getRegister(), btrue.getName(), bfalse.getName())));
        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);
        entry.addSuccessor(btrue);
        entry.addSuccessor(bfalse);
        btrue.addPredecessors(entry);
        bfalse.addPredecessors(entry);

        btrue.setMarkPhiVariables(true);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue.getName()))));
        List<Quadruple> stmts = p.stmt_1.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(stmts);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getName()))));
        btrue.addLastBlock(bfalse);
        btrue.addSuccessor(bend);
        bend.addPredecessors(btrue);

        bfalse.setMarkPhiVariables(true);
        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse.getName()))));
        stmts = p.stmt_2.accept(this, bfalse);
        bfalse.addQuadruplesToLastBlock(stmts);
        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getName()))));
        bfalse.addLastBlock(bend);
        bend.addPredecessors(bfalse);
        bfalse.addSuccessor(bend);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend.getName()))));
        List<Quadruple> phi1 = bfalse.getPhiVariables(btrue, bfalse);

        bend.addQuadruplesToLastBlock(phi1);

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
