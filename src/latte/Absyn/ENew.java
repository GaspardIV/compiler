// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class ENew  extends Expr {
  public final String ident_;
  public int line_num, col_num, offset;
  public ENew(String p1) { ident_ = p1; }

  public <R,A> R accept(latte.Absyn.Expr.Visitor<R,A> v, A arg) throws Exception { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.ENew) {
      latte.Absyn.ENew x = (latte.Absyn.ENew)o;
      return this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return this.ident_.hashCode();
  }


}
