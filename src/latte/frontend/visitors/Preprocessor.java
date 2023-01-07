package latte.frontend.visitors;

import latte.Absyn.*;
import latte.Internal.InternalBlock;
import latte.Internal.Null;

public class Preprocessor implements TopDef.Visitor<Null, Boolean>, Block.Visitor<Null, Boolean>, Stmt.Visitor<Null, Boolean> {

    @Override
    public Null visit(FnDef p, Boolean replaceBlockWithIf) {
        p.block_.accept(this, false);
        return null;
    }

    @Override
    public Null visit(ClDef p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(ClDefExt p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Blk p, Boolean replaceBlockWithIf) {
        for (int i = 0; i < p.liststmt_.size(); i++) {
            if (p.liststmt_.get(i) instanceof Cond) {
                Cond cond = (Cond) p.liststmt_.get(i);
                if (!(cond.stmt_ instanceof BStmt)) {
                    ListStmt listStmt = new ListStmt();
                    listStmt.add(cond.stmt_);
                    p.liststmt_.set(i, new Cond(cond.expr_, new BStmt(new Blk(listStmt))));
                }
            }
            if (p.liststmt_.get(i) instanceof CondElse) {
                CondElse condElse = (CondElse) p.liststmt_.get(i);
                if (!(condElse.stmt_1 instanceof BStmt)) {
                    ListStmt listStmt = new ListStmt();
                    listStmt.add(condElse.stmt_1);
                    p.liststmt_.set(i, new CondElse(condElse.expr_, new BStmt(new Blk(listStmt)), condElse.stmt_2));
                }
                condElse = (CondElse) p.liststmt_.get(i);
                if (!(condElse.stmt_2 instanceof BStmt)) {
                    ListStmt listStmt = new ListStmt();
                    listStmt.add(condElse.stmt_2);
                    p.liststmt_.set(i, new CondElse(condElse.expr_, condElse.stmt_1, new BStmt(new Blk(listStmt))));
                }
            }
            if (p.liststmt_.get(i) instanceof While) {
                While whileStmt = (While) p.liststmt_.get(i);
                if (!(whileStmt.stmt_ instanceof BStmt)) {
                    ListStmt listStmt = new ListStmt();
                    listStmt.add(whileStmt.stmt_);
                    p.liststmt_.set(i, new While(whileStmt.expr_, new BStmt(new Blk(listStmt))));
                }
            }
            p.liststmt_.get(i).accept(this, true);

        }
        return null;
    }


    @Override
    public Null visit(Empty p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(BStmt p, Boolean replaceBlockWithIf) {
        if (replaceBlockWithIf) {
            Blk blk = (Blk) p.block_;
            ListStmt listStmt = new ListStmt();
            listStmt.addAll(blk.liststmt_);
            blk.liststmt_.clear();
            Blk newBlk = new Blk(listStmt);
            blk.liststmt_.add(new InternalBlock(new BStmt(newBlk)));
            newBlk.accept(this, true);

        } else {
            p.block_.accept(this, true);
        }

        return null;
    }

    @Override
    public Null visit(Decl p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Ass p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Incr p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Decr p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Ret p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(VRet p, Boolean replaceBlockWithIf) {
        return null;
    }

    @Override
    public Null visit(Cond p, Boolean replaceBlockWithIf) {
        p.stmt_.accept(this, false);
        return null;
    }

    @Override
    public Null visit(CondElse p, Boolean replaceBlockWithIf) {
        p.stmt_1.accept(this, false);
        p.stmt_2.accept(this, false);
        return null;
    }

    @Override
    public Null visit(While p, Boolean replaceBlockWithIf) {
        p.stmt_.accept(this, false);
        return null;
    }

    @Override
    public Null visit(For p, Boolean replaceBlockWithIf) {
        p.stmt_.accept(this, null);
        return null;
    }

    @Override
    public Null visit(SExp p, Boolean replaceBlockWithIf) {
        return null;
    }
}
