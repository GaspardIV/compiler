package latte.frontend.visitors;

import latte.Absyn.Class;
import latte.Absyn.*;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.lang.Void;

public class StmtChecker implements latte.Absyn.Stmt.Visitor<Void, Environment> {
    public Void visit(latte.Absyn.Empty p, Environment arg) { /* Code for Empty goes here */
        return null;
    }

    public Void visit(latte.Absyn.BStmt p, Environment arg) {
        arg.addNewContext("Block stmt");
        p.block_.accept(new TopDefsChecker.BlockVisitor(), arg);
        Boolean wasReturn = arg.wasReturn();
        arg.popContext();
        if (wasReturn) arg.setWasReturn(true);
        return null;
    }

    public Void visit(latte.Absyn.Decl p, Environment arg) {
        for (latte.Absyn.Item x : p.listitem_) {
            x.accept(new VarDeclChecker(p.type_), arg);
        }
        return null;
    }

    public Void visit(latte.Absyn.Ass p, Environment arg) {
        if (arg.getVarType(p.ident_) == null) {
            throw new SemanticError.VariableNotDeclared(p.line_num, p.ident_);
        }
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        Type varType = arg.getVarType(p.ident_);
        if (!arg.areTypesEqualRegardingInheritance(exprType, varType)) {
            throw new SemanticError.AssingingWrongType(p.line_num, varType, exprType);
        }
        return null;
    }


    public Void visit(latte.Absyn.AssArray p, Environment arg) {
        Type iType = p.expr_1.accept(new ExprChecker(), arg);
        if (!iType.equals(new Int())) {
            throw new SemanticError.ArrayIndexHasToBeInteger(p.line_num);
        }

        Array arrayType = (Array) arg.getVarType(p.ident_);
        Type exprType = p.expr_2.accept(new ExprChecker(), arg);

        if (!arg.areTypesEqualRegardingInheritance(exprType, arrayType.type_)) {
            throw new SemanticError.AssingingWrongType(p.line_num, arrayType, exprType);
        }
        return null;
    }

    public Void visit(latte.Absyn.AssField p, Environment arg) {
        Type assT = p.expr_2.accept(new ExprChecker(), arg);
        try {
            Class classExprT = (Class) p.expr_1.accept(new ExprChecker(), arg);
            ClField field = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getField(p.ident_);
            if (field == null) {
                throw new SemanticError.FieldDoesNotExist(p.line_num);
            }
            if (!arg.areTypesEqualRegardingInheritance(assT, field.type_)) {
                throw new SemanticError.AssingingWrongType(p.line_num, field.type_, assT);
            }
        } catch (ClassCastException e) {
            throw new SemanticError(p.line_num, "Field can only be applied to an object.");
        }
        return null;
    }

    public Void visit(latte.Absyn.Incr p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!(p.expr_ instanceof EVar) && !(p.expr_ instanceof EField) && !(p.expr_ instanceof EArrayElem)) {
            throw new SemanticError(p.line_num, "Increment can only be applied to a variable or a field.");
        }
        if (!exprType.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "++", exprType);
        }
        return null;
    }

    public Void visit(latte.Absyn.Decr p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!(p.expr_ instanceof EVar) && !(p.expr_ instanceof EField) && !(p.expr_ instanceof EArrayElem)) {
            throw new SemanticError(p.line_num, "Decrement can only be applied to a variable or a field.");
        }
        if (!exprType.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "--", exprType);
        }
        return null;
    }

    public Void visit(latte.Absyn.Ret p, Environment arg) {
        Type retType = p.expr_.accept(new ExprChecker(), arg);
        if (retType.equals(new latte.Absyn.Void())) {
            throw new SemanticError.ReturningVoid(p.line_num);
        }
        if (!arg.areTypesEqualRegardingInheritance(retType, arg.getExpectedReturnType())) {
            throw new SemanticError.WrongReturnType(p.line_num, arg.getExpectedReturnType(), retType);
        }
        arg.setWasReturn(true);
        return null;
    }

    public Void visit(latte.Absyn.VRet p, Environment arg) {
        if (!(new latte.Absyn.Void()).equals(arg.getExpectedReturnType())) {
            throw new SemanticError.WrongReturnType(p.line_num, arg.getExpectedReturnType(), new latte.Absyn.Void());
        }
        arg.setWasReturn(true);
        return null;
    }

    public Void visit(latte.Absyn.Cond p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);

        if (!exprType.equals(new Bool())) {
            throw new SemanticError.CondHasToBeBoolean(p.line_num);
        }

        Bool cond = (Bool) exprType;
        if (!cond.isLitFalse) {
            Boolean wasReturnBefore = arg.wasReturn();
            acceptStmtAddBlockIfNeeded(p.stmt_, arg, this);
            if (!cond.isLitTrue) {
                arg.setWasReturn(wasReturnBefore);
            }
        }
        return null;
    }

    public Void visit(latte.Absyn.CondElse p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!exprType.equals(new Bool())) {
            throw new SemanticError.CondHasToBeBoolean(p.line_num);
        }
        Bool cond = (Bool) exprType;
        Boolean wasReturnBefore = arg.wasReturn();
        if (!cond.isLitFalse) {
            arg.setWasReturn(false);
            acceptStmtAddBlockIfNeeded(p.stmt_1, arg, new StmtChecker());
        }
        Boolean wasReturnStmt1 = arg.wasReturn();
        if (!cond.isLitTrue) {
            arg.setWasReturn(false);
            acceptStmtAddBlockIfNeeded(p.stmt_2, arg, new StmtChecker());
        }
        Boolean wasReturnStmt2 = arg.wasReturn();
        if (!cond.isLitFalse && !cond.isLitTrue) {
            arg.setWasReturn(wasReturnBefore || (wasReturnStmt1 && wasReturnStmt2));
        }
        return null;
    }

    public Void visit(latte.Absyn.While p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!exprType.equals(new Bool())) {
            throw new SemanticError.CondHasToBeBoolean(p.line_num);
        }
        acceptStmtAddBlockIfNeeded(p.stmt_, arg, new StmtChecker());
        return null;
    }
    public void acceptStmtAddBlockIfNeeded(Stmt stmt, Environment arg, StmtChecker stmtChecker) {
        if (stmt instanceof BStmt) {
            stmt.accept(stmtChecker, arg);
        } else {
            arg.addNewContext("single stmt block");
            stmt.accept(stmtChecker, arg);
            Boolean wasReturn = arg.wasReturn();
            arg.popContext();
            if (wasReturn) arg.setWasReturn(true);
        }
    }

    public Void visit(latte.Absyn.For p, Environment arg) {
        arg.addNewContext("For");
        Ar iterator = (Ar) p.arg_;
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!(new Array(null)).equalsT(exprType)) {
            throw new SemanticError.ForEachCanBeAppliedToArraysOnly(p.line_num);
        }

        Array array = (Array) exprType;
        if (!arg.areTypesEqualRegardingInheritance(array.type_, iterator.type_)) {
            throw new SemanticError.AssingingWrongType(p.line_num, iterator.type_, array.type_);
        }
        arg.addVariable(iterator.ident_, iterator.type_);
        acceptStmtAddBlockIfNeeded(p.stmt_, arg, new StmtChecker());
        arg.popContext();
        return null;
    }

    public Void visit(latte.Absyn.SExp p, Environment arg) {
        p.expr_.accept(new ExprChecker(), arg);
        return null;
    }
}
