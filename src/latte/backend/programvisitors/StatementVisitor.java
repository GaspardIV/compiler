package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.Internal.InternalBlock;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.*;
import latte.backend.quadruple.Block;
import latte.utils.Utils;

import java.util.*;

public class StatementVisitor implements Stmt.Visitor<Block, Block> {
    @Override
    public Block visit(Empty p, Block block) {
        return null;
    }

    @Override
    public Block visit(BStmt p, Block block) {
        if (((Blk) p.block_).liststmt_.size() == 1 && ((Blk) p.block_).liststmt_.get(0) instanceof InternalBlock) {
            InternalBlock internalBlock = (InternalBlock) ((Blk) p.block_).liststmt_.get(0);
            Scope oldscope = block.getScope();
            block.setScope(new Scope("block", oldscope));
            internalBlock.bStmt_.accept(this, block);
            block.setScope(oldscope);

        } else {
            ((Blk) p.block_).liststmt_.forEach(item -> item.accept(this, block.getLastBlock()));
        }
        return null;
    }

    @Override
    public Block visit(Decl p, Block block) {
        p.listitem_.forEach(item -> item.accept(new DeclVisitor(p.type_), block));
        return null;
    }

    @Override
    public Block visit(Ass p, Block block) {
        RegisterExprVisitor registerExprVisitor = new RegisterExprVisitor();
        if (p.expr_1 instanceof EField || p.expr_1 instanceof EArrayElemR || (p.expr_1 instanceof EVar && block.getScope().getVariable(((EVar) p.expr_1).ident_) == null )) {
            registerExprVisitor.loadField = false;
            List<Quadruple> left = p.expr_1.accept(registerExprVisitor, block);
            Expr nonNilRightExpr = Utils.nilExprReplace(p.expr_2, left.get(left.size() - 1).getRegister().type);
            List<Quadruple> right = new RegisterExprVisitor().generateExprCode(nonNilRightExpr, block);
            List<Quadruple> res = new ArrayList<>(left);
            res.addAll(right);
            res.add(new Quadruple(null, new Quadruple.LLVMOperation.STORE(right.get(right.size() - 1).result, left.get(left.size() - 1).result)));
            block.addQuadruples(res);
        } else {
            List<Quadruple> left = p.expr_1.accept(registerExprVisitor, block);
            Register leftLastRegister = left.get(left.size() - 1).result;
            Variable variable = leftLastRegister.getVariable();
            Expr nonNilRightExpr = Utils.nilExprReplace(p.expr_2, variable.getType());
            List<Quadruple> right = new RegisterExprVisitor().generateExprCode(nonNilRightExpr, block);
            List<Quadruple> res = new ArrayList<>(right);
            Register rightLastRegister = right.get(right.size() - 1).result;
            rightLastRegister.setVariable(variable);
            PhiManager.getInstance().markVariableAsRedefined(block.getScope(), variable);
            block.getScope().setLastVariableRegister(variable, rightLastRegister);
            block.addQuadruples(res);
        }
        return null;
    }

    @Override
    public Block visit(Incr p, Block block) {
        List<Quadruple> left = p.expr_.accept(new RegisterExprVisitor(), block);
        new Ass(p.expr_, new EAdd(p.expr_, new Plus(), new ELitInt(1))).accept(this, block);
        block.addQuadruples(left);
        return null;
    }

    @Override
    public Block visit(Decr p, Block block) {
        List<Quadruple> left = p.expr_.accept(new RegisterExprVisitor(), block);
        new Ass(p.expr_, new EAdd(p.expr_, new Minus(), new ELitInt(1))).accept(this, block);
        block.addQuadruples(left);
        return null;
    }

    @Override
    public Block visit(Ret p, Block block) {
        List<Quadruple> expr = new RegisterExprVisitor().generateExprCode(p.expr_, block);
        List<Quadruple> quadruples = new ArrayList<>(expr);
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(quadruples.get(quadruples.size() - 1).getRegister())));
        block.addQuadruples(quadruples);
        return null;
    }

    @Override
    public Block visit(VRet p, Block block) {
        Quadruple quadruple = new Quadruple(null, new Quadruple.LLVMOperation.RET());
        block.addQuadruples(Collections.singletonList(quadruple));
        return null;
    }

    @Override
    public Block visit(Cond p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;

        Block btrue = new Block(function.nextBlockName(), new Scope("if.true", scope), "if.true");
        Block bfalse = new Block(function.nextBlockName(), scope, "if.false");
        Block bend = new Block(function.nextBlockName(), scope, "if.end");

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), block);

        Register lastRegister = expr.get(expr.size() - 1).result;
        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                entry.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(btrue))));
                entry.addLastBlock(btrue);
                btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue))));
                p.stmt_.accept(this, btrue);
                btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
                btrue.addLastBlock(bend);
                bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
                return null;
            } else {
                return null;
            }
        }


        quadruples.addAll(p.expr_.accept(new JumpingCodeGenerator(btrue, bfalse), block));
        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);

        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue))));
        p.stmt_.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
        Block lastBlockOfTrue = btrue.getLastBlock();
        btrue.addLastBlock(bfalse);

        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse))));
        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
        Block lastBlockOfFalse = bfalse.getLastBlock();
        bfalse.addLastBlock(bend);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
        Set<String> variableNames = btrue.getRedefinedVariables();
        bend.addQuadruplesToLastBlock(entry.createConditionPhiVariables(variableNames, lastBlockOfTrue, lastBlockOfFalse));
        scope.resetLastRegisterOfVariables(btrue.getRedefinedVariables());
        return null;
    }

    @Override
    public Block visit(CondElse p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;

        Block btrue = new Block(function.nextBlockName(), new Scope("if.true", scope), "if.true");
        Block bfalse = new Block(function.nextBlockName(), new Scope("if.false", scope), "if.false");
        Block bend = new Block(function.nextBlockName(), scope, "if.end");

        List<Quadruple> expr = p.expr_.accept(new RegisterExprVisitor(), block);

        Register lastRegister = expr.get(expr.size() - 1).getRegister();
        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                entry.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(btrue))));
                entry.addLastBlock(btrue);
                btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue))));
                p.stmt_1.accept(this, btrue);
                btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
                btrue.addLastBlock(bend);
                bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
                return null;
            } else {
                entry.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bfalse))));
                entry.addLastBlock(bfalse);
                bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse))));
                p.stmt_2.accept(this, bfalse);
                bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
                bfalse.addLastBlock(bend);
                bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
                return null;
            }
        }
        quadruples.addAll(p.expr_.accept(new JumpingCodeGenerator(btrue, bfalse), block));

        entry.addQuadruplesToLastBlock(quadruples);
        entry.addLastBlock(btrue);

        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue))));
        p.stmt_1.accept(this, btrue);
        btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
        Block lasBlockOfTrue = btrue.getLastBlock();
        btrue.addLastBlock(bfalse);
        scope.resetLastRegisterOfVariables(btrue.getRedefinedVariables());

        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse))));
        p.stmt_2.accept(this, bfalse);
        bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
        Block lastBlockOfFalse = bfalse.getLastBlock();
        bfalse.addLastBlock(bend);

        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
        Set<String> variableNames = bfalse.getRedefinedVariables();
        variableNames.addAll(btrue.getRedefinedVariables());
        List<Quadruple> phi1 = entry.createConditionPhiVariables(variableNames, lasBlockOfTrue, lastBlockOfFalse);
        bend.addQuadruplesToLastBlock(phi1);
        scope.resetLastRegisterOfVariables(bfalse.getRedefinedVariables());
        return null;
    }

    @Override
    public Block visit(While p, Block block) {
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;
        Block cond = new Block(function.nextBlockName(), scope, "while.cond");
        Block body = new Block(function.nextBlockName(), new Scope("while.body", scope), "while.body");
        Block bend = new Block(function.nextBlockName(), scope, "while.end");



        PhiManager.getInstance().pushScope(body.getScope());
        List<Quadruple> exprs = p.expr_.accept(new RegisterExprVisitor(), cond);
        Register lastRegister = exprs.get(exprs.size() - 1).getRegister();

        if (lastRegister.isConst()) {
            if (lastRegister.getConstValue().getBool()) {
                entry.addLastBlock(body);
                entry.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(body)));
                p.stmt_.accept(this, block);
                body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(body))));
                List<Quadruple> phi1 = body.createLoopPhiVariables(entry, body.getLastBlock());
                body.addQuadruplesAtTheBeginning(phi1);
                body.addQuadruplesAtTheBeginning(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(body))));
            }
            return null;
        }

        entry.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond)));
        entry.addLastBlock(cond);
        exprs = p.expr_.accept(new JumpingCodeGenerator(body, bend), block);
        cond.addQuadruplesToLastBlock(exprs);
        cond.addLastBlock(body);
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(body))));
        p.stmt_.accept(this, body);
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond))));
        List<Quadruple> phi1 = cond.createLoopPhiVariables(entry, body.getLastBlock());

        body.addLastBlock(bend);
        cond.addQuadruplesAtTheBeginning(phi1);
        cond.addQuadruplesAtTheBeginning(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(cond))));
        scope.resetLastRegisterOfVariables(body.getRedefinedVariables());
        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
        return null;
    }

    @Override
    public Block visit(For p, Block block) {
//        throw new RuntimeException("replaced in preprocessor");
        Scope scope = block.getScope();
        Function function = scope.getCurrentFunction();
        Block entry = block;
        Block cond = new Block(function.nextBlockName(), scope, "for.cond");
        Block body = new Block(function.nextBlockName(), new Scope("for.body", scope), "for.body");
        Block bend = new Block(function.nextBlockName(), scope, "for.end");

        List<Quadruple> listVar = p.expr_.accept(new RegisterExprVisitor(), entry);
        Register listVarReg = listVar.get(listVar.size() - 1).getRegister();
        List<Quadruple> exprs = new EField(p.expr_, "length").accept(new RegisterExprVisitor(), entry);
        entry.addQuadruples(listVar);
        entry.addQuadruplesToLastBlock(exprs);
        ListItem item = new ListItem();
        String iteratorName = block.getRegisterNumber("i.d.x.");
        item.add(new Init(iteratorName, new ELitInt(0)));
        new Decl(new Int(), item).accept(this, entry);
        Register length = exprs.get(exprs.size() - 1).getRegister();
        entry.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond)));
        entry.addLastBlock(cond);
        PhiManager.getInstance().pushScope(body.getScope());
        List<Quadruple> getVar = new EVar(iteratorName).accept(new RegisterExprVisitor(), body);
        List<Quadruple> getVar2 = new EVar(iteratorName).accept(new RegisterExprVisitor(), body);
        cond.addQuadruples(getVar);
        Register itres = cond.getQuadruples().get(0).result;
//        cond.addQuadruple(new Quadruple(itres, new Quadruple.LLVMOperation.PHI(new Register("", new Int(), new ConstValue(0)),entry, new Register(iteratorName), body)));
        Register result = new Register(block.getRegisterNumber("ifres."), new Bool());
        cond.addQuadruple(new Quadruple(result, new Quadruple.LLVMOperation.REL(new LTH(), itres, length)));
        cond.addQuadruple(new Quadruple(null, new Quadruple.LLVMOperation.IF(result, body, bend)));
        cond.addLastBlock(body);
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(body))));
        Ar arg = (Ar) p.arg_;
        item.clear();
        item.add(new Init(arg.ident_, new EArrayElemR(listVarReg.getVariable().getName()/* TODO p.exprs*/, new EVar(iteratorName))));
        new Decl(arg.type_, item).accept(this, body.getLastBlock());
        p.stmt_.accept(this, body.getLastBlock());
        new Incr(new EVar(iteratorName)).accept(this, body.getLastBlock());
        body.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(cond))));
        List<Quadruple> phi1 = cond.createLoopPhiVariables(entry, body.getLastBlock());
        body.addLastBlock(bend);
        cond.addQuadruplesAtTheBeginning(phi1);
        cond.addQuadruplesAtTheBeginning(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(cond))));
        scope.resetLastRegisterOfVariables(body.getRedefinedVariables());
        bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
        return null;
    }

    @Override
    public Block visit(SExp p, Block block) {
        if (p.expr_ instanceof EField) return null;
        block.addQuadruples(new RegisterExprVisitor().generateExprCode(p.expr_, block));
        return null;
    }
}
