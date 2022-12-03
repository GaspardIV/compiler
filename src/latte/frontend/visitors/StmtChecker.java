package latte.frontend.visitors;

import latte.Absyn.Class;
import latte.Absyn.*;
import latte.Internal.ClField;
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
        Type exprType = p.expr_2.accept(new ExprChecker(), arg);
        if (p.expr_1 instanceof EVar) {
            EVar eVar = (EVar) p.expr_1;
            if (arg.getVarType(eVar.ident_) == null) {
                throw new SemanticError.VariableNotDeclared(p.line_num, eVar.ident_);
            }
            Type varType = arg.getVarType(eVar.ident_);
            if (!arg.areTypesEqualRegardingInheritance(exprType, varType)) {
                throw new SemanticError.AssingingWrongType(p.line_num, varType, exprType);
            }
        } else if (p.expr_1 instanceof EField) {
            EField eField = (EField) p.expr_1;
            try {
                Class classExprT = (Class) eField.expr_.accept(new ExprChecker(), arg);
                ClField field = arg.getClassDef(classExprT.ident_).getField(eField.ident_);

                if (field == null) {
                    throw new SemanticError.FieldDoesNotExist(p.line_num);
                }
                if (!arg.areTypesEqualRegardingInheritance(exprType, field.type_)) {
                    throw new SemanticError.AssingingWrongType(p.line_num, field.type_, exprType);
                }
            } catch (ClassCastException e) {
                throw new SemanticError.FieldCalledOnNonClass(p.line_num);
            }
        } else if (p.expr_1 instanceof EArrayElem || p.expr_1 instanceof EArrayElemR) {
            if (p.expr_1 instanceof EArrayElem) {
                EArrayElem eArrayElem = (EArrayElem) p.expr_1;
                Type iType = eArrayElem.expr_2.accept(new ExprChecker(), arg);
                if (!iType.equals(new Int())) {
                    throw new SemanticError.ArrayIndexHasToBeInteger(p.line_num);
                }
                Type arrayType = eArrayElem.expr_1.accept(new ExprChecker(), arg);
                Array arrayType1 = (Array) arrayType;
                if (!arg.areTypesEqualRegardingInheritance(exprType, arrayType1.type_)) {
                    throw new SemanticError.AssingingWrongType(p.line_num, arrayType1.type_, exprType);
                }
            } else {
                EArrayElemR eArrayElemR = (EArrayElemR) p.expr_1;
                Type iType = eArrayElemR.expr_.accept(new ExprChecker(), arg);
                if (!iType.equals(new Int())) {
                    throw new SemanticError.ArrayIndexHasToBeInteger(p.line_num);
                }
                Array arrayType = (Array) arg.getVarType(eArrayElemR.ident_);
                if (!arg.areTypesEqualRegardingInheritance(exprType, arrayType.type_)) {
                    throw new SemanticError.AssingingWrongType(p.line_num, arrayType.type_, exprType);
                }
            }

            return null;
        } else {
            throw new SemanticError.AssignmentToNonLValue(p.line_num);
        }
        return null;
    }

    public Void visit(latte.Absyn.Incr p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!(p.expr_ instanceof EVar) && !(p.expr_ instanceof EField) && !(p.expr_ instanceof EArrayElem) && !(p.expr_ instanceof EArrayElemR)) {
            throw new SemanticError.IncrementingNonLValue(p.line_num);

        }
        if (!exprType.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "++", exprType);
        }
        return null;
    }

    public Void visit(latte.Absyn.Decr p, Environment arg) {
        Type exprType = p.expr_.accept(new ExprChecker(), arg);
        if (!(p.expr_ instanceof EVar) && !(p.expr_ instanceof EField) && !(p.expr_ instanceof EArrayElem) && !(p.expr_ instanceof EArrayElemR)) {
            throw new SemanticError.DecrementingNonLValue(p.line_num);
        }
        if (!exprType.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, toString(p), exprType);
        }
        return null;
    }

    private String toString(Decr p) {
        return "--";
    }

    private String toString(Incr p) {
        return "++";
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
        boolean isLitTrue = p.expr_ instanceof ELitTrue;
        boolean isLitFalse = p.expr_ instanceof ELitFalse;
        if (!exprType.equals(new Bool())) {
            throw new SemanticError.CondHasToBeBoolean(p.line_num);
        }
        if (!isLitFalse) {
            Boolean wasReturnBefore = arg.wasReturn();
            acceptStmtAddBlockIfNeeded(p.stmt_, arg, this);
            if (!isLitTrue) {
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
        boolean isLitTrue = p.expr_ instanceof ELitTrue;
        boolean isLitFalse = p.expr_ instanceof ELitFalse;
        Boolean wasReturnBefore = arg.wasReturn();
        if (!isLitFalse) {
            arg.setWasReturn(false);
            acceptStmtAddBlockIfNeeded(p.stmt_1, arg, new StmtChecker());
        }
        Boolean wasReturnStmt1 = arg.wasReturn();
        if (!isLitTrue) {
            arg.setWasReturn(false);
            acceptStmtAddBlockIfNeeded(p.stmt_2, arg, new StmtChecker());
        }
        Boolean wasReturnStmt2 = arg.wasReturn();
        if (!isLitFalse && !isLitTrue) {
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
        if (!(exprType instanceof Array)) {
            throw new SemanticError.ForEachCanBeAppliedToArraysOnly(p.line_num);
        }

        Array array = (Array) exprType;
        if (!arg.areTypesEqualRegardingInheritance(array.type_, iterator.type_)) {
            throw new SemanticError.AssingingWrongType(p.line_num, iterator.type_, array.type_);
        }
        arg.addVariableWithErrorCheck(iterator.ident_, iterator.type_, p.line_num);
        acceptStmtAddBlockIfNeeded(p.stmt_, arg, new StmtChecker());
        arg.popContext();
        return null;
    }

    public Void visit(latte.Absyn.SExp p, Environment arg) {
        p.expr_.accept(new ExprChecker(), arg);
        return null;
    }
}
