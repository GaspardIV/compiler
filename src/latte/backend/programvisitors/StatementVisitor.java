package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Function;
import latte.backend.quadruple.Block;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class StatementVisitor implements Stmt.Visitor<List<Quadruple>, Block> {
    @Override
    public List<Quadruple> visit(Empty p, Block block) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(BStmt p, Block block) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        ((Blk) p.block_).liststmt_.forEach(item -> quadruples.addAll(item.accept(this, block)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Decl p, Block block) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        p.listitem_.forEach(item -> quadruples.addAll(item.accept(new DeclVisitor(p.type_), block)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Ass p, Block block) {
        List<Quadruple> res = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(new RegisterExprVisitor(), block);
        List<Quadruple> right = p.expr_2.accept(new RegisterExprVisitor(), block);
        res.addAll(right);
        Register leftLastRegister = left.get(left.size() - 1).result;
        Variable variable = leftLastRegister.getVariable();
        Register rightLastRegister = right.get(right.size() - 1).result;
        rightLastRegister.setVariable(variable);
        variable.setLastRegister(rightLastRegister);
        block.setLastRegisterOfVariable(variable.getName(), rightLastRegister);
        return res;
    }

    @Override
    public List<Quadruple> visit(Incr p, Block block) {
        // todo w wyrazeniu np x= y[i++]; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Plus(), new ELitInt(1))).accept(this, block);
    }

    @Override
    public List<Quadruple> visit(Decr p, Block block) {
        // todo w wyrazeniu np x= y--; jest zle!!!!
        return new Ass(p.expr_, new EAdd(p.expr_, new Minus(), new ELitInt(1))).accept(this, block);
    }

    @Override
    public List<Quadruple> visit(Ret p, Block block) {
        RegisterExprVisitor registerExprVisitor = new RegisterExprVisitor(/*environment*/);
        List<Quadruple> expr = p.expr_.accept(registerExprVisitor, block);
        List<Quadruple> quadruples = new ArrayList<>(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(VRet p, Block block) {
        Quadruple quadruple = new Quadruple(null, new Quadruple.LLVMOperation.RET());
        return Collections.singletonList(quadruple);
    }

    @Override
    public List<Quadruple> visit(Cond p, Block block) {

        List<Quadruple> quadruples = new ArrayList<>();
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;

        Block btrue = new Block(function.nextBlockName(), new Scope("if.true",scope), "if.true");
        Block bend = new Block(function.nextBlockName(),scope, "if.end");

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), block);

        Register lastRegister = expr.get(expr.size() - 1).result;
        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                entry.addQuadruplesToLastBlock(p.stmt_.accept(this, entry));
                return new ArrayList<>();
            } else {
                return new ArrayList<>();
            }
        }


        quadruples.addAll(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(expr.get(expr.size() - 1).getRegister(), btrue.getIdentifier(), bend.getIdentifier())));
        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);
        entry.addSuccessor(btrue);
        entry.addSuccessor(bend);
        btrue.addPredecessors(entry);

//        btrue.setMarkPhiVariables(true);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue.getIdentifier()))));
        List<Quadruple> stmts = p.stmt_.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(stmts);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getIdentifier()))));
//        quadruples.addAll(stmts);
        btrue.addLastBlock(bend);
        btrue.addSuccessor(bend);
        bend.addPredecessors(btrue);
        bend.addPredecessors(entry);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend.getIdentifier()))));
        List<String> variableNames = btrue.getRedefinedVariables();
        bend.addQuadruplesToLastBlock(Block.createPhiVariables(variableNames, entry, btrue));
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(CondElse p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;

        Block btrue = new Block(function.nextBlockName(), new Scope("if.true",scope), "if.true");
        Block bfalse = new Block(function.nextBlockName(), new Scope("if.false",scope), "if.false");
        Block bend = new Block(function.nextBlockName(), scope, "if.end");

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), block);

//        if is const then we can optimize it to block that points to true or false
        Register lastRegister = expr.get(expr.size() - 1).getRegister();
        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                List<Quadruple> stmts = p.stmt_1.accept(this, block);
                block.addQuadruplesToLastBlock(stmts);
                return new ArrayList<>();
            } else {
                List<Quadruple> stmts = p.stmt_2.accept(this, block);
                block.addQuadruplesToLastBlock(stmts);
                return new ArrayList<>();
            }
        }

        quadruples.addAll(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(expr.get(expr.size() - 1).getRegister(), btrue.getIdentifier(), bfalse.getIdentifier())));
        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);
        entry.addSuccessor(btrue);
        entry.addSuccessor(bfalse);
        btrue.addPredecessors(entry);
        bfalse.addPredecessors(entry);

        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue.getIdentifier()))));
        List<Quadruple> stmts = p.stmt_1.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(stmts);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getIdentifier()))));
        btrue.addLastBlock(bfalse);
        btrue.addSuccessor(bend);
        bend.addPredecessors(btrue);

        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse.getIdentifier()))));
        stmts = p.stmt_2.accept(this, bfalse);
        bfalse.addQuadruplesToLastBlock(stmts);
        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getIdentifier()))));
        bfalse.addLastBlock(bend);
        bend.addPredecessors(bfalse);
        bfalse.addSuccessor(bend);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend.getIdentifier()))));
        List<String> variableNames = bfalse.getRedefinedVariables();
        variableNames.addAll(btrue.getRedefinedVariables());
        List<Quadruple> phi1 = Block.createPhiVariables(variableNames, btrue, bfalse);

        bend.addQuadruplesToLastBlock(phi1);

        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(While p, Block block) {
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;
        Scope condScope = new Scope("while.cond", scope);
        Block cond = new Block(function.nextBlockName(), condScope, "while.cond");
        Block body = new Block(function.nextBlockName(), new Scope("while.body", condScope), "while.body");
        Block bend = new Block(function.nextBlockName(), scope, "while.end");


        entry.addLastBlock(cond);
        entry.addSuccessor(cond);
        cond.addPredecessors(entry);
        cond.setMarkPhiVariables(true);
        List<Quadruple> exprs = p.expr_.accept(new RegisterExprVisitor(), cond);


        Register lastRegister = exprs.get(exprs.size() - 1).getRegister();
        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                entry.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(body.getIdentifier())));
                cond.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(body.getIdentifier()))));
                List<Quadruple> stmts = p.stmt_.accept(this, cond);
                cond.addQuadruplesToLastBlock(stmts);
                cond.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(body.getIdentifier()))));
            }
            return new ArrayList<>();
        }



        entry.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond.getIdentifier())));

        cond.addQuadruplesToLastBlock(exprs);
        cond.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.IF(exprs.get(exprs.size() - 1).getRegister(), body.getIdentifier(), bend.getIdentifier()))));
        cond.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend.getIdentifier()))));
        cond.addLastBlock(body);
        bend.addPredecessors(cond);
        cond.addSuccessor(bend);
        cond.addSuccessor(body);
        body.addPredecessors(cond);

        body.pastePhiVariables(cond);
        body.setMarkPhiVariables(true);
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(body.getIdentifier()))));
        List<Quadruple> stmts = p.stmt_.accept(this, body);
        body.addQuadruplesToLastBlock(stmts);
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond.getIdentifier()))));
        body.addLastBlock(bend);
        body.addSuccessor(cond);
        cond.addPredecessors(body);

        cond.resetLastUseOfVariables(); // so it uses variables from cond at the end
//        Listcond.getRedefinedVariables().addAll(body.getRedefinedVariables());
        HashSet<String> variableNames = new HashSet<>(body.getRedefinedVariables());
        variableNames.addAll(cond.getRedefinedVariables());
        variableNames.addAll(cond.getUsedVariables());
        variableNames.addAll(body.getUsedVariables());
        List<Quadruple> phi1 = cond.getPhiVariables(new ArrayList<>(variableNames), entry, body);
        cond.addQuadruplesAtTheBeginning(phi1);
        cond.addQuadruplesAtTheBeginning(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(cond.getIdentifier()))));


        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend.getIdentifier()))));
//        List<Quadruple> phi2 = body.getPhiVariables(body, cond);
//        bend.addQuadruplesToLastBlock(phi2);
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(For p, Block block) {
        return new ArrayList<>();
    }

    @Override
    public List<Quadruple> visit(SExp p, Block block) {
        return p.expr_.accept(new RegisterExprVisitor(), block);
    }
}
