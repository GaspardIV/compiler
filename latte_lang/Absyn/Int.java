// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

public class Int  extends Type {
  public int line_num, col_num, offset;
  public Int() { }

  public <R,A> R accept(latte_lang.Absyn.Type.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.Int) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return 37;
  }


}
