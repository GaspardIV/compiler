package latte.backend.program.global;

import latte.Absyn.Type;
import latte.backend.quadruple.Register;

import java.util.*;

public class Scope {
    private final Scope parent;
    String contextName;
    final Map<String, Variable> variables;

    final Map<Variable, Deque<Register>> memoryLocations;

    private final Type type;

    public Type getType() {
        if (type == null) {
            return parent.getType();
        } else {
            return type;
        }
    }

    public Scope(String contextName, Scope parent) {
        this(contextName, parent, null);
    }

    public Scope(String contextName, Scope parent, Type type) {
        this.contextName = contextName;
        this.variables = new HashMap<>();
        this.parent = parent;
        this.type = type;
        this.memoryLocations = new HashMap<>();
    }

    public String getName() {
        return contextName;
    }

    protected boolean hasParent() {
        return parent != null;
    }

    public Variable getVariable(String ident_) {
        if (variables.containsKey(ident_)) {
            return variables.get(ident_);
        } else if (parent != null) {
            return parent.getVariable(ident_);
        } else {

            return null;
        }
    }

    public Function getCurrentFunction() {
        if (this instanceof Function) {
            return (Function) this;
        } else if (parent != null) {
            return parent.getCurrentFunction();
        } else {
            return null;
        }
    }

    public void addVariable(Variable variable) {
        if (variables.containsKey(variable.getName())) {
            throw new RuntimeException("Variable " + variable.getName() + " already exists in scope " + contextName); // should never happen
        } else {
            variables.put(variable.getName(), variable);
        }
    }

    public Deque<Register> getMemoryLocation(Variable variable) {
        if (memoryLocations.containsKey(variable)) {
            return memoryLocations.get(variable);
        } else if (parent != null) {
            if (parent.getMemoryLocation(variable) != null) {
                return parent.getMemoryLocation(variable);
            }
        }
        Deque<Register> memory = new ArrayDeque<>();
        memoryLocations.put(variable, memory);
        return memory;
    }
    public Register getLastVariableRegister(Variable variable) {
        Deque<Register> registerList = getMemoryLocation(variable);
        if (registerList.isEmpty()) {
            registerList.add(new Register(getCurrentFunction().getNewIdentUseNumber(variable.getName()), variable.getType()));
        }
        return registerList.getLast();
    }

    public Register getNewVariableRegister(Variable variable) {
        Deque<Register> registerList = getMemoryLocation(variable);
        Register register = new Register(getCurrentFunction().getNewIdentUseNumber(variable.getName()), variable.getType());
        registerList.add(register);
        return register;
    }

    public void setLastVariableRegister(Variable variable, Register register) {
        Deque<Register> registerList = getMemoryLocation(variable);
        registerList.add(register);
    }

}
