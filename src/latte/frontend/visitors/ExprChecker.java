package latte.frontend.visitors;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

public class ExprChecker implements latte.Absyn.Expr.Visitor<Type, Environment> {
    public Type visit(ENewArray p, Environment arg) throws SemanticError {
        Type size = p.expr_.accept(new ExprChecker(), arg);
        if (!size.equals(new Int())) {
            throw new SemanticError(p.line_num, "Size has to be an Integer.");
        }
        return new Array(p.type_);
    }

    public Type visit(EArrayElem p, Environment arg) throws SemanticError {
        Type index = p.expr_.accept(new ExprChecker(), arg);
        if (!index.equals(new Int())) {
            throw new SemanticError(p.line_num, "Index has to be an Integer.");
        }
        Array arrayType = (Array) arg.getVarType(p.ident_);
        return arrayType.type_;
    }

    public Type visit(ENew p, Environment arg) throws SemanticError {
        if (arg.getClassDef(p.ident_) == null) {
            throw new SemanticError.ClassDoesNotExist(p.line_num);
        }
        return new latte.Absyn.Class(p.ident_);
    }

    public Type visit(EMethod p, Environment arg) throws SemanticError {
        ClMethod method;
        try {
            latte.Absyn.Class classExprT = (latte.Absyn.Class) p.expr_.accept(new ExprChecker(), arg);
            method = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getMethod(p.ident_);
        } catch (Exception e) {
            throw new SemanticError(p.line_num, "Method can only be applied to an object.");
        }

        if (method == null) {
            throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
        }

        ListArg argExpectedTypes = method.listarg_;
        if (argExpectedTypes.size() != p.listexpr_.size()) {
            throw new SemanticError.WrongNumberOfArgument(p.line_num, argExpectedTypes.size(), p.listexpr_.size());
        }

        int i = 0;
        for (latte.Absyn.Expr x : p.listexpr_) {
            Type argT = x.accept(new ExprChecker(), arg);
            Type expected = ((Ar) argExpectedTypes.get(i++)).type_;
            if (!argT.equals(expected)) {
                throw new SemanticError.ArgTypesDoesNotMatch(p.line_num, i, expected, argT);
            }
        }
        return method.type_;
    }

    public Type visit(EField p, Environment arg) throws SemanticError {
        ClField field;

        Type exprT = p.expr_.accept(new ExprChecker(), arg);
        if ((new Array(null)).equalsT(exprT) && arg.buildInArrayFields().containsKey(p.ident_)) {
            return arg.buildInArrayFields().get(p.ident_);
        } else {
            try {
                latte.Absyn.Class classExprT = (Class) exprT;
                field = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getField(p.ident_);
                if (field == null) {
                    throw new SemanticError.FieldDoesNotExist(p.line_num);
                }
            } catch (ClassCastException e) {
                throw new SemanticError(p.line_num, "Field operator can only be applied to an object.");
            }
        }
        return field.type_;
    }

    public Type visit(EVar p, Environment arg) throws SemanticError {
        if (arg.getVarType(p.ident_) == null) {
            throw new SemanticError.VariableNotDeclared(p.line_num, p.ident_);
        }
        return arg.getVarType(p.ident_);
    }

    public Type visit(ELitInt p, Environment arg) {
        return new Int();
    }

    public Type visit(ELitTrue p, Environment arg) {
        Bool res = new Bool();
        res.isLitTrue = true;
        return res;
    }

    public Type visit(ELitFalse p, Environment arg) {
        Bool res = new Bool();
        res.isLitFalse = true;
        return res;
    }

    public Type visit(EApp p, Environment arg) throws SemanticError {
        FnDef fnDef = arg.getFunction(p.ident_);
        if (fnDef == null) {
            throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
        }

        ListArg argExpectedTypes = fnDef.listarg_;
        if (argExpectedTypes.size() != p.listexpr_.size()) {
            throw new SemanticError.WrongNumberOfArgument(p.line_num, argExpectedTypes.size(), p.listexpr_.size());
        }
        int i = 0;
        for (latte.Absyn.Expr x : p.listexpr_) {
            Type argT = x.accept(new ExprChecker(), arg);
            Type expected = ((Ar) argExpectedTypes.get(i++)).type_;
            if (!argT.equals(expected)) {
                throw new SemanticError.ArgTypesDoesNotMatch(p.line_num, i, expected, argT);
            }
        }
        return fnDef.type_;
    }

    public Type visit(EString p, Environment arg) {
        return new Str();
    }

    public Type visit(Neg p, Environment arg) throws SemanticError {
        Type t = p.expr_.accept(new ExprChecker(), arg);
        if (!t.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "-", t);
        }
        return new Int();
    }

    public Type visit(Not p, Environment arg) throws SemanticError {
        Type t = p.expr_.accept(new ExprChecker(), arg);
        if (!t.equals(new Bool())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "!", t);
        }
        return new Bool();
    }

    public Type visit(EMul p, Environment arg) throws SemanticError {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!t1.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "*", t1, t2);
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "*", t1, t2);
        }
        return t1;
    }

    public Type visit(EAdd p, Environment arg) throws SemanticError {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!(t1.equals(new Int()))) {
            if (!p.addop_.equals(new Plus()) || !(t1.equals(new Str()))) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, p.addop_.toString(), t1, t2);
            }
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, p.addop_.toString(), t1, t2);
        }
        return t1;
    }

    public Type visit(ERel p, Environment arg) throws SemanticError {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!t1.equals(new Int()) && !p.relop_.equals(new EQU())) {
            throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
        }
        return new Bool();
    }

    public Type visit(EAnd p, Environment arg) throws SemanticError {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!t1.equals(new Bool())) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "&&", t1, t2);
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "&&", t1, t2);
        }
        return new Bool();
    }

    public Type visit(EOr p, Environment arg) throws SemanticError {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!t1.equals(new Bool())) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "||", t1, t2);
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "||", t1, t2);
        }
        return new Bool();
    }
}