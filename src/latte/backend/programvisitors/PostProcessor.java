package latte.backend.programvisitors;

import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

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
        removeAfterReturn();
        removeDeadBlocks(firstBlock);
        removeJumpsToNonExistentBlocks();
        removePhiFromNonPredecessorsBlocks();
        removeDeadBlocks(firstBlock);
        globalCommonSubexpressionElimination();
        deadVariablesElimination();
        removeEmptyBlocks();
    }

    private void removeEmptyBlocks() {
        List<Block> blocks = getAllBlocks();
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<Block> phiBlocks = new HashSet<>();
            for (Block block : blocks) {
                for (Quadruple quadruple : block.getOverriden().getQuadruples()) {
                    if (quadruple.op instanceof Quadruple.LLVMOperation.PHI) {
                        phiBlocks.add(block.getOverriden());
                        Block block1 = ((Quadruple.LLVMOperation.PHI) quadruple.op).block1.getOverriden();
                        Block block2 = ((Quadruple.LLVMOperation.PHI) quadruple.op).block2.getOverriden();

                        phiBlocks.add(block1.getOverriden());
                        phiBlocks.add(block2.getOverriden());
                    } else if (quadruple.op instanceof Quadruple.LLVMOperation.IF) {
                        Block block1 = ((Quadruple.LLVMOperation.IF) quadruple.op).block1.getOverriden();
                        Block block2 = ((Quadruple.LLVMOperation.IF) quadruple.op).block2.getOverriden();
                        if (block1.getIdentifier().equals(block2.getIdentifier())) {
                            quadruple.op = new Quadruple.LLVMOperation.GOTO(block1.getOverriden());
                            changed = true;
                        }
                    }
                }
            }
            for (Block block : blocks) {
                if (block.getOverriden().isEmpty() && !phiBlocks.contains(block.getOverriden())) {
                    changed = block.getOverriden().overrideIfPossible(firstBlock.getOverriden(), predecessors) || changed;
                }
            }
        }
    }

    private List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        HashSet<Block> visited = new HashSet<>();
        Block block = firstBlock;
        Deque<Block> stack = new ArrayDeque<>();
        stack.add(block);
        while (!stack.isEmpty()) {
            block = stack.pop();
            if (visited.contains(block)) {
                continue;
            }
            allBlocks.add(block);
            visited.add(block);
            stack.addAll(successors.get(block));
        }
        return allBlocks;
    }

    private void deadVariablesElimination() {
        List<Block> allBlocks = getAllBlocks();
        HashMap<Block, HashSet<Register>> globalsOut = calculateGlobalBlocksLivelinessOut(allBlocks, successors);
        for (Block block : allBlocks) {
            removeDeadVariables(block, globalsOut.get(block));
        }
    }

    private HashMap<Block, HashSet<Register>> calculateGlobalBlocksLivelinessOut(Collection<Block> allBlocks,
                                                                                 HashMap<Block, HashSet<Block>> successors) {
        HashMap<Block, HashSet<Register>> in = new HashMap<>();
        HashMap<Block, HashSet<Register>> out = new HashMap<>();
        HashMap<Block, HashSet<Register>> in0 = new HashMap<>();
        HashMap<Block, HashSet<Register>> out0 = new HashMap<>();

        for (Block block : allBlocks) {
            in.put(block, new HashSet<>());
            out.put(block, new HashSet<>());
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : allBlocks) {
                in0.put(block, new HashSet<>(in.get(block)));
                out0.put(block, new HashSet<>(out.get(block)));

                HashSet<Register> blockKillVariables = new HashSet<>(block.getDefinedRegisters());
                HashSet<Register> blockUseVariables = new HashSet<>(block.getUsedRegisters());

                HashSet<Register> blockInVariables = new HashSet<>(blockUseVariables);
                blockInVariables.addAll(out.get(block));
                blockInVariables.removeAll(blockKillVariables);

                in.put(block, blockInVariables);
                HashSet<Register> blockOutVariables = new HashSet<>();
                for (Block successor : successors.get(block)) {
                    blockOutVariables.addAll(in.get(successor));
                }
                out.put(block, blockOutVariables);

                if (!in0.get(block).equals(in.get(block)) || !out0.get(block).equals(out.get(block))) {
                    changed = true;
                }
            }
        }
        return out;
    }


    private void removeDeadVariables(Block block, HashSet<Register> lives) {
        for (int i = block.getQuadruples().size() - 1; i >= 0; i--) {
            Quadruple quadruple = block.getQuadruples().get(i);
            if (quadruple.hasSideEffects() || lives.contains(quadruple.getRegister())) {
                lives.remove(quadruple.getDefinedRegister());
                lives.addAll(quadruple.getUsedRegisters());
            } else {
                quadruple.op = null;
            }
        }
    }

    static class GCSEPair {
        public Block block;
        public Quadruple quadruple;

        public GCSEPair(Block block, Quadruple quadruple) {
            this.block = block;
            this.quadruple = quadruple;
        }


        @Override
        public String toString() {
            return "GCSEPair{" +
                    "block=" + block +
                    ", quadruple=" + quadruple +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GCSEPair pair = (GCSEPair) o;
            return Objects.equals(pair.toString(), toString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(toString());
        }
    }

    private void globalCommonSubexpressionElimination() {
        HashMap<Block, HashSet<Block>> dom = computeDominates(); // node d in DOM(n) iff d dominates n
        HashMap<Quadruple.LLVMOperation, Set<GCSEPair>> definitions = new HashMap<>();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : getAllBlocks()) {
                for (Quadruple quadruple : block.getQuadruples()) {
                    if (!quadruple.isDefinition()) {
                        continue;
                    }
                    if (!definitions.containsKey(quadruple.op)) {
                        definitions.put(quadruple.op, new HashSet<>());
                        definitions.get(quadruple.op).add(new GCSEPair(block, quadruple));
                    } else {
                        Set<GCSEPair> blocks = definitions.get(quadruple.op);
                        Set<GCSEPair> toRemove = new HashSet<>();
                        boolean found = false;
                        for (GCSEPair pair : blocks) {
                            if (quadruple == pair.quadruple) {
                                found = true;
                            } else if (dom.get(block).contains(pair.block)) {
                                quadruple.getRegister().setOverride(pair.quadruple.getRegister());
                                quadruple.op = null;
                                changed = true;
                                found = true;
                                break;
                            } else if (dom.get(pair.block).contains(block)) {
                                pair.quadruple.getRegister().setOverride(quadruple.getRegister());
                                pair.quadruple.op = null;
                                toRemove.add(pair);
                                definitions.get(quadruple.op).add(new GCSEPair(block, quadruple));
                                changed = true;
                                found = true;
                                break;
                            }
                        }
                        if (!found && quadruple.op != null) {
                            definitions.get(quadruple.op).add(new GCSEPair(block, quadruple));
                        }
                        blocks.removeAll(toRemove);
                    }
                }
            }
        }
    }

    private HashMap<Block, HashSet<Block>> computeDominates() {
        HashMap<Block, HashSet<Block>> nodeDominatedBy = new HashMap<>();
        HashMap<Block, HashSet<Block>> nodeDominatedBy0 = new HashMap<>();

        for (Block block : getAllBlocks()) {
            nodeDominatedBy.put(block, new HashSet<>());
            nodeDominatedBy0.put(block, new HashSet<>());
        }
        nodeDominatedBy.get(firstBlock).add(firstBlock);
        for (Block block : getAllBlocks()) {
            if (block == firstBlock) {
                continue;
            }
            nodeDominatedBy.get(block).addAll(getAllBlocks());
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : getAllBlocks()) {
                if (block == firstBlock) {
                    continue;
                }
                nodeDominatedBy0.put(block, new HashSet<>(nodeDominatedBy.get(block)));
                nodeDominatedBy.get(block).clear();
                nodeDominatedBy.get(block).add(block);
                HashSet<Block> intersection = new HashSet<>();
                for (Block predecessor : predecessors.get(block)) {
                    if (intersection.isEmpty()) {
                        intersection.addAll(nodeDominatedBy.get(predecessor));
                    } else {
                        intersection.retainAll(nodeDominatedBy.get(predecessor));
                    }
                }
                nodeDominatedBy.get(block).addAll(intersection);

                if (!nodeDominatedBy0.get(block).equals(nodeDominatedBy.get(block))) {
                    changed = true;
                }
            }
        }

        return nodeDominatedBy;
    }

    private void removeDeadBlocks(Block current) {
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
        removeDeadBlocks(current.nextBlock());
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
