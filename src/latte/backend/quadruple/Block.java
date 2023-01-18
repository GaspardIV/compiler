package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class Block {
    private String identifier;
    List<Block> predecessors;
    List<Block> successors;
    List<Quadruple> statements;

    Block nextBlock;
    Block previousBlock;
    public static boolean markPhiVariables = false;
    public static HashMap<String, Register> phiRegisterOfVariable = new HashMap<>();

    boolean wasReturn = false;

    private Scope scope;

    private String name;
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

    public static void reset() {
        markPhiVariables = false;
        phiRegisterOfVariable = new HashMap<>();
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

    public List<Quadruple> createPhiVariables(Set<String> phiVariablesNames, Block entry, Block btrue) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : phiVariablesNames) {
            Variable variable = scope.getVariable(variableName);
            Register register = btrue.getScope().getLastRegisterOfVariable(variable);
            Register oldsRegister = entry.getScope().getLastRegisterOfVariable(variable);
            Register newRegister = scope.getNewVariableRegister(variable);
            newRegister.setVariable(variable);
            phiVariables.add(new Quadruple(newRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, entry, register, btrue)));
        }
        return phiVariables;
    }

    public List<Quadruple> getPhiVariables(List<String> variableNames, Block oldblock, Block newblock) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : variableNames) {
            Register phiRegister = phiRegisterOfVariable.get(variableName);
            if (phiRegister == null) {
                continue;
            }
            Variable variable = scope.getVariable(variableName);
            Register register = newblock.getScope().getLastRegisterOfVariable(variable);
            Register oldsRegister = oldblock.getScope().getLastRegisterOfVariable(variable);

            scope.setLastRegisterOfVariable(variable, phiRegister);
            scope.setLastVariableRegister(variable, phiRegister);
            phiRegister.setVariable(variable);

            phiVariables.add(new Quadruple(phiRegister, new Quadruple.LLVMOperation.PHI(oldsRegister, oldblock, register, newblock)));
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
                    if (current.statements.get(i).op instanceof Quadruple.LLVMOperation.RET ) {
                        current.wasReturn = true;
                    }
//  todo || (current.statements.get(i).op instanceof Quadruple.LLVMOperation.CALL && ((Quadruple.LLVMOperation.CALL) current.statements.get(i).op).name.equals("error"))
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

    public void addQuadruplesAtTheBeginning(List<Quadruple> phi1) {
        statements.addAll(0, phi1);
    }

    public Set<String> getRedefinedVariables() {
        return scope.getRedefinedVariables();
    }


    public boolean hasPhiRegisterOfVariable(String ident_) {
        return phiRegisterOfVariable.containsKey(ident_) || scope.hasPhiRegisterOfVariable(ident_);
    }

    public Set<String> getUsedVariables() {
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

    public void addQuadruple(Quadruple quadruple) {
        statements.add(quadruple);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void resetLastUseOfVariables(Scope condScope) {
        scope.resetLastUseOfVariables(condScope);
    }

    public void setLastRegisterOfVariable(Variable variable, Register rightLastRegister) {
        scope.setLastRegisterOfVariable(variable, rightLastRegister);
    }

    public void setIdentifier(Block identifier) {
        this.name = identifier.name;
        this.identifier = identifier.identifier;
    }
}