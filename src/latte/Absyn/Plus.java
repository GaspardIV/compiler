// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public class Plus extends AddOp {
  public int line_num, col_num, offset;
  public Plus() { }

  public <R,A> R accept(latte.Absyn.AddOp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof Plus;
  }

  @Override
  public String toString() {
    return "+";
  }

  public int hashCode() {
    return 37;
  }


}
