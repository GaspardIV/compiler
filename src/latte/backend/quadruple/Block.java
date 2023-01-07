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
    private final HashMap<String, Register> phiRegisterOfVariable;
    private final HashMap<String, Register> lastRegisterOfVariable;

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
        lastRegisterOfVariable = new HashMap<>();
        phiRegisterOfVariable = new HashMap<>();
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
            Register register = btrue.getlastRegisterOfVariable(variableName);
            Register oldsRegister = entry.getlastRegisterOfVariable(variableName);
            // todo tutaj getNewRegister zwraca i1, a nie i2
            Register newRegister = entry.scope.getNewVariableRegister(entry.scope.getVariable(variableName));
            phiVariables.add(new Quadruple(newRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, entry, register, btrue)));
        }
        return phiVariables;
    }

    public List<Quadruple> getPhiVariables(List<String> variableNames, Block oldblock, Block newblock) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : variableNames) {
            Register phiRegister = phiRegisterOfVariable.get(variableName);
            if (phiRegister == null && newblock.phiRegisterOfVariable.get(variableName) != null) {
                phiRegister = newblock.phiRegisterOfVariable.get(variableName);
            }
            Register register = newblock.getlastRegisterOfVariable(variableName);
            Register oldsRegister = oldblock.getlastRegisterOfVariable(variableName);


            if (lastRegisterOfVariable.get(variableName) == null) {
                lastRegisterOfVariable.put(variableName, register);
                scope.setLastVariableRegister(scope.getVariable(variableName), phiRegister);
            }
            phiVariables.add(new Quadruple(phiRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, oldblock, register, newblock)));
        }
        return phiVariables;
    }

    private Register getlastRegisterOfVariable(String variableName) {
        if (lastRegisterOfVariable.get(variableName) != null) {
            return lastRegisterOfVariable.get(variableName);
        } else {
            return previousBlock.getlastRegisterOfVariable(variableName);
        }
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

    public void setLastRegisterOfVariable(String ident_, Register last) {
        this.lastRegisterOfVariable.put(ident_, last);

    }

    public void setPhiRegisterOfVariable(String ident_, Register first) {
        if (!this.phiRegisterOfVariable.containsKey(ident_)) {
            this.phiRegisterOfVariable.put(ident_, first);
        }
    }

    public void addQuadruplesAtTheBeginning(List<Quadruple> phi1) {
        statements.addAll(0, phi1);
    }

    public List<String> getRedefinedVariables() {
        return new ArrayList<>(lastRegisterOfVariable.keySet());
    }

    public void resetLastUseOfVariables() {
        for (Map.Entry<String, Register> entry : lastRegisterOfVariable.entrySet()) {
            scope.setLastVariableRegister(scope.getVariable(entry.getKey()), entry.getValue());
        }
    }

    public boolean hasPhiRegisterOfVariable(String ident_) {
        return phiRegisterOfVariable.containsKey(ident_);
    }

    public void pastePhiVariables(Block cond) {
        for (Map.Entry<String, Register> entry : cond.phiRegisterOfVariable.entrySet()) {
            if (!phiRegisterOfVariable.containsKey(entry.getKey())) {
                phiRegisterOfVariable.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Collection<String> getUsedVariables() {
        return phiRegisterOfVariable.keySet();
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
}