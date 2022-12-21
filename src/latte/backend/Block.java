package latte.backend;

import latte.Absyn.Stmt;
import latte.backend.program.global.Scope;
import latte.backend.programvisitors.StatementVisitor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Block extends Scope {
    List<Block> predecessors;
    List<Block> successors;
    List<Stmt> statements;

    public Block(String contextName, Scope parent) {
        super(contextName, parent);
        successors = new ArrayList<>();
        predecessors = new ArrayList<>();
        statements = new ArrayList<>();
    }

    @Override
    public String toString() {
        StatementVisitor statementVisitor = new StatementVisitor(/*environment*/);
        StringBuilder stringBuilder = new StringBuilder();
        statements.forEach(stmt -> stringBuilder.append(stmt.accept(statementVisitor, this)));
        if (successors.size() != 0) {
            stringBuilder.append("br label %").append(successors.get(0).getName()).append("\n");
        }
        return MessageFormat.format("{0}: \n{3}", this.getName(), " ; predecessors=" + predecessors,
                " ; successors=" + successors, stringBuilder.toString());
    }

    public void addStatement(Stmt stmt) {
        this.statements.add(stmt);
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }

    public void addSuccessor(Block newBlock) {
        if (!successors.contains(newBlock)) {
            successors.add(newBlock);
        }
    }
}