// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class AddOp implements java.io.Serializable {
  public abstract <R,A> R accept(AddOp.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte.Absyn.Plus p, A arg);
    public R visit(latte.Absyn.Minus p, A arg);

  }

}
