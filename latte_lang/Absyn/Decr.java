// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

import frontend.SemanticError;

public class Decr  extends Stmt {
  public final String ident_;
  public int line_num, col_num, offset;
  public Decr(String p1) { ident_ = p1; }

  public <R,A> R accept(latte_lang.Absyn.Stmt.Visitor<R,A> v, A arg) throws SemanticError { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.Decr) {
      latte_lang.Absyn.Decr x = (latte_lang.Absyn.Decr)o;
      return this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return this.ident_.hashCode();
  }


}
