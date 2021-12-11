package latte.frontend;

import latte.Absyn.*;
import latte.errors.SemanticError;
import latte.frontend.visitors.ProgramChecker;
import latte.frontend.environment.Environment;

public class SematicAnalyst {
    public void checkTypes(Program ast) throws SemanticError {
        Environment environment = new Environment();
        ProgramChecker programVisitor = new ProgramChecker();
        ast.accept(programVisitor, environment);
    }
}
