package latte.Internal;

import latte.Absyn.BStmt;
import latte.Absyn.Stmt;

public class InternalBlock extends Stmt {
    public final BStmt bStmt_;
    public InternalBlock(BStmt bStmt) {
        this.bStmt_ = bStmt;
    }

    @Override
    public <R, A> R accept(Visitor<R, A> v, A arg) {
        return null;
    }
}
