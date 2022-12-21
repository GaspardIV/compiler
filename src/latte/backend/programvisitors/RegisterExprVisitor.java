package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.quadruple.ConstValue;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.utils.Utils;

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
        Register last = arg.getVariable(p.ident_).getLastRegister();
        quadruples.add(new Quadruple(last));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(ELitInt p, Scope arg) {
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Int(), new ConstValue(p.integer_))));
    }

    @Override
    public List<Quadruple> visit(ELitTrue p, Scope arg) {

        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool(), new ConstValue(true))));
    }

    @Override
    public List<Quadruple> visit(ELitFalse p, Scope arg) {
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool(), new ConstValue(false))));
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
        Quadruple.LLVMOperation.CALL quadruple = new Quadruple.LLVMOperation.CALL(function.getType(), function.getName(), registers);
//        if (new Void().equals(function.getType())){
//            quadruples.add(new Quadruple(null, quadruple));
//        }else{
            quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), function.getType()), quadruple));
//        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EString p, Scope arg) {
        String regIdent = arg.addStringGlobalRegister(p.string_);
        String escaped = Utils.getEscapedString(p.string_);
        int escapedLength = Utils.getLLVMEscapedStringLength(escaped);
        return Collections.singletonList(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Str()), new Quadruple.LLVMOperation.GETELEMENTPTRSTR(escapedLength, regIdent, 0, 0)));
    }

    @Override
    public List<Quadruple> visit(Neg p, Scope arg) {
        List<Quadruple> right = p.expr_.accept(this, arg);
        List<Quadruple> quadruples = new ArrayList<>(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Int()), new Quadruple.LLVMOperation.NEG(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Not p, Scope arg) {
        List<Quadruple> right = p.expr_.accept(this, arg);
        List<Quadruple> quadruples = new ArrayList<>(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool()), new Quadruple.LLVMOperation.NOT(quadruples.get(quadruples.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EMul p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Int()), new Quadruple.LLVMOperation.MUL(p.mulop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAdd p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), left.get(left.size() - 1).getRegister().type), new Quadruple.LLVMOperation.ADD(p.addop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(ERel p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAnd p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool()), new Quadruple.LLVMOperation.AND(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(EOr p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Quadruple(new Register("tmp" + arg.getRegisterNumber("tmp"), new Bool()), new Quadruple.LLVMOperation.OR(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }
}
