package latte.backend;

import latte.backend.program.global.Scope;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.text.MessageFormat;
import java.util.*;

public class Block extends Scope {
    List<Block> predecessors;
    List<Block> successors;
    List<Quadruple> statements;

    Block nextBlock;
    public boolean markPhiVariables = false;

    boolean wasReturn = false;

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
//        if (successors.size() != 0) {
//            stringBuilder.append("br label %").append(successors.get(0).getName()).append("\n");
//        }
        return MessageFormat.format("{0}: \n{3}", this.getName(), " ; predecessors=" + predecessors,
                " ; successors=" + successors, stringBuilder.toString());
    }

    public boolean isEmpty() {
        return statements.isEmpty();
    }

    public void addPredecessors(Block newBlock) {
        if (!predecessors.contains(newBlock)) {
            predecessors.add(newBlock);
        }
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

    public Block getLastBlock() {
        if (nextBlock != null) {
            return nextBlock.getLastBlock();
        } else {
            return this;
        }
    }

    public void addLastBlock(Block next) {
        if (nextBlock != null) {
            nextBlock.addLastBlock(next);
        } else {
            nextBlock = next;
        }
    }

    public void setMarkPhiVariables(boolean markPhiVariables) {
        this.markPhiVariables = markPhiVariables;
    }

    public List<Quadruple> getPhiVariables(Block entry, Block btrue) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (Quadruple quadruple : statements) {
            if (quadruple.isPhi()) {
                // tod

                Register oldsRegister = quadruple.getRegister().phiRegister;
                Register register = quadruple.getRegister();
                phiVariables.add(new Quadruple(quadruple.result.getVariable().getNewRegister(), new Quadruple.LLVMOperation.PHI(oldsRegister, entry, register, btrue)));
            }
        }
        return phiVariables;
    }

    public void removeDeadCode() {
        removeAfterReturn();
        removeEmptyBlocks();
    }

    private void removeEmptyBlocks() {
        if (nextBlock != null) {
            nextBlock.removeEmptyBlocks();
        }
        if (statements.size() == 1 && statements.get(0).op instanceof Quadruple.LLVMOperation.LABEL) {
            statements = new ArrayList<>();
        }
    }

    private void removeAfterReturn() {
        LinkedList<Block> queue = new LinkedList<>();
        Set<Block> visited = new HashSet<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            Block current = queue.poll();
            visited.add(current);
            List<Quadruple> newStatements = new ArrayList<>();
            current.wasReturn = current.predecessors.size() > 0 && current.predecessors.stream().allMatch(block -> block.wasReturn);
            for (int i = 0; i < current.statements.size(); i++) {
                if (!current.wasReturn) {
                    if (current.statements.get(i).op instanceof Quadruple.LLVMOperation.RET) {
                        current.wasReturn = true;
                    }
                    newStatements.add(current.statements.get(i));
                }
            }
            current.statements = newStatements;
            for (Block succ : current.successors) {
                if (!visited.contains(succ)) {
                    queue.add(succ);
                }
            }
        }
    }
}