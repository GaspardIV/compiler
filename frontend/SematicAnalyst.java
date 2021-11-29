package frontend;

import latte_lang.Absyn.Program;

public class SematicAnalyst {
    public void checkTypes(Program ast) {

    }

    public class ProgramVisitor<R, A> implements latte_lang.Absyn.Program.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Prog p, A arg) { /* Code for Prog goes here */
            for (latte_lang.Absyn.TopDef x : p.listtopdef_) {
                x.accept(new TopDefVisitor<R, A>(), arg);
            }
            return null;
        }
    }

    public class TopDefVisitor<R, A> implements latte_lang.Absyn.TopDef.Visitor<R, A> {
        public R visit(latte_lang.Absyn.FnDef p, A arg) { /* Code for FnDef goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Arg x : p.listarg_) {
                x.accept(new ArgVisitor<R, A>(), arg);
            }
            p.block_.accept(new BlockVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.ClDef p, A arg) { /* Code for ClDef goes here */
            //p.ident_;
            p.clblock_.accept(new ClBlockVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.ClDefExt p, A arg) { /* Code for ClDefExt goes here */
            //p.ident_1;
            //p.ident_2;
            p.clblock_.accept(new ClBlockVisitor<R, A>(), arg);
            return null;
        }
    }

    public class ArgVisitor<R, A> implements latte_lang.Absyn.Arg.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Ar p, A arg) { /* Code for Ar goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            //p.ident_;
            return null;
        }
    }

    public class ClBlockVisitor<R, A> implements latte_lang.Absyn.ClBlock.Visitor<R, A> {
        public R visit(latte_lang.Absyn.ClBlk p, A arg) { /* Code for ClBlk goes here */
            for (latte_lang.Absyn.ClMember x : p.listclmember_) {
                x.accept(new ClMemberVisitor<R, A>(), arg);
            }
            return null;
        }
    }

    public class ClMemberVisitor<R, A> implements latte_lang.Absyn.ClMember.Visitor<R, A> {
        public R visit(latte_lang.Absyn.ClField p, A arg) { /* Code for ClField goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.ClMethod p, A arg) { /* Code for ClMethod goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Arg x : p.listarg_) {
                x.accept(new ArgVisitor<R, A>(), arg);
            }
            p.block_.accept(new BlockVisitor<R, A>(), arg);
            return null;
        }
    }

    public class BlockVisitor<R, A> implements latte_lang.Absyn.Block.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Blk p, A arg) { /* Code for Blk goes here */
            for (latte_lang.Absyn.Stmt x : p.liststmt_) {
                x.accept(new StmtVisitor<R, A>(), arg);
            }
            return null;
        }
    }

    public class StmtVisitor<R, A> implements latte_lang.Absyn.Stmt.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Empty p, A arg) { /* Code for Empty goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.BStmt p, A arg) { /* Code for BStmt goes here */
            p.block_.accept(new BlockVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.Decl p, A arg) { /* Code for Decl goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            for (latte_lang.Absyn.Item x : p.listitem_) {
                x.accept(new ItemVisitor<R, A>(), arg);
            }
            return null;
        }

        public R visit(latte_lang.Absyn.Ass p, A arg) { /* Code for Ass goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.AssArray p, A arg) { /* Code for AssArray goes here */
            //p.ident_;
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.AssField p, A arg) { /* Code for AssField goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            //p.ident_;
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.Incr p, A arg) { /* Code for Incr goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.Decr p, A arg) { /* Code for Decr goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.Ret p, A arg) { /* Code for Ret goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.VRet p, A arg) { /* Code for VRet goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Cond p, A arg) { /* Code for Cond goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            p.stmt_.accept(new StmtVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.CondElse p, A arg) { /* Code for CondElse goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            p.stmt_1.accept(new StmtVisitor<R, A>(), arg);
            p.stmt_2.accept(new StmtVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.While p, A arg) { /* Code for While goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            p.stmt_.accept(new StmtVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.For p, A arg) { /* Code for For goes here */
            p.arg_.accept(new ArgVisitor<R, A>(), arg);
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            p.stmt_.accept(new StmtVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.SExp p, A arg) { /* Code for SExp goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }
    }

    public class ItemVisitor<R, A> implements latte_lang.Absyn.Item.Visitor<R, A> {
        public R visit(latte_lang.Absyn.NoInit p, A arg) { /* Code for NoInit goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.Init p, A arg) { /* Code for Init goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }
    }

    public class TypeVisitor<R, A> implements latte_lang.Absyn.Type.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Int p, A arg) { /* Code for Int goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Str p, A arg) { /* Code for Str goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Bool p, A arg) { /* Code for Bool goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Void p, A arg) { /* Code for Void goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Class p, A arg) { /* Code for Class goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.Array p, A arg) { /* Code for Array goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.Fun p, A arg) { /* Code for Fun goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            for (latte_lang.Absyn.Type x : p.listtype_) {
                x.accept(new TypeVisitor<R, A>(), arg);
            }
            return null;
        }
    }

    public class ExprVisitor<R, A> implements latte_lang.Absyn.Expr.Visitor<R, A> {
        public R visit(latte_lang.Absyn.ENewArray p, A arg) { /* Code for ENewArray goes here */
            p.type_.accept(new TypeVisitor<R, A>(), arg);
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.EArrayElem p, A arg) { /* Code for EArrayElem goes here */
            //p.ident_;
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.ENew p, A arg) { /* Code for ENew goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.EMethod p, A arg) { /* Code for EMethod goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            //p.ident_;
            for (latte_lang.Absyn.Expr x : p.listexpr_) {
                x.accept(new ExprVisitor<R, A>(), arg);
            }
            return null;
        }

        public R visit(latte_lang.Absyn.EField p, A arg) { /* Code for EField goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.EVar p, A arg) { /* Code for EVar goes here */
            //p.ident_;
            return null;
        }

        public R visit(latte_lang.Absyn.ELitInt p, A arg) { /* Code for ELitInt goes here */
            //p.integer_;
            return null;
        }

        public R visit(latte_lang.Absyn.ELitTrue p, A arg) { /* Code for ELitTrue goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.ELitFalse p, A arg) { /* Code for ELitFalse goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.EApp p, A arg) { /* Code for EApp goes here */
            //p.ident_;
            for (latte_lang.Absyn.Expr x : p.listexpr_) {
                x.accept(new ExprVisitor<R, A>(), arg);
            }
            return null;
        }

        public R visit(latte_lang.Absyn.EString p, A arg) { /* Code for EString goes here */
            //p.string_;
            return null;
        }

        public R visit(latte_lang.Absyn.Neg p, A arg) { /* Code for Neg goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.Not p, A arg) { /* Code for Not goes here */
            p.expr_.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.EMul p, A arg) { /* Code for EMul goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.mulop_.accept(new MulOpVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.EAdd p, A arg) { /* Code for EAdd goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.addop_.accept(new AddOpVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.ERel p, A arg) { /* Code for ERel goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.relop_.accept(new RelOpVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.EAnd p, A arg) { /* Code for EAnd goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }

        public R visit(latte_lang.Absyn.EOr p, A arg) { /* Code for EOr goes here */
            p.expr_1.accept(new ExprVisitor<R, A>(), arg);
            p.expr_2.accept(new ExprVisitor<R, A>(), arg);
            return null;
        }
    }

    public class AddOpVisitor<R, A> implements latte_lang.Absyn.AddOp.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Plus p, A arg) { /* Code for Plus goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Minus p, A arg) { /* Code for Minus goes here */
            return null;
        }
    }

    public class MulOpVisitor<R, A> implements latte_lang.Absyn.MulOp.Visitor<R, A> {
        public R visit(latte_lang.Absyn.Times p, A arg) { /* Code for Times goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Div p, A arg) { /* Code for Div goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.Mod p, A arg) { /* Code for Mod goes here */
            return null;
        }
    }

    public class RelOpVisitor<R, A> implements latte_lang.Absyn.RelOp.Visitor<R, A> {
        public R visit(latte_lang.Absyn.LTH p, A arg) { /* Code for LTH goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.LE p, A arg) { /* Code for LE goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.GTH p, A arg) { /* Code for GTH goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.GE p, A arg) { /* Code for GE goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.EQU p, A arg) { /* Code for EQU goes here */
            return null;
        }

        public R visit(latte_lang.Absyn.NE p, A arg) { /* Code for NE goes here */
            return null;
        }
    }
}
