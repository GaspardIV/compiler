// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class MulOp implements java.io.Serializable {
  public abstract <R,A> R accept(MulOp.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(latte.Absyn.Times p, A arg);
    public R visit(latte.Absyn.Div p, A arg);
    public R visit(latte.Absyn.Mod p, A arg);

  }

}
