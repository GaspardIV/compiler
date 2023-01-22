package latte.backend.programvisitors;

import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;

import java.util.*;

public class PostProcessor {
    Block firstBlock;
    HashMap<Block, HashSet<Block>> successors = new HashMap<>();
    HashMap<Block, HashSet<Block>> predecessors = new HashMap<>();
    HashMap<Block, Boolean> wasReturn = new HashMap<>();

    public PostProcessor(Block firstBlock) {
        this.firstBlock = firstBlock;
        prepareBlocks();
    }

    private void initIfNotYet(Block block) {
        successors.putIfAbsent(block, new HashSet<>());
        predecessors.putIfAbsent(block, new HashSet<>());
        wasReturn.putIfAbsent(block, false);
    }

    private void prepareBlocks() {
        Block currentBlock = firstBlock;
        if (currentBlock == null) return;
        Block nextBlock = currentBlock;
        while (nextBlock != null) {
            splitIfBlockContainsOtherBlocks(nextBlock);
            initIfNotYet(nextBlock);
            nextBlock = nextBlock.nextBlock();
        }
        nextBlock = currentBlock;
        while (nextBlock != null) {
            for (Quadruple lastQuadruple : nextBlock.getQuadruples()) {
                if (lastQuadruple.op instanceof Quadruple.LLVMOperation.GOTO) {
                    Block successor = ((Quadruple.LLVMOperation.GOTO) lastQuadruple.op).block;
                    addEdge(nextBlock, successor);
                } else if (lastQuadruple.op instanceof Quadruple.LLVMOperation.IF) {
                    Block successorTrue = ((Quadruple.LLVMOperation.IF) lastQuadruple.op).block1;
                    Block successorFalse = ((Quadruple.LLVMOperation.IF) lastQuadruple.op).block2;
                    addEdge(nextBlock, successorTrue);
                    addEdge(nextBlock, successorFalse);
                }
            }
            nextBlock = nextBlock.nextBlock();
        }
    }

    private void splitIfBlockContainsOtherBlocks(Block currentBlock) {
        List<Quadruple> all = currentBlock.getQuadruples();
        List<Quadruple> res = new ArrayList<>();
        Block nextOfLastBlock = currentBlock.nextBlock();
        for (Quadruple quadruple : all) {
            if (quadruple.op instanceof Quadruple.LLVMOperation.LABEL) {
                currentBlock.setQuadruples(res);
                res = new ArrayList<>();
                Block newBlock = ((Quadruple.LLVMOperation.LABEL) quadruple.op).label;
                if (currentBlock != newBlock) {
                    currentBlock.setNextBlock(newBlock);
                }
                currentBlock = newBlock;
            }
            res.add(quadruple);
        }
        currentBlock.setQuadruples(res);
        currentBlock.setNextBlock(nextOfLastBlock);
    }

    private void addEdge(Block currentBlock, Block successor) {
            successors.get(currentBlock).add(successor);
            predecessors.get(successor).add(currentBlock);
    }

    public void removeJumpsToNonExistentBlocks() {
        List<Quadruple> all = firstBlock.getQuadruplesFromAllBlocks();
        Set<Block> existingBlocks = getExistingBlocks(all);
        Block block = firstBlock;
        for (Quadruple quadruple : all) {
            if (quadruple.op instanceof Quadruple.LLVMOperation.LABEL) {
                block = ((Quadruple.LLVMOperation.LABEL) quadruple.op).label;
            } else if (quadruple.op instanceof Quadruple.LLVMOperation.IF) {
                Quadruple.LLVMOperation.IF ifOp = (Quadruple.LLVMOperation.IF) quadruple.op;
                if (!existingBlocks.contains(ifOp.block1) && existingBlocks.contains(ifOp.block2)) {
                    quadruple.op = new Quadruple.LLVMOperation.GOTO(ifOp.block2);
                    removeEdge(block, ifOp.block1);
                } else if (!existingBlocks.contains(ifOp.block2) && existingBlocks.contains(ifOp.block1)) {
                    quadruple.op = new Quadruple.LLVMOperation.GOTO(ifOp.block1);
                    removeEdge(block, ifOp.block2);
                } else if (!existingBlocks.contains(ifOp.block1) && !existingBlocks.contains(ifOp.block2)) {
                    quadruple.op = null;
                    removeEdge(block, ifOp.block1);
                    removeEdge(block, ifOp.block2);
                }
            } else if (quadruple.op instanceof Quadruple.LLVMOperation.GOTO) {
                Quadruple.LLVMOperation.GOTO gotoOp = (Quadruple.LLVMOperation.GOTO) quadruple.op;
                if (!existingBlocks.contains(gotoOp.block)) {
                    quadruple.op = null;
                    removeEdge(block, gotoOp.block);
                }
            }
        }
    }

    private void removeEdge(Block block, Block destination) {
            successors.get(block).remove(destination);
            predecessors.get(destination).remove(block);
    }

    private Set<Block> getExistingBlocks(List<Quadruple> all) {
        Set<Block> existingBlocks = new HashSet<>();
        for (Quadruple quadruple : all) {
            if (quadruple.op instanceof Quadruple.LLVMOperation.LABEL) {
                existingBlocks.add(((Quadruple.LLVMOperation.LABEL) quadruple.op).label);
            }
        }
        return existingBlocks;
    }
    public void removePhiFromNonPredecessorsBlocks() {
        List<Quadruple> all = firstBlock.getQuadruplesFromAllBlocks();
        Set<Block> existingBlocks2 = getExistingBlocks(all);

        Block block = firstBlock;
        for (Quadruple quadruple : all) {
            if (quadruple.op instanceof Quadruple.LLVMOperation.LABEL) {
                block = ((Quadruple.LLVMOperation.LABEL) quadruple.op).label;
            } else if (quadruple.op instanceof Quadruple.LLVMOperation.PHI) {
                Quadruple.LLVMOperation.PHI phiOp = (Quadruple.LLVMOperation.PHI) quadruple.op;
                if ((!predecessors.get(block).contains(phiOp.block1) || !existingBlocks2.contains(phiOp.block1))) {
                    quadruple.getRegister().setOverride(phiOp.register2);
                    quadruple.op = null;
                }
                if ((!predecessors.get(block).contains(phiOp.block2) || !existingBlocks2.contains(phiOp.block2))) {
                    quadruple.getRegister().setOverride(phiOp.register1);
                    quadruple.op = null;
                }
            }
        }
    }

    public void process() {
        removeEmptyBlocks(firstBlock);
        removeAfterReturn();
        removeEmptyBlocks(firstBlock);
        removeJumpsToNonExistentBlocks();
        removeEmptyBlocks(firstBlock);
        removePhiFromNonPredecessorsBlocks();
        removeEmptyBlocks(firstBlock);
    }

    private void removeEmptyBlocks(Block current) {
        if (current == null) return;
        if (current.getQuadruples().size() == 0 ||
                (((current.getQuadruples().size() == 1 && current.getQuadruples().get(0).op instanceof Quadruple.LLVMOperation.LABEL)
                        || predecessors.get(current).size() == 0) && current != firstBlock)) {
            for (Block successor : successors.get(current)) {
                predecessors.get(successor).remove(current);
            }
            for (Block predecessor : predecessors.get(current)) {
                successors.get(predecessor).remove(current);
            }
            successors.get(current).clear();
            predecessors.get(current).clear();
            current.setQuadruples(new ArrayList<>());
        }
        removeEmptyBlocks(current.nextBlock());
    }

    private void removeAfterReturn() {
        LinkedList<Block> queue = new LinkedList<>();
        Set<Block> visited = new HashSet<>();
        queue.add(firstBlock);
        while (!queue.isEmpty()) {
            Block current = queue.poll();
            visited.add(current);
            wasReturn.put(current, predecessors.get(current).size() > 0 && predecessors.get(current).stream().allMatch(block -> wasReturn.get(block)));
            List<Quadruple> statements = current.getQuadruples();
            List<Quadruple> newStatements = new ArrayList<>();
            for (Block succ : successors.get(current)) {
                if (!visited.contains(succ)) {
                    queue.add(succ);
                }
            }
            for (Quadruple statement : statements) {
                if (!wasReturn.get(current)) {
                    if (statement.op instanceof Quadruple.LLVMOperation.RET) {
                        wasReturn.put(current, true);
                    }
                    newStatements.add(statement);
                } else {
                    if (statement.op instanceof Quadruple.LLVMOperation.GOTO) {
                        removeEdge(current, ((Quadruple.LLVMOperation.GOTO) statement.op).block);
                    } else if (statement.op instanceof Quadruple.LLVMOperation.IF) {
                        Quadruple.LLVMOperation.IF ifOp = (Quadruple.LLVMOperation.IF) statement.op;
                        removeEdge(current, ifOp.block1);
                        removeEdge(current, ifOp.block2);
                    }
                }
            }
            current.setQuadruples(newStatements);
        }
    }

    public List<Quadruple> getQuadruples() {
        return firstBlock.getQuadruplesFromAllBlocks();
    }
}
