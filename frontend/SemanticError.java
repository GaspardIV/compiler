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
}
