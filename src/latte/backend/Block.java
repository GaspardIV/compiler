package latte.backend;

import latte.Absyn.Stmt;
import latte.backend.program.global.Scope;
import latte.frontend.visitors.programvisitors.StatementVisitor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Block extends Scope {
    List<Block> predecessors;
    List<Block> successors;
    List<Stmt> statements;

//    Environment environment;

//    public Block(String contextName, Scope parent, Environment environment) {
    public Block(String contextName, Scope parent) {
        super(contextName, parent);
        successors= new ArrayList<>();
        predecessors = new ArrayList<>();
        statements = new ArrayList<>();
//        this.environment = environment;
    }

    @Override
    public String toString() {
        StatementVisitor statementVisitor = new StatementVisitor(/*environment*/);
        StringBuilder stringBuilder = new StringBuilder();
        statements.forEach(stmt -> stringBuilder.append(stmt.accept(statementVisitor, this)));
        return MessageFormat.format("{0}: ; {1} {2} \n {3}",this.getName(), ":"+ "predecessors=" + predecessors ,
                ", successors=" + successors, stringBuilder.toString());
    }

    public void addStatement(Stmt stmt) {
        this.statements.add(stmt);
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }
}