// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public class Empty  extends Stmt {
  public int line_num, col_num, offset;
  public Empty() { }

  public <R,A> R accept(latte.Absyn.Stmt.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Empty) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return 37;
  }


}
