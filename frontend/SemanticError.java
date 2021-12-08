package frontend;

public class SemanticError extends Exception {
    private final int lineNum;

    public SemanticError(int lineNum, String error) {
        super(error);
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public static class FunctionAlreadyDeclared extends SemanticError {
        public FunctionAlreadyDeclared(int line_num) {
            super(line_num, "function already declared");
        }
    }

    public static class NoMain extends SemanticError {
        public NoMain(int line_num) {
            super(line_num, "no main method");
        }
    }

    public static class ClassAlreadyDeclared extends SemanticError {
        public ClassAlreadyDeclared(int line_num) {
            super(line_num, "class already declared");
        }
    }

    public static class VariableAlreadyDeclared extends SemanticError {
        public VariableAlreadyDeclared(int line_num) {
            super(line_num, "variable already declared");
        }
    }

    public static class TypesDeasNotMatch extends SemanticError {
        public TypesDeasNotMatch(int line_num) {
            super(line_num, "type doesn't match");
        }
    }

    public static class VariableNotDeclared extends SemanticError {
        public VariableNotDeclared(int line_num) {
            super(line_num, "variable not declared");
        }
    }

    public static class ArrayIndexHasToBeInteger extends SemanticError {
        public ArrayIndexHasToBeInteger(int line_num) {
            super(line_num, "array index has to be an integer");

        }
    }

    public static class FunctionNotDeclared extends SemanticError {
        public FunctionNotDeclared(int line_num) {
            super(line_num, "Function not declared");
        }
    }

    public static class WrongNumberOfArgument extends SemanticError {
        public WrongNumberOfArgument(int line_num) {
            super(line_num, "Wrong number of arguments");
        }
    }

    public static class ClassDoesNotExist extends SemanticError {
        public ClassDoesNotExist(int line_num) {
            super(line_num, "class does not exist");
        }
    }

    public static class FieldDoesNotExist extends SemanticError {
        public FieldDoesNotExist(int line_num) {
            super(line_num, "field does not exist");
        }
    }

    public static class WrongReturnType extends SemanticError {
        public WrongReturnType(int line_num) {
            super(line_num, "wrong return type");
        }
    }

    public static class CondHasToBeBoolean extends SemanticError {
        public CondHasToBeBoolean(int line_num) {
            super(line_num, "cond has to be boolean");
        }
    }

    public static class FunctionWithoutReturn extends SemanticError {
        public FunctionWithoutReturn(int lineNum) {
            super(lineNum, "function without return");
        }
    }

    public static class ForEachCanBeAppliedToArraysOnly extends SemanticError {
        public ForEachCanBeAppliedToArraysOnly(int line_num) {
            super(line_num, "for each can be applied to arrays only");
        }
    }
}
