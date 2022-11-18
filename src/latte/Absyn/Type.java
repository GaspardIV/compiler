// File generated by the BNF Converter (bnfc 2.9.3).

package latte.Absyn;

public abstract class Type implements java.io.Serializable {
  public abstract <R,A> R accept(Type.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    R visit(latte.Absyn.Int p, A arg);
    R visit(latte.Absyn.Str p, A arg);
    R visit(latte.Absyn.Bool p, A arg);
    R visit(latte.Absyn.Void p, A arg);
    R visit(latte.Absyn.Class p, A arg);
    R visit(latte.Absyn.Array p, A arg);
    R visit(latte.Absyn.Fun p, A arg);
  }


}
