package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.util.ArrayList;
import java.util.List;

public class JumpingCodeGenerator extends RegisterExprVisitor {

    private Block jumpBlockTrue;
    private Block jumpBlockFalse;

    public JumpingCodeGenerator(Block jumpBlockTrue, Block jumpBlockFalse) {
        this.jumpBlockTrue = jumpBlockTrue;
        this.jumpBlockFalse = jumpBlockFalse;
    }

    @Override
    public List<Quadruple> visit(EVar p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        quadruples = p.accept(new RegisterExprVisitor(), block);
        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue, jumpBlockFalse)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(ELitTrue p, Block block) {
        List<Quadruple> res = new ArrayList<>();
        res.add(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(jumpBlockTrue)));
        return res;
    }

    @Override
    public List<Quadruple> visit(ELitFalse p, Block block) {
        List<Quadruple> res = new ArrayList<>();
        res.add(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(jumpBlockFalse)));
        return res;
    }

    @Override
    public List<Quadruple> visit(EApp p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>(p.accept(new RegisterExprVisitor(), block));
        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue, jumpBlockFalse)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Not p, Block block) {
        List<Quadruple> quadruples;
        Block tmpT = jumpBlockTrue;
        Block tmpF = jumpBlockFalse;
        jumpBlockTrue = tmpF;
        jumpBlockFalse = tmpT;
        quadruples = p.expr_.accept(this, block);
        jumpBlockFalse = tmpF;
        jumpBlockTrue = tmpT;
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAnd p, Block condBlock) {
        List<Quadruple> res = new ArrayList<>();
        Block blockMiddle = new Block(condBlock.getScope().getCurrentFunction().nextBlockName(), condBlock.getScope(), "and");
        List<Quadruple> left = p.expr_1.accept(new JumpingCodeGenerator(blockMiddle, jumpBlockFalse), condBlock);
        res.addAll(left);
        res.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(blockMiddle)));
        List<Quadruple> right = p.expr_2.accept(new JumpingCodeGenerator(jumpBlockTrue, jumpBlockFalse), condBlock);
        res.addAll(right);
        return res;
    }

    @Override
    public List<Quadruple> visit(EOr p, Block condBlock) {
        List<Quadruple> res = new ArrayList<>();
        Block blockMiddle = new Block(condBlock.getScope().getCurrentFunction().nextBlockName(), condBlock.getScope(), "or");
        List<Quadruple> left = p.expr_1.accept(new JumpingCodeGenerator(jumpBlockTrue, blockMiddle), condBlock);
        res.addAll(left);
        res.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(blockMiddle)));
        List<Quadruple> right = p.expr_2.accept(new JumpingCodeGenerator(jumpBlockTrue, jumpBlockFalse), condBlock);
        res.addAll(right);
        return res;
    }

    @Override
    public List<Quadruple> visit(ERel p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        quadruples = p.accept(new RegisterExprVisitor(), block);
        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue, jumpBlockFalse)));
        return quadruples;
    }


}
