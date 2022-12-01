package latte.frontend.visitors;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.lang.Void;

public abstract class TopDefsChecker {
    public static class TopDefDeclarationCheckVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
        public Void visit(FnDef p, Environment arg) {
            checkIfFunctionAlreadyDefined(p, arg);
            addFunctionDef(p, arg);
            return null;
        }

        public Void visit(ClDef p, Environment arg) {
            ClDefExt i = new ClDefExt(p.ident_, null, p.clblock_);
            i.line_num = p.line_num;
            checkIfCLassAlreadyDefined(i, arg);
            addClassDef(i, arg);
            return null;
        }

        public Void visit(ClDefExt p, Environment arg) throws SemanticError.ClassAlreadyDeclared {
            checkIfCLassAlreadyDefined(p, arg);
            addClassDef(p, arg);
            return null;
        }

        private void addFunctionDef(FnDef p, Environment arg) {
            arg.addFunction(p.ident_, p);
        }

        private void checkIfFunctionAlreadyDefined(FnDef p, Environment arg) {
            if (arg.getFunction(p.ident_) != null) {
                throw new SemanticError.FunctionAlreadyDeclared(p.line_num, p.ident_);
            }
        }

        private void addClassDef(ClDefExt p, Environment arg) {
            arg.addClassDef(p.ident_1, p);
        }

        private void checkIfCLassAlreadyDefined(ClDefExt p, Environment arg) throws SemanticError.ClassAlreadyDeclared {
            if (arg.getClassDef(p.ident_1) != null) {
                throw new SemanticError.ClassAlreadyDeclared(p.line_num, p.ident_1);
            }
        }
    }

    public static class TopDefDefinitionCheckVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
        public Void visit(FnDef p, Environment environment) {
            return visitFunctionLikeDefinition(environment, p.ident_, p.listarg_, p.line_num, p.type_, p.block_);
        }

        public Void visit(ClDef p, Environment environment) {
            environment.addNewContext("class_" + p.ident_);
            environment.addVariable("self", new latte.Absyn.Class(p.ident_));
            environment.getClassDef(p.ident_).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }

        public Void visit(ClDefExt p, Environment environment) {
            environment.addNewContext("class_" + p.ident_1);
            environment.addVariable("self", new latte.Absyn.Class(p.ident_1));
            environment.getClassDef(p.ident_1).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }
    }

    public static class ClBlockTypeCheckVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberTypeCheckVisitor(), arg);
            }
            return null;
        }
    }

    public static class ClBlockInitFieldsInEnvironmentVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberInitFieldsInEnvironmentVisitor(), arg);
            }
            return null;
        }
    }

    public static class ClMemberInitFieldsInEnvironmentVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
        public Void visit(ClField p, Environment arg) {
            if (arg.currentContextContainsVar(p.ident_)) {
                throw new SemanticError.FieldAlreadyDeclaredInCurrentContext(p.line_num, p.ident_);
            }
            arg.addVariable(p.ident_, p.type_);
            return null;
        }

        @Override
        public Void visit(ClFields p, Environment arg) {
            for (ClFieldItem x : p.listclfielditem_) {
                ClFieldItemNoInit i = (ClFieldItemNoInit) x;
                if (arg.currentContextContainsVar(i.ident_)) {
                    throw new SemanticError.FieldAlreadyDeclaredInCurrentContext(i.line_num, i.ident_);
                }
                arg.addVariable(((ClFieldItemNoInit) x).ident_, p.type_);
            }
            return null;
        }

        public Void visit(ClMethod p, Environment arg) {
            FnDef fnDef = new FnDef(p.type_, p.ident_, p.listarg_, p.block_);
            fnDef.line_num = p.line_num;
            if (arg.isFunctionGlobal(p.ident_)) {
                throw new SemanticError.FunctionAlreadyDeclared(p.line_num, p.ident_);
            }
            if (arg.isFunctionInCurrentContext(p.ident_)) {
                throw new SemanticError.MethodAlreadyDeclaredInCurrentContext(p.line_num, p.ident_);
            }
            arg.addFunction(p.ident_, fnDef);
            return null;
        }
    }

    public static class ClMemberTypeCheckVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
        public Void visit(ClField p, Environment arg) {
            return null;
        }

        @Override
        public Void visit(ClFields p, Environment arg) {
            return null;
        }

        public Void visit(ClMethod p, Environment environment) {
            return visitFunctionLikeDefinition(environment, p.ident_, p.listarg_, p.line_num, p.type_, p.block_);
        }
    }

    public static class BlockVisitor implements latte.Absyn.Block.Visitor<Void, Environment> {
        public Void visit(Blk p, Environment arg) {
            for (latte.Absyn.Stmt x : p.liststmt_) {
                x.accept(new StmtChecker(), arg);
            }
            return null;
        }
    }

    /**
     * Checks if function or method definition is correct
     */
    private static Void visitFunctionLikeDefinition(Environment environment, String ident_, ListArg listarg_, int line_num, Type type_, Block block_) {
        environment.addNewContext(ident_);
        for (Arg x : listarg_) {
            Ar arg = (Ar) x;
            if (environment.currentContextContainsVar(arg.ident_)) {
                throw new SemanticError.VariableAlreadyDeclared(line_num, arg.ident_);
            }
            if (arg.type_ instanceof latte.Absyn.Class) {
                ClDefExt i = environment.getClassDef(((Class) arg.type_).ident_);
                if (i == null) {
                    throw new SemanticError.ClassNotDeclared(line_num, ((Class) arg.type_).ident_);
                }
            }
            if (arg.type_ instanceof latte.Absyn.Void) {
                throw new SemanticError.VoidArgument(line_num, arg.ident_);
            }
            environment.addVariable(arg.ident_, arg.type_);
        }
        environment.setExpectedReturnType(type_);
        block_.accept(new BlockVisitor(), environment);
        if (!environment.wasReturn() && !type_.equals(new latte.Absyn.Void())) {
            throw new SemanticError.FunctionWithoutReturn(line_num, ident_);
        }
        environment.popContext();
        return null;
    }
}
