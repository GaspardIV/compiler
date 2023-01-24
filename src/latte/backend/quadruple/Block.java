package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class Block {
    private String identifier;
    List<Quadruple> statements;
    Block nextBlock;
    private Scope scope;
    private String name;

    public Block(String contextName, Scope scope) {
        this(contextName, scope, "null");
    }

    public Block(String contextName, Scope scope, String identifier) {
        this.name = contextName;
        this.scope = scope;
        statements = new ArrayList<>();

        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.name + "_" + identifier;
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
        }
    }

    public List<Quadruple> createConditionPhiVariables(Set<String> redefinedVariables, Block block1, Block block2) {
        List<Quadruple> phiVariables = new ArrayList<>();
        for (String variableName : redefinedVariables) {
            Variable variable = scope.getVariable(variableName);
            if (variable == null) continue;
            Register register1 = block1.getScope().getLastRegisterOfVariableRedefinitionInCurrentScope(variable);
            Register register2 = block2.getScope().getLastRegisterOfVariableRedefinitionInCurrentScope(variable);
            Register newRegister = scope.getNewVariableRegister(variable);
            PhiManager.getInstance().markVariableAsRedefined(scope, variable);
            newRegister.setVariable(variable);
            phiVariables.add(new Quadruple(newRegister, new Quadruple.LLVMOperation.PHI(register1, block1, register2, block2)));
        }
        return phiVariables;
    }

    public List<Quadruple> createLoopPhiVariables(Block block1, Block block2) {
        List<Quadruple> phiVariables = new ArrayList<>();
        Collection<Map.Entry<Variable, Register>> phiVariablesRegisters = PhiManager.getInstance().getTopPhiVariables();
        PhiManager.getInstance().popScope();
        for (Map.Entry<Variable, Register> variableRegisterEntry : phiVariablesRegisters) {
            Variable variable = variableRegisterEntry.getKey();
            Register phiRegister = variableRegisterEntry.getValue();
            Register register1 = block1.getScope().getLastRegisterOfVariableRedefinitionInCurrentScope(variable);
            Register register2 = block2.getScope().getLastRegisterOfVariableRedefinitionInCurrentScope(variable);
            scope.setLastVariableRegister(variable, phiRegister);
            phiRegister.setVariable(variable);
            phiVariables.add(new Quadruple(phiRegister, new Quadruple.LLVMOperation.PHI(register1, block1, register2, block2)));
            PhiManager.getInstance().markVariableAsRedefined(scope, variable);
        }
        return phiVariables;
    }

    public void addQuadruplesAtTheBeginning(List<Quadruple> phi1) {
        statements.addAll(0, phi1);
    }

    public Set<String> getRedefinedVariables() {
        return scope.getRedefinedVariables();
    }

    public Scope getScope() {
        return scope;
    }

    public Variable getVariable(String ident_) {
        return scope.getVariable(ident_);
    }

    public String getRegisterNumber(String tmp) {
        if (scope.getCurrentFunction() != null) {
            return scope.getCurrentFunction().getNewIdentUseNumber(tmp);
        } else {
            return tmp;
        }
    }

    public void addQuadruple(Quadruple quadruple) {
        statements.add(quadruple);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public List<Quadruple> getQuadruples() {
        return statements;
    }

    public Block nextBlock() {
        return nextBlock;
    }

    public void setQuadruples(List<Quadruple> newStatements) {
        this.statements = newStatements;
    }

    public void setNextBlock(Block newBlock) {
        this.nextBlock = newBlock;
    }

    public Collection<Register> getDefinedRegisters() {
        Set<Register> definedRegisters = new HashSet<>();
        for (Quadruple quadruple : statements) {
            definedRegisters.add(quadruple.getDefinedRegister());
        }
        return definedRegisters;
    }

    public Collection<Register> getUsedRegisters() {
        Set<Register> usedRegisters = new HashSet<>();
        for (Quadruple quadruple : statements) {
            usedRegisters.addAll(quadruple.getUsedRegisters());
        }
        return usedRegisters;
    }

    public boolean isEmpty() {
        return statements.stream().allMatch(q -> q.toString().equals("") || q.op instanceof Quadruple.LLVMOperation.GOTO || q.op instanceof Quadruple.LLVMOperation.LABEL);
    }

    public boolean overrideIfPossible(Block firstBlock, HashMap<Block, HashSet<Block>> predecessors) {
        boolean isFirstBlock = this == firstBlock;
        Block next = statements.stream().filter(q -> q.op instanceof Quadruple.LLVMOperation.GOTO).map(q -> ((Quadruple.LLVMOperation.GOTO) q.op).block).findFirst().orElse(null);
        if (next != null) {
            if (isFirstBlock && predecessors.get(next).size() > 1) {
                //        Entry block to function must not have predecessors
                return false;
            }else {
                statements.clear();
                this.override(next);
                return true;
            }
        }
        return false;
    }

    public void override(Block next) {
        this.identifier = next.identifier;
        this.name = next.name;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }
}