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

    private final Type type;

    public Type getType() {
        if (type == null) {
            return parent.getType();
        } else {
            return type;
        }
    }

    public Scope (String contextName, Scope parent) {
        this(contextName, parent, null);
    }
    public Scope (String contextName, Scope parent, Type type) {
        this.contextName = contextName;
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.classes = new HashMap<>();
        this.parent = parent;
        this.type = type;
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
}
