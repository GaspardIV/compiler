// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class ClMember implements java.io.Serializable {
  public abstract <R,A> R accept(ClMember.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte.Absyn.ClFields p, A arg);
    public R visit(latte.Absyn.ClMethod p, A arg);

  }

}
