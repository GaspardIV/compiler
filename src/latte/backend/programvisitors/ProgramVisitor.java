package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.Program;
import java.lang.Void;


public class ProgramVisitor implements TopDef.Visitor<Void, Void> {
    private final Program program;

    public ProgramVisitor(Program program) {
        this.program = program;
    }

    @Override
    public Void visit(FnDef p, Void arg) {
        program.addFunction(p.ident_, p.type_, p.listarg_, ((Blk)p.block_).liststmt_);
        return null;
    }

    @Override
    public Void visit(ClDef p, Void arg) {
        return null;
    }

    @Override
    public Void visit(ClDefExt p, Void arg) {
        if (p == null) return null;
        return null;
    }
}
