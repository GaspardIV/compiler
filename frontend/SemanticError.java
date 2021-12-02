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
}
