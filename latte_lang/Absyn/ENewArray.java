// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

import frontend.SemanticError;

public class ENewArray  extends Expr {
  public final Type type_;
  public final Expr expr_;
  public int line_num, col_num, offset;
  public ENewArray(Type p1, Expr p2) { type_ = p1; expr_ = p2; }

  public <R,A> R accept(latte_lang.Absyn.Expr.Visitor<R,A> v, A arg) throws SemanticError { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.ENewArray) {
      latte_lang.Absyn.ENewArray x = (latte_lang.Absyn.ENewArray)o;
      return this.type_.equals(x.type_) && this.expr_.equals(x.expr_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.type_.hashCode())+this.expr_.hashCode();
  }


}
