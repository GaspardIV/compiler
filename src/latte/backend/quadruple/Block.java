package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class Block {
    private final String identifier;
    List<Quadruple> statements;

    Block nextBlock;
    private Scope scope;

    private final String name;
    private HashMap<Quadruple, HashSet<Register>> liveOut;
    private HashMap<Quadruple, HashSet<Register>> liveIn;
    private HashSet<Register> blockLiveIn;
    private HashSet<Register> blockLiveOut;

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
        return scope.getCurrentFunction().getNewIdentUseNumber(tmp);
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

    public void setLocalLiveIn(HashMap<Quadruple, HashSet<Register>> liveIn) {
        this.liveIn = liveIn;
    }

    public void setLocalLiveOut(HashMap<Quadruple, HashSet<Register>> liveOut) {
        this.liveOut = liveOut;
    }

//    public void updateLocalsLiveInLiveOutWithGlobals(HashSet<Register> in, HashSet<Register> out) {
//        System.out.println("Updating locals live in live out with globals");
//        System.out.println("=========================================");
//        System.out.println("Block: " + this.getIdentifier());
//        System.out.println("Block Live in: " + in);
//        for (Quadruple quadruple : statements) {
//            if (!quadruple.toString().equals("")) {
//                System.out.println("in: " + liveIn.get(quadruple));
//                System.out.println("Quadruple: " + quadruple);
//                System.out.println("out: " + liveOut.get(quadruple));
//            }
//        }
//        System.out.println("Block Live out: " + out);
//        System.out.println("Block: " + this.getIdentifier());
//        System.out.println("=========================================");
//        for (Quadruple quadruple : statements) {
//            HashSet<Register> liveIn = this.liveIn.get(quadruple);
//            HashSet<Register> liveOut = this.liveOut.get(quadruple);
//            liveIn.addAll(in);
//            liveOut.addAll(in);
//        }
////        if (!in.equals(this.liveIn.get(statements.get(0)))) {
////            System.out.println("ERROR");
////            System.out.println("Block: " + this.identifier);
////            System.out.println("Out: " + in);
////            System.out.println("Last: " + this.liveOut.get(statements.get(0)));
////        }
////        if (!out.equals(this.liveOut.get(statements.get(statements.size() - 1)))) {
////            System.out.println("ERROR");
////            System.out.println("Block: " + this.identifier);
////            System.out.println("Out: " + out);
////            System.out.println("Last: " + this.liveOut.get(statements.get(statements.size() - 1)));
////        }
//////        assert ();
//    }

    public void setGlobalsInOut(HashSet<Register> in, HashSet<Register> out) {
        this.blockLiveIn = in;
        this.blockLiveOut = out;
    }

    public HashSet<Register> getGlobalsOut() {
        return blockLiveOut;
    }
}