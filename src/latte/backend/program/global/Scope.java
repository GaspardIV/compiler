package latte.backend.program.global;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    String contextName;
    final Map<String, Variable> variables;
    final Map<String, Function> functions;
    final Map<String, Classs> classes;

    public Scope (String contextName, Scope parent) {
        this.contextName = contextName;
        this.variables = new HashMap<String, Variable>();
        this.functions = new HashMap<String, Function>();
        this.classes = new HashMap<String, Classs>();
        // copy parent's variables, functions, and classes
        if (parent != null) {
            this.variables.putAll(parent.variables);
            this.functions.putAll(parent.functions);
            this.classes.putAll(parent.classes);
        }
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
}
