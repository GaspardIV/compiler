// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class Block implements java.io.Serializable {
  public abstract <R,A> R accept(Block.Visitor<R,A> v, A arg) throws Exception;
  public interface Visitor <R,A> {
    R visit(latte.Absyn.Blk p, A arg) throws Exception;

  }

}
