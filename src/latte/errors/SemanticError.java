package latte.errors;

import latte.Absyn.Type;
import latte.utils.Utils;

public class SemanticError extends RuntimeException {
    private final int lineNum;

    public SemanticError(int lineNum, String error) {
        super(error);
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public static class FunctionAlreadyDeclared extends SemanticError {
        public FunctionAlreadyDeclared(int line_num, String ident_) {
            super(line_num, "Function '" + ident_ + "' already declared in this scope.");
        }
    }

    public static class NoMain extends SemanticError {
        public NoMain(int line_num) {
            super(line_num, "No 'main' method found.");
        }
    }

    public static class ClassAlreadyDeclared extends SemanticError {
        public ClassAlreadyDeclared(int line_num, String ident_) {
            super(line_num, "Class '" + ident_ + "' already declared in this scope.");
        }
    }

    public static class VariableNotDeclared extends SemanticError {
        public VariableNotDeclared(int line_num, String ident_) {
            super(line_num, "Variable '" + ident_ + "' not declared in this scope.");
        }
    }

    public static class ArrayIndexHasToBeInteger extends SemanticError {
        public ArrayIndexHasToBeInteger(int line_num) {
            super(line_num, "Arrays must be indexed by int values.");
        }
    }

    public static class FunctionNotDeclaredInThisScope extends SemanticError {
        public FunctionNotDeclaredInThisScope(int line_num, String ident_) {
            super(line_num, "Function '" + ident_ + "' not declared in this scope.");
        }
    }

    public static class WrongNumberOfArgument extends SemanticError {
        public WrongNumberOfArgument(int line_num, int expected, int actual) {
            super(line_num, "Wrong number of arguments. Got " + actual + ", and " + expected + " was expected.");
        }
    }

    public static class ClassDoesNotExist extends SemanticError {
        public ClassDoesNotExist(int line_num) {
            super(line_num, "Class does not exist.");
        }
    }

    public static class FieldDoesNotExist extends SemanticError {
        public FieldDoesNotExist(int line_num) {
            super(line_num, "Field does not exist.");
        }
    }

    public static class WrongReturnType extends SemanticError {
        public WrongReturnType(int line_num, Type expected, Type actual) {
            super(line_num, "Wrong return type. Got '" + Utils.toString(actual) + "', but '" + Utils.toString(expected) + "' was expected.");
        }
    }

    public static class CondHasToBeBoolean extends SemanticError {
        public CondHasToBeBoolean(int line_num) {
            super(line_num, "Condition has to be boolean");
        }
    }

    public static class FunctionWithoutReturn extends SemanticError {
        public FunctionWithoutReturn(int lineNum, String ident_) {
            super(lineNum, "Function " + ident_ + " without return.");
        }
    }

    public static class ForEachCanBeAppliedToArraysOnly extends SemanticError {
        public ForEachCanBeAppliedToArraysOnly(int line_num) {
            super(line_num, "For each loop can be applied to arrays only.");
        }
    }

    public static class OperatorCannotBeAppliedToTypes extends SemanticError {
        public OperatorCannotBeAppliedToTypes(int line_num, String s, Type t1, Type t2) {
            super(line_num, "Operator '" + s + "' cannot be applied to types '" + Utils.toString(t1) + "' and '" + Utils.toString(t2) + "'.");
        }
    }

    public static class OperatorCannotBeAppliedToType extends SemanticError {
        public OperatorCannotBeAppliedToType(int line_num, String s, Type t) {
            super(line_num, "Operator '" + s + "' cannot be applied to type '" + Utils.toString(t) + "'.");
        }
    }

    public static class RelOperatorCannotBeAppliedToTypes extends SemanticError {
        public RelOperatorCannotBeAppliedToTypes(int line_num, Type t1, Type t2) {
            super(line_num, "Cannot compare '" + Utils.toString(t1) + "' and '" + Utils.toString(t2) + "'.");
        }
    }

    public static class ArgTypesDoesNotMatch extends SemanticError {
        public ArgTypesDoesNotMatch(int line_num, int i, Type expected, Type argT) {
            super(line_num, "" + i + " argument type does not match. Got '" + Utils.toString(argT) + "', but '" + Utils.toString(expected) + "' was expected.");
        }
    }

    public static class AssingingWrongType extends SemanticError {
        public AssingingWrongType(int line_num, Type expected, Type actual) {
            super(line_num, "Assigning '" + Utils.toString(actual) + "' to '" + Utils.toString(expected) + "' variable.");
        }
    }

    public static class ClassNotDeclared extends SemanticError {
        public ClassNotDeclared(int line_num, String ident_) {
            super(line_num, "Class '" + ident_ + "' not declared.");
        }
    }

    public static class MainNotInt extends SemanticError {
        public MainNotInt(int line_num) {
            super(line_num, "Main function must return int.");
        }
    }

    public static class MainWithArgs extends SemanticError {
        public MainWithArgs(int line_num) {
            super(line_num, "Main function can't have arguments.");
        }
    }

    public static class ReturningVoid extends SemanticError {
        public ReturningVoid(int line_num) {
            super(line_num, "Returning void is not allowed.");
        }
    }

    public static class ArrayOfVoid extends SemanticError {
        public ArrayOfVoid(int line_num) {
            super(line_num, "Array of void is not allowed.");
        }
    }

    public static class FieldAlreadyDeclaredInCurrentContext extends SemanticError {
        public FieldAlreadyDeclaredInCurrentContext(int line_num, String ident_) {
            super(line_num, "Field '" + ident_ + "' already declared in this scope.");
        }
    }

    public static class MethodAlreadyDeclaredInCurrentContext extends SemanticError {
        public MethodAlreadyDeclaredInCurrentContext(int line_num, String ident_) {
            super(line_num, "Method '" + ident_ + "' already declared in this scope.");
        }
    }

    public static class VariableWithVoidType extends SemanticError {
        public VariableWithVoidType(int line_num, String ident_) {
            super(line_num, "Variable '" + ident_ + "' has void type.");
        }
    }

    public static class SizeOfArrayMustBeInt extends SemanticError {
        public SizeOfArrayMustBeInt(int line_num) {
            super(line_num, "Size of array must be int.");
        }
    }

    public static class ArrayIndexMustBeInt extends SemanticError {
        public ArrayIndexMustBeInt(int line_num) {
            super(line_num, "Array index must be int.");
        }
    }

    public static class ArrayElementAccessedOnNonArray extends SemanticError {
        public ArrayElementAccessedOnNonArray(int line_num) {
            super(line_num, "Array element accessed on non-array.");
        }
    }

    public static class InheritanceLoop extends SemanticError {
        public InheritanceLoop(int line_num, String ident_) {
            super(line_num, "Inheritance loop detected. Class '" + ident_ + "' inherits from itself.");
        }
    }

    public static class MethodCalledOnNonClass extends SemanticError {
        public MethodCalledOnNonClass(int line_num) {
            super(line_num, "Method called on non-class.");
        }
    }

    public static class FieldCalledOnNonClass extends SemanticError {
        public FieldCalledOnNonClass(int line_num) {
            super(line_num, "Field called on non-class.");
        }
    }

    public static class ArrayVariableExpected extends SemanticError {
        public ArrayVariableExpected(int line_num, String ident_) {
            super(line_num, "Array variable expected. '" + ident_ + "' is not an array.");
        }
    }

    public static class AssignmentToNonLValue extends SemanticError {
        public AssignmentToNonLValue(int line_num) {
            super(line_num, "Invalid left side of assignment. Assignment to non-lvalue.");
        }
    }

    public static class IncrementingNonLValue extends SemanticError {
        public IncrementingNonLValue(int line_num) {
            super(line_num, "Invalid left side of increment. Incrementing non-lvalue.");
        }
    }

    public static class DecrementingNonLValue extends SemanticError {
        public DecrementingNonLValue(int line_num) {
            super(line_num, "Invalid left side of decrement. Decrementing non-lvalue.");
        }
    }

    public static class VariableAlreadyDeclaredInCurrentContext extends SemanticError {
        public VariableAlreadyDeclaredInCurrentContext(int line_num, String ident_) {
            super(line_num, "'" + ident_ + "' already declared in this scope.");
        }
    }

    public static class NewOnNonClass extends SemanticError {
        public NewOnNonClass(int line_num) {
            super(line_num, "New on non-class.");
        }
    }

    public static class OverriddenMethodSignatureDoesNotMatchWitchBase extends SemanticError {
        public OverriddenMethodSignatureDoesNotMatchWitchBase(int line_num, String ident_) {
            super(line_num, "Overridden method '" + ident_ + "' signature does not match with base.");
        }
    }
}
