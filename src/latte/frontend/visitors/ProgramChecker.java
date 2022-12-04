package latte.frontend.visitors;

import latte.Absyn.FnDef;
import latte.Absyn.Int;
import latte.Absyn.Prog;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

public class ProgramChecker implements latte.Absyn.Program.Visitor<Void, Environment> {
    public Void visit(Prog p, Environment arg) {
        createGlobalContext(p, arg);
        checkTopDefs(p, arg);
        return null;
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

