package latte.backend;

import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Block extends Scope {
    List<Block> predecessors;
    List<Block> successors;
    List<Quadruple> statements;

    Block nextBlock;

    public Block(String contextName, Scope parent) {
        super(contextName, parent);
        successors = new ArrayList<>();
        predecessors = new ArrayList<>();
        statements = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        statements.forEach(stmt -> stringBuilder.append(stmt.toString()));
        if (successors.size() != 0) {
            stringBuilder.append("br label %").append(successors.get(0).getName()).append("\n");
        }
        return MessageFormat.format("{0}: \n{3}", this.getName(), " ; predecessors=" + predecessors,
                " ; successors=" + successors, stringBuilder.toString());
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }

    public void addSuccessor(Block newBlock) {
        if (!successors.contains(newBlock)) {
            successors.add(newBlock);
        }
    }

    public void addQuadruplesToLastBlock(List<Quadruple> quadruples) {
        if (nextBlock != null) {
            nextBlock.addQuadruplesToLastBlock(quadruples);
        } else {
            statements.addAll(quadruples);
        }
    }

    public List<Quadruple> getQuadruplesFromAllBlocks() {
        List<Quadruple> quadruples = new ArrayList<>(statements);
        if (nextBlock != null) {
            quadruples.addAll(nextBlock.getQuadruplesFromAllBlocks());
        }
        return quadruples;
    }
}