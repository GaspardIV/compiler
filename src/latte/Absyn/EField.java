// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class EField  extends Expr {
  public final Expr expr_;
  public final String ident_;
  public int line_num, col_num, offset;
  public EField(Expr p1, String p2) { expr_ = p1; ident_ = p2; }

  public <R,A> R accept(latte.Absyn.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.EField) {
      latte.Absyn.EField x = (latte.Absyn.EField)o;
      return this.expr_.equals(x.expr_) && this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.expr_.hashCode())+this.ident_.hashCode();
  }


}
