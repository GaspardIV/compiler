package latte.frontend.visitors;

import latte.Absyn.*;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.lang.Void;

public abstract class TopDefsChecker {
    public static class TopDefDeclarationCheckVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
        public Void visit(FnDef p, Environment arg) throws SemanticError {
            checkIfFunctionAlreadyDefined(p, arg);
            addFunctionDef(p, arg);
            return null;
        }

        public Void visit(ClDef p, Environment arg) throws SemanticError {
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

        private void checkIfFunctionAlreadyDefined(FnDef p, Environment arg) throws SemanticError {
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
        public Void visit(FnDef p, Environment environment) throws SemanticError {
            environment.addNewContext(p.ident_);
            for (latte.Absyn.Arg x : p.listarg_) {
                Ar arg = (Ar) x;
                if (environment.actContextContainsVar(arg.ident_)) {
                    throw new SemanticError.VariableAlreadyDeclared(p.line_num, arg.ident_);
                }
                environment.addVariable(arg.ident_, arg.type_);
            }
            environment.setExpectedReturnType(p.type_);
            p.block_.accept(new BlockVisitor(), environment);
            if (!environment.wasReturn() && !p.type_.equals(new latte.Absyn.Void())) {
                throw new SemanticError.FunctionWithoutReturn(p.line_num, p.ident_);
            }
            environment.popContext();
            return null;
        }

        public Void visit(ClDef p, Environment environment) throws SemanticError {
            environment.addNewContext("class_" + p.ident_);
            environment.addVariable("this", new latte.Absyn.Class(p.ident_));
            environment.getClassDef(p.ident_).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }

        public Void visit(ClDefExt p, Environment environment) throws SemanticError {
            environment.addNewContext("class_" + p.ident_1);
            environment.addVariable("this", new latte.Absyn.Class(p.ident_1));
            environment.getClassDef(p.ident_1).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }
    }

    public static class ClBlockTypeCheckVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) throws SemanticError {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberTypeCheckVisitor(), arg);
            }
            return null;
        }
    }

    public static class ClBlockInitFieldsInEnvironmentVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) throws SemanticError {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberInitFieldsInEnvironmentVisitor(), arg);
            }
            return null;
        }
    }

    public static class ClMemberInitFieldsInEnvironmentVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
        public Void visit(ClField p, Environment arg) {
            arg.addVariable(p.ident_, p.type_);
            return null;
        }

        public Void visit(ClMethod p, Environment arg) throws SemanticError {
            FnDef fnDef = new FnDef(p.type_, p.ident_, p.listarg_, p.block_);
            fnDef.line_num = p.line_num;
            arg.addFunction(p.ident_, fnDef);
            return null;
        }
    }

    public static class ClMemberTypeCheckVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
        public Void visit(ClField p, Environment arg) {
            return null;
        }

        public Void visit(ClMethod p, Environment environment) throws SemanticError {
            environment.addNewContext(p.ident_);
            for (latte.Absyn.Arg x : p.listarg_) {
                Ar arg = (Ar) x;
                if (environment.actContextContainsVar(arg.ident_)) {
                    throw new SemanticError.VariableAlreadyDeclared(p.line_num, arg.ident_);
                }
                environment.addVariable(arg.ident_, arg.type_);
            }
            environment.setExpectedReturnType(p.type_);
            p.block_.accept(new BlockVisitor(), environment);
            if (!environment.wasReturn() && !p.type_.equals(new latte.Absyn.Void())) {
                throw new SemanticError.FunctionWithoutReturn(p.line_num, p.ident_);
            }
            environment.popContext();
            return null;
        }
    }

    public static class BlockVisitor implements latte.Absyn.Block.Visitor<Void, Environment> {
        public Void visit(Blk p, Environment arg) throws SemanticError {
            for (latte.Absyn.Stmt x : p.liststmt_) {
                x.accept(new StmtChecker(), arg);
            }
            return null;
        }
    }
}
