package latte.frontend.visitors;

import latte.Absyn.FnDef;
import latte.Absyn.Int;
import latte.Absyn.Prog;
import latte.backend.program.Program;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;
import latte.backend.programvisitors.ProgramVisitor;

public class ProgramChecker implements latte.Absyn.Program.Visitor<Program, Environment> {
    public Program visit(Prog p, Environment arg) {
        createGlobalContext(p, arg);
        checkTopDefs(p, arg);
        return createProgram(p, arg);
    }

    private Program createProgram(Prog p, Environment arg) {
        Program program = new Program();
        ProgramVisitor programVisitor = new ProgramVisitor(arg, program);
        for (latte.Absyn.TopDef x : p.listtopdef_) {
            x.accept(programVisitor, null);
        }
        return program;
    }

    private void createGlobalContext(Prog p, Environment arg) {
        for (latte.Absyn.TopDef x : p.listtopdef_) {
            x.accept(new TopDefsChecker.TopDefDeclarationCheckVisitor(), arg);
        }
        checkMain(arg, p.line_num);
        fillClassesWithExtends(arg);
    }

    private void checkTopDefs(Prog p, Environment arg) {
        for (latte.Absyn.TopDef x : p.listtopdef_) {
            x.accept(new TopDefsChecker.TopDefDefinitionCheckVisitor(), arg);
        }
    }

    private void checkMain(Environment arg, int line_num) throws SemanticError.NoMain {
        FnDef main = arg.getFunction("main");
        if (main == null) {
            throw new SemanticError.NoMain(line_num);
        }
        if (main.listarg_.size() != 0) {
            throw new SemanticError.MainWithArgs(line_num);
        }
        if (!main.type_.equals(new Int())) {
            throw new SemanticError.MainNotInt(line_num);
        }
    }

    private void fillClassesWithExtends(Environment arg) {
        arg.initInheristance();
    }
}

