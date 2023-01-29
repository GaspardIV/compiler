package latte.frontend;

import latte.Absyn.Program;
import latte.frontend.environment.Environment;
import latte.frontend.visitors.ProgramChecker;

public class SematicAnalyst {
    public latte.backend.program.Program checkTypes(Program ast)  {
        Environment environment = new Environment();
        ProgramChecker programVisitor = new ProgramChecker();
        return ast.accept(programVisitor, environment);
    }
}