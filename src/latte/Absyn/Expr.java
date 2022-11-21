// File generated by the BNF Converter (bnfc 2.9.4).

package latte.Absyn;

public abstract class Expr implements java.io.Serializable {
  public abstract <R,A> R accept(Expr.Visitor<R,A> v, A arg) throws Exception;
  public interface Visitor <R,A> {
    R visit(latte.Absyn.ENewArray p, A arg) throws Exception;
    R visit(latte.Absyn.EArrayElem p, A arg) throws Exception;
    R visit(latte.Absyn.ENew p, A arg) throws Exception;
    R visit(latte.Absyn.EMethod p, A arg) throws Exception;
    R visit(latte.Absyn.EField p, A arg) throws Exception;
    R visit(latte.Absyn.EVar p, A arg) throws Exception;
    R visit(latte.Absyn.ELitInt p, A arg);
    R visit(latte.Absyn.ELitTrue p, A arg);
    R visit(latte.Absyn.ELitFalse p, A arg);
    R visit(latte.Absyn.EApp p, A arg) throws Exception;
    R visit(latte.Absyn.EString p, A arg);
    R visit(latte.Absyn.Neg p, A arg) throws Exception;
    R visit(latte.Absyn.Not p, A arg) throws Exception;
    R visit(latte.Absyn.EMul p, A arg) throws Exception;
    R visit(latte.Absyn.EAdd p, A arg) throws Exception;
    R visit(latte.Absyn.ERel p, A arg) throws Exception;
    R visit(latte.Absyn.EAnd p, A arg) throws Exception;
    R visit(latte.Absyn.EOr p, A arg) throws Exception;
    R visit(latte.Absyn.ESelf p, A arg);
    R visit(latte.Absyn.ENull p, A arg);
    R visit(latte.Absyn.ECastNull p, A arg);
  }

}
