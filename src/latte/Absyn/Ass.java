// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

import latte.errors.SemanticError;

public class Ass  extends Stmt {
  public final String ident_;
  public final Expr expr_;
  public int line_num, col_num, offset;
  public Ass(String p1, Expr p2) { ident_ = p1; expr_ = p2; }

  public <R,A> R accept(latte.Absyn.Stmt.Visitor<R,A> v, A arg) throws SemanticError{ return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Ass) {
      latte.Absyn.Ass x = (latte.Absyn.Ass)o;
      return this.ident_.equals(x.ident_) && this.expr_.equals(x.expr_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.ident_.hashCode())+this.expr_.hashCode();
  }


}
