// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class ELitFalse  extends Expr {
  public int line_num, col_num, offset;
  public ELitFalse() { }

  public <R,A> R accept(latte.Absyn.Expr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof ELitFalse;
  }

  public int hashCode() {
    return 37;
  }


}
