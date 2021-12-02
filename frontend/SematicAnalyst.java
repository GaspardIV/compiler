package frontend;

import latte_lang.Absyn.*;
import java.lang.Void;

public class SematicAnalyst {
    public void checkTypes(Program ast) throws SemanticError {
        Enviroment enviroment = new Enviroment();
        ProgramCheckVisitor programVisitor = new ProgramCheckVisitor();
        ast.accept(programVisitor, enviroment);
    }

    public class ProgramCheckVisitor implements latte_lang.Absyn.Program.Visitor<Void, Enviroment> {
        public Void visit(Prog p, Enviroment arg) throws SemanticError {
            createGlobalContext(p, arg);
            checkTopDefs(p, arg);
            return null;
        }

        private void checkTopDefs(Prog p, Enviroment arg) throws SemanticError {
            for (latte_lang.Absyn.TopDef x : p.listtopdef_) {
                x.accept(new TopDefCheckVisitor(), arg);
            }
        }

        private void createGlobalContext(Prog p, Enviroment arg) throws SemanticError {
            for (latte_lang.Absyn.TopDef x : p.listtopdef_) {
                x.accept(new TopDefDeclareVisitor(), arg);
            }
            checkMain(arg, p.line_num);
            fillClassesWithExtends(arg);
        }

        private void fillClassesWithExtends(Enviroment arg) throws SemanticError {
            arg.initInheristance();
        }

        private void checkMain(Enviroment arg, int line_num) throws SemanticError.NoMain {
            if (arg.getFunction("main") == null) {
                throw new SemanticError.NoMain(line_num);
            }
        }
    }

    public class TopDefDeclareVisitor implements latte_lang.Absyn.TopDef.Visitor<Void, Enviroment> {
        public Void visit(FnDef p, Enviroment arg) throws SemanticError { /* Code for FnDef goes here */
            checkIfFunctionAlreadyDefined(p, arg);
            addFunctionDef(p, arg);
            return null;
        }

        public Void visit(ClDef p, Enviroment arg) throws SemanticError { /* Code for ClDef goes here */
            ClDefExt i = new ClDefExt(p.ident_, null, p.clblock_);
            i.line_num = p.line_num;
            checkIfCLassAlreadyDefined(i, arg);
            addClassDef(i, arg);
            return null;
        }

        public Void visit(ClDefExt p, Enviroment arg) throws SemanticError.ClassAlreadyDeclared { /* Code for ClDefExt goes here */
            checkIfCLassAlreadyDefined(p, arg);
            addClassDef(p, arg);
            return null;
        }

        private void addFunctionDef(FnDef p, Enviroment arg) {
//            ListType listType = new ListType();
//            for (latte_lang.Absyn.Arg x : p.listarg_) {
//                listType.add(x.accept(new ArgGetTypeVisitor(), arg));
//            }
            arg.addFunction(p.ident_, p);
        }

        private void checkIfFunctionAlreadyDefined(FnDef p, Enviroment arg) throws SemanticError {
            if (arg.getFunction(p.ident_) != null) {
                throw new SemanticError.FunctionAlreadyDeclared(p.line_num);
            }
        }

        private void addClassDef(ClDefExt p, Enviroment arg) {
            arg.addClassDef(p.ident_1, p);
        }

        private void checkIfCLassAlreadyDefined(ClDefExt p, Enviroment arg) throws SemanticError.ClassAlreadyDeclared {
            if (arg.getClassDef(p.ident_1) != null) {
                throw new SemanticError.ClassAlreadyDeclared(p.line_num);
            }
        }
    }

    public class TopDefCheckVisitor implements latte_lang.Absyn.TopDef.Visitor<Void, Enviroment> {
        public Void visit(FnDef p, Enviroment arg) { /* Code for FnDef goes here */
            p.type_.accept(new TypeVisitor(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Arg x : p.listarg_) {
                x.accept(new ArgVisitor(), arg);
            }
            p.block_.accept(new BlockVisitor(), arg);
            return null;
        }

        public Void visit(ClDef p, Enviroment arg) { /* Code for ClDef goes here */
            //p.ident_;
            p.clblock_.accept(new ClBlockVisitor(), arg);
            return null;
        }

        public Void visit(ClDefExt p, Enviroment arg) { /* Code for ClDefExt goes here */
            //p.ident_1;
            //p.ident_2;
            p.clblock_.accept(new ClBlockVisitor(), arg);
            return null;
        }
    }

    public class ArgGetTypeVisitor implements latte_lang.Absyn.Arg.Visitor<Type, Enviroment> {
        public Type visit(latte_lang.Absyn.Ar p, Enviroment arg) { /* Code for Ar goes here */
            p.type_.accept(new TypeVisitor(), arg);
            //p.ident_;
            return p.type_;
        }
    }

    public class ArgVisitor implements latte_lang.Absyn.Arg.Visitor<Void, Enviroment> {
        public Void visit(Ar p, Enviroment arg) { /* Code for Ar goes here */
            p.type_.accept(new TypeVisitor(), arg);
            //p.ident_;
            return null;
        }
    }

    public class ClBlockVisitor implements latte_lang.Absyn.ClBlock.Visitor<Void, Enviroment> {
        public Void visit(ClBlk p, Enviroment arg) { /* Code for ClBlk goes here */
            for (latte_lang.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberVisitor(), arg);
            }
            return null;
        }
    }

    public class ClMemberVisitor implements latte_lang.Absyn.ClMember.Visitor<Void, Enviroment> {
        public Void visit(ClField p, Enviroment arg) { /* Code for ClField goes here */
            p.type_.accept(new TypeVisitor(), arg);
            //p.ident_;
            return null;
        }

        public Void visit(ClMethod p, Enviroment arg) { /* Code for ClMethod goes here */
            p.type_.accept(new TypeVisitor(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Arg x : p.listarg_) {
                x.accept(new ArgVisitor(), arg);
            }
            p.block_.accept(new BlockVisitor(), arg);
            return null;
        }
    }

    public class BlockVisitor implements latte_lang.Absyn.Block.Visitor<Void, Enviroment> {
        public Void visit(Blk p, Enviroment arg) { /* Code for Blk goes here */
            for (latte_lang.Absyn.Stmt x : p.liststmt_) {
                x.accept(new StmtVisitor(), arg);
            }
            return null;
        }
    }

    public class StmtVisitor implements latte_lang.Absyn.Stmt.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.Empty p, Enviroment arg) { /* Code for Empty goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.BStmt p, Enviroment arg) { /* Code for BStmt goes here */
            p.block_.accept(new BlockVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.Decl p, Enviroment arg) { /* Code for Decl goes here */
            p.type_.accept(new TypeVisitor(), arg);
            for (latte_lang.Absyn.Item x : p.listitem_) {
                x.accept(new ItemVisitor(), arg);
            }
            return null;
        }

        public Void visit(latte_lang.Absyn.Ass p, Enviroment arg) { /* Code for Ass goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.AssArray p, Enviroment arg) { /* Code for AssArray goes here */
            //p.ident_;
            p.expr_1.accept(new ExprVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.AssField p, Enviroment arg) { /* Code for AssField goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            //p.ident_;
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.Incr p, Enviroment arg) { /* Code for Incr goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.Decr p, Enviroment arg) { /* Code for Decr goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.Ret p, Enviroment arg) { /* Code for Ret goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.VRet p, Enviroment arg) { /* Code for VRet goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Cond p, Enviroment arg) { /* Code for Cond goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            p.stmt_.accept(new StmtVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.CondElse p, Enviroment arg) { /* Code for CondElse goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            p.stmt_1.accept(new StmtVisitor(), arg);
            p.stmt_2.accept(new StmtVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.While p, Enviroment arg) { /* Code for While goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            p.stmt_.accept(new StmtVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.For p, Enviroment arg) { /* Code for For goes here */
            p.arg_.accept(new ArgVisitor(), arg);
            p.expr_.accept(new ExprVisitor(), arg);
            p.stmt_.accept(new StmtVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.SExp p, Enviroment arg) { /* Code for SExp goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }
    }

    public class ItemVisitor implements latte_lang.Absyn.Item.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.NoInit p, Enviroment arg) { /* Code for NoInit goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.Init p, Enviroment arg) { /* Code for Init goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }
    }

    public class TypeVisitor implements latte_lang.Absyn.Type.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.Int p, Enviroment arg) { /* Code for Int goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Str p, Enviroment arg) { /* Code for Str goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Bool p, Enviroment arg) { /* Code for Bool goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Void p, Enviroment arg) { /* Code for Void goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Class p, Enviroment arg) { /* Code for Class goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.Array p, Enviroment arg) { /* Code for Array goes here */
            p.type_.accept(new TypeVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.Fun p, Enviroment arg) { /* Code for Fun goes here */
            p.type_.accept(new TypeVisitor(), arg);
            for (latte_lang.Absyn.Type x : p.listtype_) {
                x.accept(new TypeVisitor(), arg);
            }
            return null;
        }
    }

    public class ExprVisitor implements latte_lang.Absyn.Expr.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.ENewArray p, Enviroment arg) { /* Code for ENewArray goes here */
            p.type_.accept(new TypeVisitor(), arg);
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.EArrayElem p, Enviroment arg) { /* Code for EArrayElem goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.ENew p, Enviroment arg) { /* Code for ENew goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.EMethod p, Enviroment arg) { /* Code for EMethod goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Expr x : p.listexpr_) {
                x.accept(new ExprVisitor(), arg);
            }
            return null;
        }

        public Void visit(latte_lang.Absyn.EField p, Enviroment arg) { /* Code for EField goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.EVar p, Enviroment arg) { /* Code for EVar goes here */
            //p.ident_;
            return null;
        }

        public Void visit(latte_lang.Absyn.ELitInt p, Enviroment arg) { /* Code for ELitInt goes here */
            //p.integer_;
            return null;
        }

        public Void visit(latte_lang.Absyn.ELitTrue p, Enviroment arg) { /* Code for ELitTrue goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.ELitFalse p, Enviroment arg) { /* Code for ELitFalse goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.EApp p, Enviroment arg) { /* Code for EApp goes here */
            //p.ident_;
            for (latte_lang.Absyn.Expr x : p.listexpr_) {
                x.accept(new ExprVisitor(), arg);
            }
            return null;
        }

        public Void visit(latte_lang.Absyn.EString p, Enviroment arg) { /* Code for EString goes here */
            //p.string_;
            return null;
        }

        public Void visit(latte_lang.Absyn.Neg p, Enviroment arg) { /* Code for Neg goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.Not p, Enviroment arg) { /* Code for Not goes here */
            p.expr_.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.EMul p, Enviroment arg) { /* Code for EMul goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            p.mulop_.accept(new MulOpVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.EAdd p, Enviroment arg) { /* Code for EAdd goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            p.addop_.accept(new AddOpVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.ERel p, Enviroment arg) { /* Code for ERel goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            p.relop_.accept(new RelOpVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.EAnd p, Enviroment arg) { /* Code for EAnd goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }

        public Void visit(latte_lang.Absyn.EOr p, Enviroment arg) { /* Code for EOr goes here */
            p.expr_1.accept(new ExprVisitor(), arg);
            p.expr_2.accept(new ExprVisitor(), arg);
            return null;
        }
    }

    public class AddOpVisitor implements latte_lang.Absyn.AddOp.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.Plus p, Enviroment arg) { /* Code for Plus goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Minus p, Enviroment arg) { /* Code for Minus goes here */
            return null;
        }
    }

    public class MulOpVisitor implements latte_lang.Absyn.MulOp.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.Times p, Enviroment arg) { /* Code for Times goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Div p, Enviroment arg) { /* Code for Div goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.Mod p, Enviroment arg) { /* Code for Mod goes here */
            return null;
        }
    }

    public class RelOpVisitor implements latte_lang.Absyn.RelOp.Visitor<Void, Enviroment> {
        public Void visit(latte_lang.Absyn.LTH p, Enviroment arg) { /* Code for LTH goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.LE p, Enviroment arg) { /* Code for LE goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.GTH p, Enviroment arg) { /* Code for GTH goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.GE p, Enviroment arg) { /* Code for GE goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.EQU p, Enviroment arg) { /* Code for EQU goes here */
            return null;
        }

        public Void visit(latte_lang.Absyn.NE p, Enviroment arg) { /* Code for NE goes here */
            return null;
        }
    }
}
