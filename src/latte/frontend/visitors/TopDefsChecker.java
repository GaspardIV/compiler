package latte.frontend.visitors;

import latte.Absyn.*;
import latte.Internal.ClField;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.lang.Void;
import java.util.HashSet;

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
            if (p.type_ instanceof Array && ((Array) p.type_).type_ instanceof latte.Absyn.Void) {
                throw new SemanticError.ArrayOfVoid(p.line_num);
            }

            if (p.type_ instanceof Array && ((Array) p.type_).type_ instanceof latte.Absyn.Class) {
                String className = ((latte.Absyn.Class) ((Array) p.type_).type_).ident_;
                if (arg.getClassDef(className) == null) {
                    throw new SemanticError.ClassNotDeclared(p.line_num, className);
                }
            }

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

            // check if no method redefinitions inside of class
            ClBlk block = (ClBlk) p.clblock_;
            ListClMember members = block.listclmember_;
            HashSet<String> membersIdents = new HashSet<>();
            for (ClMember member : members) {
                if (member instanceof ClMethod) {
                    ClMethod method = (ClMethod) member;
                    if (membersIdents.contains(method.ident_)) {
                        throw new SemanticError.MethodAlreadyDeclaredInCurrentContext(p.line_num, method.ident_);
                    }
                    membersIdents.add(method.ident_);
                }
            }
        }
    }

    public static class TopDefDefinitionCheckVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
        public Void visit(FnDef p, Environment environment) {
            return visitFunctionLikeDefinition(environment, p.ident_, p.listarg_, p.line_num, p.type_, p.block_);
        }

        public Void visit(ClDef p, Environment environment) {
            environment.addNewContext("class_" + p.ident_);
            environment.addVariableWithErrorCheck("self", new latte.Absyn.Class(p.ident_), p.line_num);
            environment.getClassDef(p.ident_).classDef.clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }

        public Void visit(ClDefExt p, Environment environment) {
            environment.addNewContext("class_" + p.ident_1);
            environment.addVariableWithErrorCheck("self", new latte.Absyn.Class(p.ident_1), p.line_num);
            environment.getClassDef(p.ident_1).classDef.clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
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
            arg.addVariableWithErrorCheck(p.ident_, p.type_, p.line_num);
            return null;
        }

        @Override
        public Void visit(ClFields p, Environment arg) {
            for (ClFieldItem x : p.listclfielditem_) {
                arg.addVariableWithErrorCheck(((ClFieldItemNoInit) x).ident_, p.type_, p.line_num);
            }
            return null;
        }

        public Void visit(ClMethod p, Environment arg) {
            FnDef fnDef = new FnDef(p.type_, p.ident_, p.listarg_, p.block_);
            fnDef.line_num = p.line_num;
//            if (arg.isFunctionGlobal(p.ident_)) {
//                throw new SemanticError.FunctionAlreadyDeclared(p.line_num, p.ident_);
//            }
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
            environment.addVariableWithErrorCheck(arg.ident_, arg.type_, arg.line_num);
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
