// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public class ClMethod  extends ClMember {
  public final Type type_;
  public final String ident_;
  public final ListArg listarg_;
  public final Block block_;
  public int line_num, col_num, offset;
  public ClMethod(Type p1, String p2, ListArg p3, Block p4) { type_ = p1; ident_ = p2; listarg_ = p3; block_ = p4; }

  public <R,A> R accept(latte.Absyn.ClMember.Visitor<R,A> v, A arg) throws Exception { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof latte.Absyn.ClMethod) {
      latte.Absyn.ClMethod x = (latte.Absyn.ClMethod)o;
      return this.type_.equals(x.type_) && this.ident_.equals(x.ident_) && this.listarg_.equals(x.listarg_) && this.block_.equals(x.block_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(37*(this.type_.hashCode())+this.ident_.hashCode())+this.listarg_.hashCode())+this.block_.hashCode();
  }


}
