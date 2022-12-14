package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.backend.quadruple.Value;

import java.util.*;

public class RegisterExprVisitor implements Expr.Visitor<List<Quadruple>, Scope> {
//    private final Environment environment;
//
//    public RegisterExprVisitor(Environment environment) {
//        this.environment = environment;
//    }

    @Override
    public List<Quadruple> visit(ENewArray p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EArrayElem p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EArrayElemR p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENullArr p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENull p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENil p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENew p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EMethod p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EField p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EVar p, Scope arg) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        quadruples.add(new Quadruple(new Register(p.ident_ + arg.getRegisterNumber(p.ident_))));
//        quadruples.add(new Register(p.ident_+arg.getRegisterNumber(p.ident_)));
        return quadruples;
//        return new Arrays.fromArray(new Quadruple[] {
//                new Quadruple(Quadruple.Opcode.LOAD, p.ident_, null, null)
//        });
//        return returp.ident_+arg.getRegisterNumber(p.ident_);
    }

    @Override
    public List<Quadruple> visit(ELitInt p, Scope arg) {
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.VALUE(new Value(p.integer_))));
    }

    @Override
    public List<Quadruple> visit(ELitTrue p, Scope arg) {

        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.VALUE(new Value(1))));
    }

    @Override
    public List<Quadruple> visit(ELitFalse p, Scope arg) {
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.VALUE(new Value(0))));
    }

    @Override
    public List<Quadruple> visit(EApp p, Scope arg) {
        Function function = arg.getFunction(p.ident_);
        List<Quadruple> quadruples = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        for (int i = 0; i < p.listexpr_.size(); i++) {
            List<Quadruple> exprQuadruples = p.listexpr_.get(i).accept(this, arg);
            quadruples.addAll(exprQuadruples);
            registers.add(exprQuadruples.get(exprQuadruples.size() - 1).getRegister());
        }
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.CALL(function.getName(), registers)));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EString p, Scope arg) {
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.VALUE(new Value(p.string_))));
    }

    @Override
    public List<Quadruple> visit(Neg p, Scope arg) {
        List<Quadruple> quadruples = p.expr_.accept(this, arg);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.NEG(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Not p, Scope arg) {
        List<Quadruple> quadruples = p.expr_.accept(this, arg);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.NOT(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EMul p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.MUL(p.mulop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAdd p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.ADD(p.addop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(ERel p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAnd p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.AND(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(EOr p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp")), new Quadruple.LLVMOperation.OR(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }
}
