// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class Blk  extends Block {
  public final ListStmt liststmt_;
  public int line_num, col_num, offset;
  public Blk(ListStmt p1) { liststmt_ = p1; }

  public <R,A> R accept(latte.Absyn.Block.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Blk) {
      latte.Absyn.Blk x = (latte.Absyn.Blk)o;
      return this.liststmt_.equals(x.liststmt_);
    }
    return false;
  }

  public int hashCode() {
    return this.liststmt_.hashCode();
  }


}
