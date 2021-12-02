// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

public class AssArray  extends Stmt {
  public final String ident_;
  public final Expr expr_1, expr_2;
  public int line_num, col_num, offset;
  public AssArray(String p1, Expr p2, Expr p3) { ident_ = p1; expr_1 = p2; expr_2 = p3; }

  public <R,A> R accept(latte_lang.Absyn.Stmt.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.AssArray) {
      latte_lang.Absyn.AssArray x = (latte_lang.Absyn.AssArray)o;
      return this.ident_.equals(x.ident_) && this.expr_1.equals(x.expr_1) && this.expr_2.equals(x.expr_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.ident_.hashCode())+this.expr_1.hashCode())+this.expr_2.hashCode();
  }


}
