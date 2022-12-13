package latte.backend.program.global;

import latte.frontend.environment.Environment;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    String contextName;
    final Map<String, Variable> variables;
    final Map<String, Function> functions;
    final Map<String, Classs> classes;

    final Map<String, Integer> registers;

//    Environment environment;

    public Scope (String contextName, Scope parent) {
        this.contextName = contextName;
        this.variables = new HashMap<String, Variable>();
        this.functions = new HashMap<String, Function>();
        this.classes = new HashMap<String, Classs>();
        this.registers = new HashMap<String, Integer>();
        // copy parent's variables, functions, and classes
        if (parent != null) {
            this.variables.putAll(parent.variables);
            this.functions.putAll(parent.functions);
            this.classes.putAll(parent.classes);
//            this.environment = parent.environment;
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

    public String getRegisterNumber(String ident_) {
        if (registers.containsKey(ident_)) {
            registers.put(ident_, registers.get(ident_) + 1);
            return registers.get(ident_).toString();
        } else {
            registers.put(ident_, 1);
            return "";
        }
    }
}
