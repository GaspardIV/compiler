package latte.frontend.visitors.programvisitors;

//import latte.Absyn
import latte.Absyn.*;
import latte.backend.program.global.Scope;
import latte.frontend.environment.Environment;
import latte.frontend.visitors.ExprChecker;

import java.text.MessageFormat;

import static latte.utils.Utils.toLLVMString;

public class StatementVisitor implements Stmt.Visitor<String, Scope> {
    public StatementVisitor(/*Environment environment*/) {
//        this.environment = environment;
    }

//    private final Environment environment;
    // todo arg zamiast Scope to m oze byc env, albo function, albo scope xd? -> zeby miec np return type, wiec mozliwe ze FUN najlepiej xd.
    @Override
    public String visit(Empty p, Scope arg) {
        return " ; empty statement";
    }

    @Override
    public String visit(BStmt p, Scope arg) {
        StringBuilder stringBuilder = new StringBuilder();
        ((Blk)p.block_).liststmt_.forEach(stmt -> stringBuilder.append(stmt.accept(this, arg)));
        return stringBuilder.toString();
    }

    @Override
    public String visit(Decl p, Scope arg) {
        return " ; declaration";
//        return null;
    }

    @Override
    public String visit(Ass p, Scope arg) {
        return " ; assignment";
//        return null;
    }

    @Override
    public String visit(Incr p, Scope arg) {
        return " ; increment";
//        return null;
    }

    @Override
    public String visit(Decr p, Scope arg) {
        return " ; decrement";
//        return null;
    }

    @Override
    public String visit(Ret p, Scope arg) {
        Type type = p.expr_.accept(new ExprChecker(), new Environment()); // todo tu moze byc new environemt prawdopodobnie jezeli niepotrzebne
//        Register register = p.expr_.accept(new ExprVisitor(), arg);
        RegisterExprVisitor registerExprVisitor = new RegisterExprVisitor(/*environment*/);
        return MessageFormat.format("ret {0} {1}", toLLVMString(type), p.expr_.accept(registerExprVisitor, arg));
    }

    @Override
    public String visit(VRet p, Scope arg) {
        return "ret void";
//        return null;
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
        return " ; SEXP";
    }
}
