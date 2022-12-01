// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public class BStmt  extends Stmt {
  public final Block block_;
  public int line_num, col_num, offset;
  public BStmt(Block p1) { block_ = p1; }

  public <R,A> R accept(latte.Absyn.Stmt.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.BStmt) {
      latte.Absyn.BStmt x = (latte.Absyn.BStmt)o;
      return this.block_.equals(x.block_);
    }
    return false;
  }

  public int hashCode() {
    return this.block_.hashCode();
  }


}
