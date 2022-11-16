package latte.frontend.visitors;

import latte.Absyn.Prog;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

public class ProgramChecker implements latte.Absyn.Program.Visitor<Void, Environment> {
    public Void visit(Prog p, Environment arg) throws SemanticError {
        createGlobalContext(p, arg);
        checkTopDefs(p, arg);

//        todo: tutaj dobre miejsce na zwijanie stalych i usuwanie martwego kodu
        // konflikty reduce reduce sa nieakceptowalne!!!
        return null;
    }

    private void createGlobalContext(Prog p, Environment arg) throws SemanticError {
        for (latte.Absyn.TopDef x : p.listtopdef_) {
            x.accept(new TopDefsChecker.TopDefDeclarationCheckVisitor(), arg);
        }
        checkMain(arg, p.line_num);
        fillClassesWithExtends(arg);
    }

    private void checkTopDefs(Prog p, Environment arg) throws SemanticError {
        for (latte.Absyn.TopDef x : p.listtopdef_) {
            x.accept(new TopDefsChecker.TopDefDefinitionCheckVisitor(), arg);
        }
    }

    private void checkMain(Environment arg, int line_num) throws SemanticError.NoMain {
        if (arg.getFunction("main") == null) {
            throw new SemanticError.NoMain(line_num);
        }
    }

    private void fillClassesWithExtends(Environment arg) throws SemanticError {
        arg.initInheristance();
    }
}

