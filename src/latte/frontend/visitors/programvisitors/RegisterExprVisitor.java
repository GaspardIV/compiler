package latte.frontend.visitors.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.backend.quadruple.Value;
import latte.frontend.environment.Environment;
import latte.frontend.visitors.ExprChecker;

import javax.lang.model.type.TypeVisitor;
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
        quadruples.add(new Register(p.ident_+arg.getRegisterNumber(p.ident_)));
        return quadruples;
//        return new Arrays.fromArray(new Quadruple[] {
//                new Quadruple(Quadruple.Opcode.LOAD, p.ident_, null, null)
//        });
//        return returp.ident_+arg.getRegisterNumber(p.ident_);
    }

    @Override
    public List<Quadruple> visit(ELitInt p, Scope arg) {
        return Collections.singletonList(new Value(p.integer_.toString()));
    }

    @Override
    public List<Quadruple> visit(ELitTrue p, Scope arg) {
        return Collections.singletonList(new Value("1"));
    }

    @Override
    public List<Quadruple> visit(ELitFalse p, Scope arg) {
        return Collections.singletonList(new Value("0"));
    }

    @Override
    public List<Quadruple> visit(EApp p, Scope arg) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EString p, Scope arg) {
        return Collections.singletonList(new Value(p.string_));
    }

    @Override
    public List<Quadruple> visit(Neg p, Scope arg) {
        return Collections.singletonList(new Value("-" + p.expr_.accept(this, arg)));
    }

    @Override
    public List<Quadruple> visit(Not p, Scope arg) {
        return Collections.singletonList(new Value("!" + p.expr_.accept(this, arg)));
    }

    @Override
    public List<Quadruple> visit(EMul p, Scope arg) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Register(left.get(left.size()-1).toString() + p.mulop_.toString() + right.get(right.size()-1).toString()));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAdd p, Scope arg) {
//        Type type = p.expr_1.accept(new ExprChecker(), new Environment());// todo it has to be filled with environment????
//        p
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, arg);
        List<Quadruple> right = p.expr_2.accept(this, arg);
        quadruples.addAll(left);
        quadruples.addAll(right);
        quadruples.add(new Register(left.get(left.size()-1).toString() + p.addop_.toString() + right.get(right.size()-1).toString()));
        // todo zamiast new Register() trzeba zrobić jakąś funkcję która będzie zwracała register, np jakby kazdy quadralple automatycznie byl typu Q1, Q2, Q3, Q4 i kazdy z nich mial przyjamniej result!!!!!
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(ERel p, Scope arg) {
        return null;
    }

    @Override
    public List<Quadruple> visit(EAnd p, Scope arg) {
        return null;
    }

    @Override
    public List<Quadruple> visit(EOr p, Scope arg) {
        return
    }
}
