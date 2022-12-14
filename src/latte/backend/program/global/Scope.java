package latte.backend.program.global;

import latte.Absyn.Type;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private final Scope parent;
    String contextName;
    final Map<String, Variable> variables;
    final Map<String, Function> functions;
    final Map<String, Classs> classes;

    final Map<String, Integer> registers;

    private final Type type;

    public Type getType() {
        if (type == null) {
            return parent.getType();
        } else {
            return type;
        }
    }

//    Environment environment;

    public Scope (String contextName, Scope parent) {
        this.contextName = contextName;
        this.variables = new HashMap<String, Variable>();
        this.functions = new HashMap<String, Function>();
        this.classes = new HashMap<String, Classs>();
        this.registers = new HashMap<String, Integer>();
        this.type = null;
        this.parent = parent;
        // copy parent's variables, functions, and classes
//        if (parent != null) {
//            this.variables.putAll(parent.variables);
//            this.functions.putAll(parent.functions);
//            this.classes.putAll(parent.classes);
//            this.type = parent.type;
//        }
    }
    public Scope (String contextName, Scope parent, Type type) {
        this.contextName = contextName;
        this.variables = new HashMap<String, Variable>();
        this.functions = new HashMap<String, Function>();
        this.classes = new HashMap<String, Classs>();
        this.registers = new HashMap<String, Integer>();
        // copy parent's variables, functions, and classes
        this.parent = parent;
//        if (parent != null) {
//            this.variables.putAll(parent.variables);
//            this.functions.putAll(parent.functions);
//            this.classes.putAll(parent.classes);
            this.type = type;
//        }
    }

    public void add(Function function) {
        functions.put(function.getName(), function);
    }

    public void add(Classs classs) {
        classes.put(classs.getName(), classs);
    }

    public void add(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    public String getName() {
        return contextName;
    }

    public String getRegisterNumber(String ident_) {
        if (registers.containsKey(ident_)) {
            registers.put(ident_, registers.get(ident_) + 1);
            return registers.get(ident_).toString();
        } else {
            registers.put(ident_, 1);
            return "";
        }
    }

    public Function getFunction(String ident_) {
        if (functions.containsKey(ident_)) {
            return functions.get(ident_);
        } else if (parent != null) {
            return parent.getFunction(ident_);
        } else {
            return null;
        }
    }

    protected boolean hasParent() {
        return parent != null;
    }
}
