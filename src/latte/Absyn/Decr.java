// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class Decr  extends Stmt {
  public final Expr expr_;
  public int line_num, col_num, offset;
  public Decr(Expr p1) { expr_ = p1; }

  public <R,A> R accept(latte.Absyn.Stmt.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Decr) {
      latte.Absyn.Decr x = (latte.Absyn.Decr)o;
      return this.expr_.equals(x.expr_);
    }
    return false;
  }

  public int hashCode() {
    return this.expr_.hashCode();
  }


}
