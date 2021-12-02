// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

public class Class  extends Type {
  public final String ident_;
  public int line_num, col_num, offset;
  public Class(String p1) { ident_ = p1; }

  public <R,A> R accept(latte_lang.Absyn.Type.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte_lang.Absyn.Class) {
      latte_lang.Absyn.Class x = (latte_lang.Absyn.Class)o;
      return this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return this.ident_.hashCode();
  }


}
