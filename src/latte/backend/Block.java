package latte.backend;

import latte.Absyn.Stmt;
import latte.backend.program.global.Scope;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Block extends Scope {
    List<Block> predecessors;
    List<Block> successors;
    List<Stmt> statements;

    public Block(String contextName, Scope parent) {
        super(contextName, parent);
        successors= new ArrayList<>();
        predecessors = new ArrayList<>();
        statements = new ArrayList<>();
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}: ; {1} {2} \n\n" +
                        "\n\n\n",this.getName(), ":"+ "predecessors=" + predecessors ,
                ", successors=" + successors );
    }

    public void addStatement(Stmt stmt) {
        this.statements.add(stmt);
    }
}