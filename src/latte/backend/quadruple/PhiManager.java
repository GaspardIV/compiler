package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class PhiManager {
    private static PhiManager instance = null;

    public Set<Map.Entry<Variable, Register>> getTopPhiVariables() {
        Scope scope = scopes.element();
        return phiVariables.get(scope).entrySet();
    }

    public void reset() {
        phiVariables = new HashMap<>();
        scopes = new ArrayDeque<>();
    }

    public static PhiManager getInstance() {
        if (instance == null) {
            instance = new PhiManager();
        }
        return instance;
    }
    private Deque<Scope> scopes;
    private HashMap<Scope, HashMap<Variable, Register>> phiVariables = new HashMap<>();

    private PhiManager() {
        reset();
    }

    public static void resetStatic() {
        getInstance().reset();
    }

    public void pushScope(Scope condScope) {
        scopes.push(condScope);
        phiVariables.put(condScope, new HashMap<>());
    }

    public void popScope() {
        Scope scope = scopes.pop();
        phiVariables.remove(scope);
    }

    public void addPhiRegisterIfNeeded(Variable variable) {
        for (Iterator<Scope> it = scopes.descendingIterator(); it.hasNext(); ) {
            Scope scope = it.next();
            if (!phiVariables.get(scope).containsKey(variable) && !scope.hasVariable(variable)) {
                Register phiRegister = scope.getNewVariableRegister(variable);
                phiVariables.get(scope).put(variable, phiRegister);
            }
        }
    }
}
