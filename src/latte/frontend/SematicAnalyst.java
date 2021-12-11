package latte.frontend;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

import java.lang.Void;

public class SematicAnalyst {
    public void checkTypes(Program ast) throws SemanticError {
        Environment environment = new Environment();
        ProgramCheckVisitor programVisitor = new ProgramCheckVisitor();
        ast.accept(programVisitor, environment);
    }

    public class ProgramCheckVisitor implements latte.Absyn.Program.Visitor<Void, Environment> {
        public Void visit(Prog p, Environment arg) throws SemanticError {
            createGlobalContext(p, arg);
            checkTopDefs(p, arg);
            return null;
        }

        private void checkTopDefs(Prog p, Environment arg) throws SemanticError {
            for (latte.Absyn.TopDef x : p.listtopdef_) {
                x.accept(new TopDefCheckVisitor(), arg);
            }
        }

        private void createGlobalContext(Prog p, Environment arg) throws SemanticError {
            for (latte.Absyn.TopDef x : p.listtopdef_) {
                x.accept(new TopDefDeclareVisitor(), arg);
            }
            checkMain(arg, p.line_num);
            fillClassesWithExtends(arg);
        }

        private void fillClassesWithExtends(Environment arg) throws SemanticError {
            arg.initInheristance();
        }

        private void checkMain(Environment arg, int line_num) throws SemanticError.NoMain {
            if (arg.getFunction("main") == null) {
                throw new SemanticError.NoMain(line_num);
            }
        }
    }

    public class TopDefDeclareVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
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

    public class TopDefCheckVisitor implements latte.Absyn.TopDef.Visitor<Void, Environment> {
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
            if (!environment.wasReturn()) {
                throw new SemanticError.FunctionWithoutReturn(p.line_num, p.ident_);
            }
            environment.popContext();
            return null;
        }

        public Void visit(ClDef p, Environment environment) throws SemanticError {
            environment.addNewContext("class_" + p.ident_);
            environment.addVariable("this", new Class(p.ident_));
            environment.getClassDef(p.ident_).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }

        public Void visit(ClDefExt p, Environment environment) throws SemanticError {
            environment.addNewContext("class_" + p.ident_1);
            environment.addVariable("this", new Class(p.ident_1));
            environment.getClassDef(p.ident_1).clblock_.accept(new ClBlockInitFieldsInEnvironmentVisitor(), environment);
            p.clblock_.accept(new ClBlockTypeCheckVisitor(), environment);
            environment.popContext();
            return null;
        }
    }

    public class ClBlockTypeCheckVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) throws SemanticError {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberTypeCheckVisitor(), arg);
            }
            return null;
        }
    }

    public class ClBlockInitFieldsInEnvironmentVisitor implements latte.Absyn.ClBlock.Visitor<Void, Environment> {
        public Void visit(ClBlk p, Environment arg) throws SemanticError {
            for (latte.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberInitFieldsInEnvironmentVisitor(), arg);
            }
            return null;
        }
    }

    public class ClMemberInitFieldsInEnvironmentVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
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

    public class ClMemberTypeCheckVisitor implements latte.Absyn.ClMember.Visitor<Void, Environment> {
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
            if (!environment.wasReturn()) {
                throw new SemanticError.FunctionWithoutReturn(p.line_num, p.ident_);
            }
            environment.popContext();
            return null;
        }
    }

    public class BlockVisitor implements latte.Absyn.Block.Visitor<Void, Environment> {
        public Void visit(Blk p, Environment arg) throws SemanticError {
            for (latte.Absyn.Stmt x : p.liststmt_) {
                x.accept(new StmtVisitor(), arg);
            }
            return null;
        }
    }

    public class StmtVisitor implements latte.Absyn.Stmt.Visitor<Void, Environment> {
        public Void visit(latte.Absyn.Empty p, Environment arg) { /* Code for Empty goes here */
            return null;
        }

        public Void visit(latte.Absyn.BStmt p, Environment arg) throws SemanticError {
            arg.addNewContext("Block stmt");
            p.block_.accept(new BlockVisitor(), arg);
            Boolean wasReturn = arg.wasReturn();
            arg.popContext();
            if (wasReturn) arg.setWasReturn(true);
            return null;
        }

        public Void visit(latte.Absyn.Decl p, Environment arg) throws SemanticError {
            for (latte.Absyn.Item x : p.listitem_) {
                x.accept(new ItemDeclVisitor(p.type_), arg);
            }
            return null;
        }

        public Void visit(latte.Absyn.Ass p, Environment arg) throws SemanticError {
            if (arg.getVarType(p.ident_) == null) {
                throw new SemanticError.VariableNotDeclared(p.line_num, p.ident_);
            }
            Type exprType = p.expr_.accept(new ExprVisitor(), arg);
            Type varType = arg.getVarType(p.ident_);
            if (!exprType.equals(varType)) {
                throw new SemanticError.AssingingWrongType(p.line_num, varType, exprType);
            }
            return null;
        }


        public Void visit(latte.Absyn.AssArray p, Environment arg) throws SemanticError {
            Type iType = p.expr_1.accept(new ExprVisitor(), arg);
            if (!iType.equals(new Int())) {
                throw new SemanticError.ArrayIndexHasToBeInteger(p.line_num);
            }

            Array arrayType = (Array) arg.getVarType(p.ident_);
            Type exprType = p.expr_2.accept(new ExprVisitor(), arg);

            if (!arrayType.type_.equals(exprType)) {
                throw new SemanticError.AssingingWrongType(p.line_num, arrayType, exprType);
            }
            return null;
        }

        public Void visit(latte.Absyn.AssField p, Environment arg) throws SemanticError {
            Type assT = p.expr_2.accept(new ExprVisitor(), arg);
            try {
                Class classExprT = (Class) p.expr_1.accept(new ExprVisitor(), arg);
                ClField field = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getField(p.ident_);
                if (field == null) {
                    throw new SemanticError.FieldDoesNotExist(p.line_num);
                }
                if (!field.type_.equals(assT)) {
                    throw new SemanticError.AssingingWrongType(p.line_num, field.type_, assT);
                }
            } catch (ClassCastException e) {
                throw new SemanticError(p.line_num, "Field can only be applied to an object.");
            }
            return null;
        }

        public Void visit(latte.Absyn.Incr p, Environment arg) throws SemanticError {
            Type type = arg.getVarType(p.ident_);
            if (!type.equals(new Int())) {
                throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "++", type);
            }
            return null;
        }

        public Void visit(latte.Absyn.Decr p, Environment arg) throws SemanticError {
            Type type = arg.getVarType(p.ident_);
            if (!type.equals(new Int())) {
                throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "--", type);
            }
            return null;
        }

        public Void visit(latte.Absyn.Ret p, Environment arg) throws SemanticError {
            Type retType = p.expr_.accept(new ExprVisitor(), arg);
            if (!retType.equals(arg.getExpectedReturnType())) {
                throw new SemanticError.WrongReturnType(p.line_num);
            }
            arg.setWasReturn(true);
            return null;
        }

        public Void visit(latte.Absyn.VRet p, Environment arg) throws SemanticError {
            if (!(new latte.Absyn.Void()).equals(arg.getExpectedReturnType())) {
                throw new SemanticError.WrongReturnType(p.line_num);
            }
            arg.setWasReturn(true);
            return null;
        }

        public Void visit(latte.Absyn.Cond p, Environment arg) throws SemanticError {
            Type exprType = p.expr_.accept(new ExprVisitor(), arg);
            if (!exprType.equals(new Bool())) {
                throw new SemanticError.CondHasToBeBoolean(p.line_num);
            }
            Boolean wasReturnBefore = arg.wasReturn();
            p.stmt_.accept(new StmtVisitor(), arg);
            arg.setWasReturn(wasReturnBefore);
            return null;
        }

        public Void visit(latte.Absyn.CondElse p, Environment arg) throws SemanticError {
            Type exprType = p.expr_.accept(new ExprVisitor(), arg);
            if (!exprType.equals(new Bool())) {
                throw new SemanticError.CondHasToBeBoolean(p.line_num);
            }
            Boolean wasReturnBefore = arg.wasReturn();
            arg.setWasReturn(false);
            p.stmt_1.accept(new StmtVisitor(), arg);
            Boolean wasReturnStmt1 = arg.wasReturn();
            arg.setWasReturn(false);
            p.stmt_2.accept(new StmtVisitor(), arg);
            Boolean wasReturnStmt2 = arg.wasReturn();
            arg.setWasReturn(wasReturnBefore || (wasReturnStmt1 && wasReturnStmt2));
            return null;
        }

        public Void visit(latte.Absyn.While p, Environment arg) throws SemanticError {
            Type exprType = p.expr_.accept(new ExprVisitor(), arg);
            if (!exprType.equals(new Bool())) {
                throw new SemanticError.CondHasToBeBoolean(p.line_num);
            }
            p.stmt_.accept(new StmtVisitor(), arg);
            return null;
        }

        public Void visit(latte.Absyn.For p, Environment arg) throws SemanticError {
            arg.addNewContext("For");
            Ar iterator = (Ar) p.arg_;
            Type exprType = p.expr_.accept(new ExprVisitor(), arg);
            if (!(new Array(null)).equalsT(exprType)) {
                throw new SemanticError.ForEachCanBeAppliedToArraysOnly(p.line_num);
            }

            Array array = (Array) exprType;
            if (!iterator.type_.equals(array.type_)) {
                throw new SemanticError.AssingingWrongType(p.line_num, iterator.type_, array.type_);
            }
            arg.addVariable(iterator.ident_, iterator.type_);
            p.stmt_.accept(new StmtVisitor(), arg);
            arg.popContext();
            return null;
        }

        public Void visit(latte.Absyn.SExp p, Environment arg) throws SemanticError {
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }
    }

    public class ItemDeclVisitor implements latte.Absyn.Item.Visitor<String, Environment> {
        Type itemType;

        public ItemDeclVisitor(Type itemType) {
            this.itemType = itemType;
        }

        public String visit(latte.Absyn.NoInit p, Environment arg) throws SemanticError {
            if (arg.actContextContainsVar(p.ident_)) {
                throw new SemanticError.VariableAlreadyDeclared(p.line_num, p.ident_);
            } else {
                arg.addVariable(p.ident_, itemType);
            }
            return p.ident_;
        }

        public String visit(latte.Absyn.Init p, Environment arg) throws SemanticError {
            NoInit itemNoInit = new NoInit(p.ident_);
            itemNoInit.line_num = p.line_num;
            this.visit(itemNoInit, arg);

            Type exprType = p.expr_.accept(new ExprVisitor(), arg);

            if (!exprType.equals(itemType)) {
                throw new SemanticError.AssingingWrongType(p.line_num, itemType, exprType);
            }

            return p.ident_;
        }
    }

    public class ExprVisitor implements latte.Absyn.Expr.Visitor<Type, Environment> {
        public Type visit(ENewArray p, Environment arg) throws SemanticError {
            Type size = p.expr_.accept(new ExprVisitor(), arg);
            if (!size.equals(new Int())) {
                throw new SemanticError(p.line_num, "Size has to be an Integer.");
            }
            return new Array(p.type_);
        }

        public Type visit(EArrayElem p, Environment arg) throws SemanticError {
            Type index = p.expr_.accept(new ExprVisitor(), arg);
            if (!index.equals(new Int())) {
                throw new SemanticError(p.line_num, "Index has to be an Integer.");
            }
            Array arrayType = (Array) arg.getVarType(p.ident_);
            return arrayType.type_;
        }

        public Type visit(ENew p, Environment arg) throws SemanticError {
            if (arg.getClassDef(p.ident_) == null) {
                throw new SemanticError.ClassDoesNotExist(p.line_num);
            }
            return new Class(p.ident_);
        }

        public Type visit(EMethod p, Environment arg) throws SemanticError {
            ClMethod method;
            try {
                Class classExprT = (Class) p.expr_.accept(new ExprVisitor(), arg);
                method = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getMethod(p.ident_);
            } catch (Exception e) {
                throw new SemanticError(p.line_num, "Method can only be applied to an object.");
            }

            if (method == null) {
                throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
            }

            ListArg argExpectedTypes = method.listarg_;
            if (argExpectedTypes.size() != p.listexpr_.size()) {
                throw new SemanticError.WrongNumberOfArgument(p.line_num);
            }

            int i = 0;
            for (latte.Absyn.Expr x : p.listexpr_) {
                Type argT = x.accept(new ExprVisitor(), arg);
                Type expected = ((Ar) argExpectedTypes.get(i++)).type_;
                if (!argT.equals(expected)) {
                    throw new SemanticError.ArgTypesDoesNotMatch(p.line_num, i, expected, argT);
                }
            }
            return method.type_;
        }

        public Type visit(EField p, Environment arg) throws SemanticError {
            ClField field;

            Type exprT = p.expr_.accept(new ExprVisitor(), arg);
            if ((new Array(null)).equalsT(exprT) && arg.buildInArrayFields().containsKey(p.ident_)) {
                return arg.buildInArrayFields().get(p.ident_);
            } else {
                try {
                    Class classExprT = (Class) exprT;
                    field = ((ClBlk) arg.getClassDef(classExprT.ident_).clblock_).listclmember_.getField(p.ident_);
                    if (field == null) {
                        throw new SemanticError.FieldDoesNotExist(p.line_num);
                    }
                } catch (ClassCastException e) {
                    throw new SemanticError(p.line_num, "Field operator can only be applied to an object.");
                }
            }
            return field.type_;
        }

        public Type visit(EVar p, Environment arg) throws SemanticError {
            if (arg.getVarType(p.ident_) == null) {
                throw new SemanticError.VariableNotDeclared(p.line_num, p.ident_);
            }
            return arg.getVarType(p.ident_);
        }

        public Type visit(ELitInt p, Environment arg) {
            return new Int();
        }

        public Type visit(ELitTrue p, Environment arg) { /* Code for ELitTrue goes here */
            return new Bool();
        }

        public Type visit(ELitFalse p, Environment arg) { /* Code for ELitFalse goes here */
            return new Bool();
        }

        public Type visit(EApp p, Environment arg) throws SemanticError {
            FnDef fnDef = arg.getFunction(p.ident_);
            if (fnDef == null) {
                throw new SemanticError.FunctionNotDeclaredInThisScope(p.line_num, p.ident_);
            }

            ListArg argExpectedTypes = fnDef.listarg_;
            if (argExpectedTypes.size() != p.listexpr_.size()) {
                throw new SemanticError.WrongNumberOfArgument(p.line_num);
            }
            int i = 0;
            for (latte.Absyn.Expr x : p.listexpr_) {
                Type argT = x.accept(new ExprVisitor(), arg);
                Type expected = ((Ar) argExpectedTypes.get(i++)).type_;
                if (!argT.equals(expected)) {
                    throw new SemanticError.ArgTypesDoesNotMatch(p.line_num, i, expected, argT);
                }
            }
            return fnDef.type_;
        }

        public Type visit(EString p, Environment arg) {
            return new Str();
        }

        public Type visit(Neg p, Environment arg) throws SemanticError {
            Type t = p.expr_.accept(new ExprVisitor(), arg);
            if (!t.equals(new Int())) {
                throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "-", t);
            }
            return new Int();
        }

        public Type visit(Not p, Environment arg) throws SemanticError {
            Type t = p.expr_.accept(new ExprVisitor(), arg);
            if (!t.equals(new Bool())) {
                throw new SemanticError.OperatorCannotBeAppliedToType(p.line_num, "!", t);
            }
            return new Bool();
        }

        public Type visit(EMul p, Environment arg) throws SemanticError {
            Type t1 = p.expr_1.accept(new ExprVisitor(), arg);
            Type t2 = p.expr_2.accept(new ExprVisitor(), arg);
            if (!t1.equals(new Int())) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "*", t1, t2);
            }
            if (!t1.equals(t2)) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "*", t1, t2);
            }
            return t1;
        }

        public Type visit(EAdd p, Environment arg) throws SemanticError {
            Type t1 = p.expr_1.accept(new ExprVisitor(), arg);
            Type t2 = p.expr_2.accept(new ExprVisitor(), arg);
            if (!(t1.equals(new Int())) && !(t1.equals(new Str()))) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "+", t1, t2);
            }
            if (!t1.equals(t2)) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "+", t1, t2);
            }
            return t1;
        }

        public Type visit(ERel p, Environment arg) throws SemanticError {
            Type t1 = p.expr_1.accept(new ExprVisitor(), arg);
            Type t2 = p.expr_2.accept(new ExprVisitor(), arg);
            if (!t1.equals(new Int())) {
                throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
            }
            if (!t1.equals(t2)) {
                throw new SemanticError.RelOperatorCannotBeAppliedToTypes(p.line_num, t1, t2);
            }
            return new Bool();
        }

        public Type visit(EAnd p, Environment arg) throws SemanticError {
            Type t1 = p.expr_1.accept(new ExprVisitor(), arg);
            Type t2 = p.expr_2.accept(new ExprVisitor(), arg);
            if (!t1.equals(new Bool())) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "&&", t1, t2);
            }
            if (!t1.equals(t2)) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "&&", t1, t2);
            }
            return new Bool();
        }

        public Type visit(EOr p, Environment arg) throws SemanticError {
            Type t1 = p.expr_1.accept(new ExprVisitor(), arg);
            Type t2 = p.expr_2.accept(new ExprVisitor(), arg);
            if (!t1.equals(new Bool())) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "||", t1, t2);
            }
            if (!t1.equals(t2)) {
                throw new SemanticError.OperatorCannotBeAppliedToTypes(p.line_num, "||", t1, t2);
            }
            return new Bool();
        }
    }
}
