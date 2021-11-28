// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang;
/** Composition Visitor
*/

public class ComposVisitor<A> implements
  latte_lang.Absyn.Program.Visitor<latte_lang.Absyn.Program,A>,
  latte_lang.Absyn.TopDef.Visitor<latte_lang.Absyn.TopDef,A>,
  latte_lang.Absyn.Arg.Visitor<latte_lang.Absyn.Arg,A>,
  latte_lang.Absyn.ClBlock.Visitor<latte_lang.Absyn.ClBlock,A>,
  latte_lang.Absyn.ClMember.Visitor<latte_lang.Absyn.ClMember,A>,
  latte_lang.Absyn.Block.Visitor<latte_lang.Absyn.Block,A>,
  latte_lang.Absyn.Stmt.Visitor<latte_lang.Absyn.Stmt,A>,
  latte_lang.Absyn.Item.Visitor<latte_lang.Absyn.Item,A>,
  latte_lang.Absyn.Type.Visitor<latte_lang.Absyn.Type,A>,
  latte_lang.Absyn.Expr.Visitor<latte_lang.Absyn.Expr,A>,
  latte_lang.Absyn.AddOp.Visitor<latte_lang.Absyn.AddOp,A>,
  latte_lang.Absyn.MulOp.Visitor<latte_lang.Absyn.MulOp,A>,
  latte_lang.Absyn.RelOp.Visitor<latte_lang.Absyn.RelOp,A>
{
    /* Program */
    public latte_lang.Absyn.Program visit(latte_lang.Absyn.Prog p, A arg)
    {
      latte_lang.Absyn.ListTopDef listtopdef_ = new latte_lang.Absyn.ListTopDef();
      for (latte_lang.Absyn.TopDef x : p.listtopdef_)
      {
        listtopdef_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.Prog(listtopdef_);
    }

    /* TopDef */
    public latte_lang.Absyn.TopDef visit(latte_lang.Absyn.FnDef p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      String ident_ = p.ident_;
      latte_lang.Absyn.ListArg listarg_ = new latte_lang.Absyn.ListArg();
      for (latte_lang.Absyn.Arg x : p.listarg_)
      {
        listarg_.add(x.accept(this,arg));
      }
      latte_lang.Absyn.Block block_ = p.block_.accept(this, arg);
      return new latte_lang.Absyn.FnDef(type_, ident_, listarg_, block_);
    }
    public latte_lang.Absyn.TopDef visit(latte_lang.Absyn.ClDef p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.ClBlock clblock_ = p.clblock_.accept(this, arg);
      return new latte_lang.Absyn.ClDef(ident_, clblock_);
    }
    public latte_lang.Absyn.TopDef visit(latte_lang.Absyn.ClDefExt p, A arg)
    {
      String ident_1 = p.ident_1;
      String ident_2 = p.ident_2;
      latte_lang.Absyn.ClBlock clblock_ = p.clblock_.accept(this, arg);
      return new latte_lang.Absyn.ClDefExt(ident_1, ident_2, clblock_);
    }

    /* Arg */
    public latte_lang.Absyn.Arg visit(latte_lang.Absyn.Ar p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      String ident_ = p.ident_;
      return new latte_lang.Absyn.Ar(type_, ident_);
    }

    /* ClBlock */
    public latte_lang.Absyn.ClBlock visit(latte_lang.Absyn.ClBlk p, A arg)
    {
      latte_lang.Absyn.ListClMember listclmember_ = new latte_lang.Absyn.ListClMember();
      for (latte_lang.Absyn.ClMember x : p.listclmember_)
      {
        listclmember_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.ClBlk(listclmember_);
    }

    /* ClMember */
    public latte_lang.Absyn.ClMember visit(latte_lang.Absyn.ClField p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      String ident_ = p.ident_;
      return new latte_lang.Absyn.ClField(type_, ident_);
    }
    public latte_lang.Absyn.ClMember visit(latte_lang.Absyn.ClMethod p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      String ident_ = p.ident_;
      latte_lang.Absyn.ListArg listarg_ = new latte_lang.Absyn.ListArg();
      for (latte_lang.Absyn.Arg x : p.listarg_)
      {
        listarg_.add(x.accept(this,arg));
      }
      latte_lang.Absyn.Block block_ = p.block_.accept(this, arg);
      return new latte_lang.Absyn.ClMethod(type_, ident_, listarg_, block_);
    }

    /* Block */
    public latte_lang.Absyn.Block visit(latte_lang.Absyn.Blk p, A arg)
    {
      latte_lang.Absyn.ListStmt liststmt_ = new latte_lang.Absyn.ListStmt();
      for (latte_lang.Absyn.Stmt x : p.liststmt_)
      {
        liststmt_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.Blk(liststmt_);
    }

    /* Stmt */
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Empty p, A arg)
    {
      return new latte_lang.Absyn.Empty();
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.BStmt p, A arg)
    {
      latte_lang.Absyn.Block block_ = p.block_.accept(this, arg);
      return new latte_lang.Absyn.BStmt(block_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Decl p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      latte_lang.Absyn.ListItem listitem_ = new latte_lang.Absyn.ListItem();
      for (latte_lang.Absyn.Item x : p.listitem_)
      {
        listitem_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.Decl(type_, listitem_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Ass p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.Ass(ident_, expr_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.AssArray p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.AssArray(ident_, expr_1, expr_2);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.AssField p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      String ident_ = p.ident_;
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.AssField(expr_1, ident_, expr_2);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Incr p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.Incr(ident_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Decr p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.Decr(ident_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Ret p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.Ret(expr_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.VRet p, A arg)
    {
      return new latte_lang.Absyn.VRet();
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.Cond p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      latte_lang.Absyn.Stmt stmt_ = p.stmt_.accept(this, arg);
      return new latte_lang.Absyn.Cond(expr_, stmt_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.CondElse p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      latte_lang.Absyn.Stmt stmt_1 = p.stmt_1.accept(this, arg);
      latte_lang.Absyn.Stmt stmt_2 = p.stmt_2.accept(this, arg);
      return new latte_lang.Absyn.CondElse(expr_, stmt_1, stmt_2);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.While p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      latte_lang.Absyn.Stmt stmt_ = p.stmt_.accept(this, arg);
      return new latte_lang.Absyn.While(expr_, stmt_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.For p, A arg)
    {
      latte_lang.Absyn.Arg arg_ = p.arg_.accept(this, arg);
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      latte_lang.Absyn.Stmt stmt_ = p.stmt_.accept(this, arg);
      return new latte_lang.Absyn.For(arg_, expr_, stmt_);
    }
    public latte_lang.Absyn.Stmt visit(latte_lang.Absyn.SExp p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.SExp(expr_);
    }

    /* Item */
    public latte_lang.Absyn.Item visit(latte_lang.Absyn.NoInit p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.NoInit(ident_);
    }
    public latte_lang.Absyn.Item visit(latte_lang.Absyn.Init p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.Init(ident_, expr_);
    }

    /* Type */
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Int p, A arg)
    {
      return new latte_lang.Absyn.Int();
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Str p, A arg)
    {
      return new latte_lang.Absyn.Str();
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Bool p, A arg)
    {
      return new latte_lang.Absyn.Bool();
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Void p, A arg)
    {
      return new latte_lang.Absyn.Void();
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Class p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.Class(ident_);
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Array p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      return new latte_lang.Absyn.Array(type_);
    }
    public latte_lang.Absyn.Type visit(latte_lang.Absyn.Fun p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      latte_lang.Absyn.ListType listtype_ = new latte_lang.Absyn.ListType();
      for (latte_lang.Absyn.Type x : p.listtype_)
      {
        listtype_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.Fun(type_, listtype_);
    }

    /* Expr */
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ENewArray p, A arg)
    {
      latte_lang.Absyn.Type type_ = p.type_.accept(this, arg);
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.ENewArray(type_, expr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EArrayElem p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.EArrayElem(ident_, expr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ENew p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.ENew(ident_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EMethod p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      String ident_ = p.ident_;
      latte_lang.Absyn.ListExpr listexpr_ = new latte_lang.Absyn.ListExpr();
      for (latte_lang.Absyn.Expr x : p.listexpr_)
      {
        listexpr_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.EMethod(expr_, ident_, listexpr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EField p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      String ident_ = p.ident_;
      return new latte_lang.Absyn.EField(expr_, ident_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EVar p, A arg)
    {
      String ident_ = p.ident_;
      return new latte_lang.Absyn.EVar(ident_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ELitInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new latte_lang.Absyn.ELitInt(integer_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ELitTrue p, A arg)
    {
      return new latte_lang.Absyn.ELitTrue();
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ELitFalse p, A arg)
    {
      return new latte_lang.Absyn.ELitFalse();
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EApp p, A arg)
    {
      String ident_ = p.ident_;
      latte_lang.Absyn.ListExpr listexpr_ = new latte_lang.Absyn.ListExpr();
      for (latte_lang.Absyn.Expr x : p.listexpr_)
      {
        listexpr_.add(x.accept(this,arg));
      }
      return new latte_lang.Absyn.EApp(ident_, listexpr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EString p, A arg)
    {
      String string_ = p.string_;
      return new latte_lang.Absyn.EString(string_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.Neg p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.Neg(expr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.Not p, A arg)
    {
      latte_lang.Absyn.Expr expr_ = p.expr_.accept(this, arg);
      return new latte_lang.Absyn.Not(expr_);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EMul p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.MulOp mulop_ = p.mulop_.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.EMul(expr_1, mulop_, expr_2);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EAdd p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.AddOp addop_ = p.addop_.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.EAdd(expr_1, addop_, expr_2);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.ERel p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.RelOp relop_ = p.relop_.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.ERel(expr_1, relop_, expr_2);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EAnd p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.EAnd(expr_1, expr_2);
    }
    public latte_lang.Absyn.Expr visit(latte_lang.Absyn.EOr p, A arg)
    {
      latte_lang.Absyn.Expr expr_1 = p.expr_1.accept(this, arg);
      latte_lang.Absyn.Expr expr_2 = p.expr_2.accept(this, arg);
      return new latte_lang.Absyn.EOr(expr_1, expr_2);
    }

    /* AddOp */
    public latte_lang.Absyn.AddOp visit(latte_lang.Absyn.Plus p, A arg)
    {
      return new latte_lang.Absyn.Plus();
    }
    public latte_lang.Absyn.AddOp visit(latte_lang.Absyn.Minus p, A arg)
    {
      return new latte_lang.Absyn.Minus();
    }

    /* MulOp */
    public latte_lang.Absyn.MulOp visit(latte_lang.Absyn.Times p, A arg)
    {
      return new latte_lang.Absyn.Times();
    }
    public latte_lang.Absyn.MulOp visit(latte_lang.Absyn.Div p, A arg)
    {
      return new latte_lang.Absyn.Div();
    }
    public latte_lang.Absyn.MulOp visit(latte_lang.Absyn.Mod p, A arg)
    {
      return new latte_lang.Absyn.Mod();
    }

    /* RelOp */
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.LTH p, A arg)
    {
      return new latte_lang.Absyn.LTH();
    }
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.LE p, A arg)
    {
      return new latte_lang.Absyn.LE();
    }
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.GTH p, A arg)
    {
      return new latte_lang.Absyn.GTH();
    }
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.GE p, A arg)
    {
      return new latte_lang.Absyn.GE();
    }
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.EQU p, A arg)
    {
      return new latte_lang.Absyn.EQU();
    }
    public latte_lang.Absyn.RelOp visit(latte_lang.Absyn.NE p, A arg)
    {
      return new latte_lang.Absyn.NE();
    }
}
