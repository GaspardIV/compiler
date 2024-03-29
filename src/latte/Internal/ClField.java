// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Internal;

import latte.Absyn.ClMember;
import latte.Absyn.Type;

public class ClField  extends ClMember {
  public final Type type_;
  public final String ident_;
  public int line_num, col_num, offset;
  public ClField(Type p1, String p2) { type_ = p1; ident_ = p2; }

  public <R,A> R accept(latte.Absyn.ClMember.Visitor<R,A> v, A arg) { throw new RuntimeException("not implemented ????");}

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof ClField) {
      ClField x = (ClField)o;
      return this.type_.equals(x.type_) && this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.type_.hashCode())+this.ident_.hashCode();
  }


}
