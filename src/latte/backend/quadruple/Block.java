package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class Block {
    private final String identifier;
    List<Block> predecessors;
    List<Block> successors;
    List<Quadruple> statements;

    Block nextBlock;
    Block previousBlock;
    public boolean markPhiVariables = false;

    boolean wasReturn = false;

    private Scope scope;

    private final String name;
    public Block(String contextName, Scope scope) {
        this(contextName, scope, "null");
    }

    public Block(String contextName, Scope scope, String identifier) {
        this.name = contextName;
        this.scope = scope;
        successors = new ArrayList<>();
        predecessors = new ArrayList<>();
        statements = new ArrayList<>();

        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.name + "_" + identifier;
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

    public void addQuadruples(List<Quadruple> quadruples) {
        statements.addAll(quadruples);
    }
    public void addStatement(Quadruple statement) {
        statements.add(statement);
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
            next.previousBlock = this;
        }
    }

    public void setMarkPhiVariables(boolean markPhiVariables) {
        this.markPhiVariables = markPhiVariables;
    }


    public static List<Quadruple> createPhiVariables(List<String> phiVariablesNames, Block entry, Block btrue) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : phiVariablesNames) {
            Register register = btrue.getScope().getLastRegisterOfVariable(variableName);
            Register oldsRegister = entry.getScope().getLastRegisterOfVariable(variableName);
            // todo tutaj getNewRegister zwraca i1, a nie i2
            Register newRegister = entry.scope.getNewVariableRegister(entry.scope.getVariable(variableName));
            phiVariables.add(new Quadruple(newRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, entry, register, btrue)));
        }
        return phiVariables;
    }

    public List<Quadruple> getPhiVariables(List<String> variableNames, Block oldblock, Block newblock) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : variableNames) {
            Register phiRegister = scope.getPhiRegisterOfVariable(variableName);
            if (phiRegister == null && newblock.getScope().getPhiRegisterOfVariable(variableName) != null) {
                phiRegister = newblock.getScope().getPhiRegisterOfVariable(variableName);
            }
            Register register = newblock.getScope().getLastRegisterOfVariable(variableName);
            Register oldsRegister = oldblock.getScope().getLastRegisterOfVariable(variableName);


            if (scope.getLastRegisterOfVariable(variableName) == null) {
//                lastRegisterOfVariable.put(variableName, register);
                scope.setLastVariableRegister(scope.getVariable(variableName), phiRegister);
            }
            phiVariables.add(new Quadruple(phiRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, oldblock, register, newblock)));
        }
        return phiVariables;
    }

//    private Register getlastRegisterOfVariable(String variableName) {
//        if (lastRegisterOfVariable.get(variableName) != null) {
//            return lastRegisterOfVariable.get(variableName);
//        } else {
//            return previousBlock.getlastRegisterOfVariable(variableName);
//        }
//    }

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

//    public void setLastRegisterOfVariable(String ident_, Register last) {
//        this.lastRegisterOfVariable.put(ident_, last);
//    }

//    public void setPhiRegisterOfVariable(String ident_, Register first) {
//        if (!this.phiRegisterOfVariable.containsKey(ident_)) {
//            this.phiRegisterOfVariable.put(ident_, first);
//        }
//    }

    public void addQuadruplesAtTheBeginning(List<Quadruple> phi1) {
        statements.addAll(0, phi1);
    }

    public List<String> getRedefinedVariables() {
        return scope.getRedefinedVariables();
    }


    public boolean hasPhiRegisterOfVariable(String ident_) {
        return scope.hasPhiRegisterOfVariable(ident_);
    }

    public void pastePhiVariables(Block cond) {
        scope.pastePhiVariables(cond.scope);
    }

    public Collection<String> getUsedVariables() {
        return scope.getUsedVariables();
    }

    public Scope getScope() {
        return scope;
    }

    public Variable getVariable(String ident_) {
        return scope.getVariable(ident_);
    }

    public String getRegisterNumber(String tmp) {
        return scope.getCurrentFunction().getNewIdentUseNumber(tmp);
    }

    public void setNextBlock(Block body) {
        this.nextBlock = body;
        body.previousBlock = this;
    }

    public void addQuadruple(Quadruple quadruple) {
        statements.add(quadruple);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPreviousBlock(Block entry) {
        this.previousBlock = entry;
    }

    public List<Quadruple> getQuadruples() {
        return statements;
    }

    public void resetLastUseOfVariables(Scope condScope) {
        scope.resetLastUseOfVariables(condScope);
    }
}