//package latte.backend.programvisitors;
//
//import latte.Absyn.*;
//import latte.backend.program.global.Function;
//import latte.backend.program.global.Scope;
//import latte.backend.quadruple.Block;
//import latte.backend.quadruple.Quadruple;
//import latte.backend.quadruple.Register;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class JumpingCodeGenerator extends RegisterExprVisitor {
//
//
//    public List<Quadruple> visit(EVar p, Block block) {
//        List<Quadruple> quadruples = new ArrayList<>();
//        quadruples = p.accept(new RegisterExprVisitor(), block);
//        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
//        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue.getIdentifier(), jumpBlockFalse.getIdentifier())));
//        return quadruples;
//    }
//
//    @Override
//    public List<Quadruple> visit(ELitTrue p, Block block) {
//        List<Quadruple> res = new ArrayList<>();
//        res.add(new Quadruple(null, new Quadruple.LLVMOperation.BR(jumpBlockTrue.getIdentifier())));
//        return res;
//    }
//
//    @Override
//    public List<Quadruple> visit(ELitFalse p, Block block) {
//        List<Quadruple> res = new ArrayList<>();
//        res.add(new Quadruple(null, new Quadruple.LLVMOperation.BR(jumpBlockFalse.getIdentifier())));
//        return res;
//    }
//
//    @Override
//    public List<Quadruple> visit(EApp p, Block block) {
//        List<Quadruple> quadruples = new ArrayList<>();
//        quadruples = p.accept(new RegisterExprVisitor(), block);
//        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
//        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue.getIdentifier(), jumpBlockFalse.getIdentifier())));
//        return quadruples;
//    }
//
//    @Override
//    public List<Quadruple> visit(Not p, Block block) {
//        List<Quadruple> quadruples = new ArrayList<>();
//        quadruples = p.accept(new RegisterExprVisitor(), block);
//        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
//        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue.getIdentifier(), jumpBlockFalse.getIdentifier())));
//        return quadruples;
//    }
//
//    @Override
//    public List<Quadruple> visit(EAnd p, Block condBlock) {
//        List<Quadruple> res = new ArrayList<>();
//        Block blockMiddle = new Block(condBlock.getScope().getCurrentFunction().nextBlockName(), condBlock.getScope(), "and");
//        List<Quadruple> left = p.expr_1.accept(new JumpingCodeGenerator(blockMiddle, jumpBlockFalse), condBlock);
//        res.addAll(left);
//        res.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(blockMiddle.getIdentifier())));
//        List<Quadruple> right = p.expr_2.accept(new JumpingCodeGenerator(jumpBlockTrue, jumpBlockFalse), condBlock);
//        res.addAll(right);
////        condBlock.addQuadruples(right);
//        return res;
//    }
//
//    @Override
//    public List<Quadruple> visit(EOr p, Block condBlock) {
//        List<Quadruple> res = new ArrayList<>();
//        Block blockMiddle = new Block(condBlock.getScope().getCurrentFunction().nextBlockName(), condBlock.getScope(), "or");
//        List<Quadruple> left = p.expr_1.accept(new JumpingCodeGenerator(jumpBlockTrue, blockMiddle), condBlock);
//        res.addAll(left);
//        res.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(blockMiddle.getIdentifier())));
//        List<Quadruple> right = p.expr_2.accept(new JumpingCodeGenerator(jumpBlockTrue, jumpBlockFalse), condBlock);
//        res.addAll(right);
//        return res;
//    }
//
//    @Override
//    public List<Quadruple> visit(ERel p, Block block) {
//        List<Quadruple> quadruples = new ArrayList<>();
//        quadruples = p.accept(new RegisterExprVisitor(), block);
//        Register tmp = quadruples.get(quadruples.size() - 1).getRegister();
//        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.IF(tmp, jumpBlockTrue.getIdentifier(), jumpBlockFalse.getIdentifier())));
//        return quadruples;
//    }
//
//    @Override
//
//    public List<Quadruple> generateExprCode(Expr expr, Block block) {
////        if (IsExprBoolTypeManager.getInstance().isBool(expr)) {
//////            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int()), new Quadruple.LLVMOperation.NEG(quadruples.get(quadruples.size() - 1).getRegister())));
////            Quadruple quadruple = new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.NEG(expr.accept(this, block).get(0).getRegister()));
////            return new CondElse(expr, new Ass(p.expr_1, new ELitTrue()), new Ass(p.expr_1, new ELitFalse())).accept(this, block);
//////            return expr.accept(new JumpingCodeGenerator(block.getTrueBlock(), block.getFalseBlock()), block);
////            return Collections.singletonList(quadruple);
//
////        } else {
////            return expr.accept(this, block);
////        }
//    }
//
//
//
//}
