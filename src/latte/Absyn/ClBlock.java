// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class ClBlock implements java.io.Serializable {
  public abstract <R,A> R accept(ClBlock.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte.Absyn.ClBlk p, A arg);

  }

}
