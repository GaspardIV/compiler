// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class Not  extends Expr {
  public final Expr expr_;
  public int line_num, col_num, offset;
  public Not(Expr p1) { expr_ = p1; }

  public <R,A> R accept(latte.Absyn.Expr.Visitor<R,A> v, A arg) throws Exception { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Not) {
      latte.Absyn.Not x = (latte.Absyn.Not)o;
      return this.expr_.equals(x.expr_);
    }
    return false;
  }

  public int hashCode() {
    return this.expr_.hashCode();
  }


}
