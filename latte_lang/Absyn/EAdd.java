// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

import frontend.SemanticError;

public class EAdd  extends Expr {
  public final Expr expr_1, expr_2;
  public final AddOp addop_;
  public int line_num, col_num, offset;
  public EAdd(Expr p1, AddOp p2, Expr p3) { expr_1 = p1; addop_ = p2; expr_2 = p3; }

  public <R,A> R accept(latte_lang.Absyn.Expr.Visitor<R,A> v, A arg) throws SemanticError { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.EAdd) {
      latte_lang.Absyn.EAdd x = (latte_lang.Absyn.EAdd)o;
      return this.expr_1.equals(x.expr_1) && this.addop_.equals(x.addop_) && this.expr_2.equals(x.expr_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.expr_1.hashCode())+this.addop_.hashCode())+this.expr_2.hashCode();
  }


}
