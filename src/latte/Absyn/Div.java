// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public class Div  extends MulOp {
  public int line_num, col_num, offset;
  public Div() { }

  public <R,A> R accept(latte.Absyn.MulOp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof Div;
  }

  public int hashCode() {
    return 37;
  }


}
