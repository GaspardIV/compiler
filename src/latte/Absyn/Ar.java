// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public class Ar  extends Arg {
  public final Type type_;
  public final String ident_;
  public int line_num, col_num, offset;
  public Ar(Type p1, String p2) { type_ = p1; ident_ = p2; }

  public <R,A> R accept(latte.Absyn.Arg.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.Ar) {
      latte.Absyn.Ar x = (latte.Absyn.Ar)o;
      return this.type_.equals(x.type_) && this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.type_.hashCode())+this.ident_.hashCode();
  }


}