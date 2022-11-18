package latte.frontend;

import latte.Absyn.Program;
import latte.frontend.environment.Environment;
import latte.frontend.visitors.ProgramChecker;

public class SematicAnalyst {
    public void checkTypes(Program ast) throws Exception {
        Environment environment = new Environment();
        ProgramChecker programVisitor = new ProgramChecker();
        ast.accept(programVisitor, environment);
    }
}
