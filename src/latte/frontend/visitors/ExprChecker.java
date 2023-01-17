package latte.frontend.visitors;

import latte.Absyn.Class;
import latte.Absyn.*;
import latte.Absyn.Void;
import latte.Internal.ClField;
import latte.Internal.Null;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;
import latte.utils.Utils;

public class ExprChecker implements latte.Absyn.Expr.Visitor<Type, Environment> {
    public Type visit(ENewArray p, Environment arg) {
        Type size = p.expr_.accept(new ExprChecker(), arg);
        if (!size.equals(new Int())) {
            throw new SemanticError.SizeOfArrayMustBeInt(p.line_num);
        }

        arg.checkArrayType(p.type_, p.line_num);
        return new Array(p.type_);
    }

//    public Type visit(EArrayElem p, Environment arg) {
//        Type index = p.expr_2.accept(new ExprChecker(), arg);
//        if (!index.equals(new Int())) {
//            throw new SemanticError.ArrayIndexMustBeInt(p.line_num);
//        }
//        Type type = p.expr_1.accept(new ExprChecker(), arg);
//        if (!(type instanceof Array)) {
//            throw new SemanticError.ArrayElementAccessedOnNonArray(p.line_num);
//
//        }
//        Array arrayType = (Array) type;
//        return arrayType.type_;
//    }

    public Type visit(ENew p, Environment arg) {
        if (arg.getClassDef(p.ident_) == null) {
            throw new SemanticError.ClassDoesNotExist(p.line_num);
        }
        return new latte.Absyn.Class(p.ident_);
    }

    public Type visit(EMethod p, Environment arg) {
        ClMethod method;
        try {
            latte.Absyn.Class classExprT = (latte.Absyn.Class) p.expr_.accept(new ExprChecker(), arg);
            method = arg.getClassDef(classExprT.ident_).getMethod(p.ident_);
        } catch (Exception e) {
            throw new SemanticError.MethodCalledOnNonClass(p.line_num);
        }

        if (method == null) {
            throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
        }


        return visitFunctionLikeCallExpression(arg, p.line_num, p.ident_, method.listarg_, p.listexpr_, method.type_);
    }

    public Type visit(EField p, Environment arg) {
        ClField field;

        Type exprT = p.expr_.accept(new ExprChecker(), arg);
        if (exprT instanceof Array && arg.buildInArrayFields().containsKey(p.ident_)) {
            return arg.buildInArrayFields().get(p.ident_);
        } else {
            try {
                latte.Absyn.Class classExprT = (Class) exprT;
                field = arg.getClassDef(classExprT.ident_).getField(p.ident_);
                if (field == null) {
                    throw new SemanticError.FieldDoesNotExist(p.line_num);
                }
            } catch (NullPointerException | ClassCastException e) {
                throw new SemanticError.FieldCalledOnNonClass(p.line_num);
            }
        }
        return field.type_;
    }

    public Type visit(EVar p, Environment arg) {
        if (arg.getVarType(p.ident_) == null) {
            throw new SemanticError.VariableNotDeclared(p.line_num, p.ident_);
        }
        return arg.getVarType(p.ident_);
    }

    public Type visit(ELitInt p, Environment arg) {
        return new Int();
    }

    public Type visit(ELitTrue p, Environment arg) {
        return new Bool();
    }

    public Type visit(ELitFalse p, Environment arg) {
        return new Bool();
    }

    public Type visit(EApp p, Environment arg) {
        FnDef fnDef = arg.getFunction(p.ident_);
        if (fnDef == null) {
            throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
        }
        return visitFunctionLikeCallExpression(arg, p.line_num, p.ident_, fnDef.listarg_, p.listexpr_, fnDef.type_);
    }

    public Type visit(EString p, Environment arg) {
        return new Str();
    }

    public Type visit(Neg p, Environment arg) {
        Type t = p.expr_.accept(new ExprChecker(), arg);
        if (!t.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "-", t);
        }
        return new Int();
    }

    public Type visit(Not p, Environment arg) {
        Type t = p.expr_.accept(new ExprChecker(), arg);
        if (!t.equals(new Bool())) {
            throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "!", t);
        }
        return new Bool();
    }

    public Type visit(EMul p, Environment arg) {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!t1.equals(new Int())) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, Utils.toString(p.mulop_), t1, t2);
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, Utils.toString(p.mulop_), t1, t2);
        }
        return t1;
    }

    public Type visit(EAdd p, Environment arg) {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (!(t1.equals(new Int()))) {
            if (!p.addop_.equals(new Plus()) || !(t1.equals(new Str()))) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, Utils.toString(p.addop_), t1, t2);
            }
        }
        if (!t1.equals(t2)) {
            throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, Utils.toString(p.addop_), t1, t2);
        }
        return t1;
    }

    public Type visit(ERel p, Environment arg) {
        Type t1 = p.expr_1.accept(new ExprChecker(), arg);
        Type t2 = p.expr_2.accept(new ExprChecker(), arg);
        if (t1.equals(new Void()) || t2.equals(new Void())) {
            throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
        }
        if (!t1.equals(new Int()) && (!p.relop_.equals(new EQU()) && !p.relop_.equals(new NE()))) {
            throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
        }
        if (!arg.areTypesEqualRegardingInheritance(t1, t2) && !arg.areTypesEqualRegardingInheritance(t2, t1)) {
            throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
        }
        return new Bool();
    }

    public Type visit(EAnd p, Environment arg) {
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

    public Type visit(EOr p, Environment arg) {
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

    @Override
    public Type visit(EArrayElemR p, Environment arg) {
        Type index = p.expr_.accept(new ExprChecker(), arg);
        if (!index.equals(new Int())) {
            throw new SemanticError.ArrayIndexMustBeInt(p.line_num);
        }
        Type type = arg.getVarType(p.ident_);
        if (!(type instanceof Array)) {
            throw new SemanticError.ArrayVariableExpected(p.line_num, p.ident_);
        }
        Array arrayType = (Array) type;
        return arrayType.type_;
    }

    @Override
    public Type visit(ENullArr eNullArr, Environment arg) {
        arg.checkArrayType(eNullArr.type_, eNullArr.line_num);
        return new Array(eNullArr.type_);
    }



    @Override
    public Type visit(ENull p, Environment arg) {
        if (arg.getClassDef(p.ident_) == null) {
            throw new SemanticError.ClassDoesNotExist(p.line_num); // todo add class name (ident_)
        }
        return new latte.Absyn.Class(p.ident_);
    }

    @Override
    public Type visit(ENil p, Environment arg) {
        return new Null();
    }

    /**
     * checks if function or method is correctly called
     */
    private Type visitFunctionLikeCallExpression(Environment arg, int line_num, String ident_, ListArg listarg_, ListExpr listexpr_, Type type_) {

        if (arg.isFunctionGlobalErrorFunction(ident_)) {
            arg.setWasReturn(true);
        }

        ListArg argExpectedTypes = listarg_;
        if (argExpectedTypes.size() != listexpr_.size()) {
            throw new SemanticError.WrongNumberOfArgument(line_num, argExpectedTypes.size(), listexpr_.size());
        }

        int i = 0;
        for (Expr x : listexpr_) {
            Type argT = x.accept(new ExprChecker(), arg);
            Type expected = ((Ar) argExpectedTypes.get(i++)).type_;
            if (!arg.areTypesEqualRegardingInheritance(argT, expected)) {
                throw new SemanticError.ArgTypesDoesNotMatch(line_num, i, expected, argT);
            }
        }
        return type_;
    }
}