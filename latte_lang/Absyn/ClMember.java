// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

public abstract class ClMember implements java.io.Serializable {
  public abstract <R,A> R accept(ClMember.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte_lang.Absyn.ClField p, A arg);
    public R visit(latte_lang.Absyn.ClMethod p, A arg);

  }

}
