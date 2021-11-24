// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

public abstract class Expr implements java.io.Serializable {
  public abstract <R,A> R accept(Expr.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte_lang.Absyn.EVar p, A arg);
    public R visit(latte_lang.Absyn.ELitInt p, A arg);
    public R visit(latte_lang.Absyn.ELitTrue p, A arg);
    public R visit(latte_lang.Absyn.ELitFalse p, A arg);
    public R visit(latte_lang.Absyn.EApp p, A arg);
    public R visit(latte_lang.Absyn.EString p, A arg);
    public R visit(latte_lang.Absyn.Neg p, A arg);
    public R visit(latte_lang.Absyn.Not p, A arg);
    public R visit(latte_lang.Absyn.EMul p, A arg);
    public R visit(latte_lang.Absyn.EAdd p, A arg);
    public R visit(latte_lang.Absyn.ERel p, A arg);
    public R visit(latte_lang.Absyn.EAnd p, A arg);
    public R visit(latte_lang.Absyn.EOr p, A arg);

  }

}
