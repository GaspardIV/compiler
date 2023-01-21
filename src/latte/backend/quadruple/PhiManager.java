package latte.backend.quadruple;

import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.*;

public class PhiManager {
    private static PhiManager instance = null;

    public Collection<Map.Entry<Variable, Register>> getTopPhiVariables() {
        Scope scope = scopes.element();
        List<Map.Entry<Variable, Register>> res = new ArrayList<>();
        for (Map.Entry<Variable, Register> entry :  phiVariables.get(scope).entrySet()) {
            if (phiRegisterNeeded.get(scope).contains(entry.getKey())) {
                res.add(entry);
            } else {
                entry.getValue().setOverride(registersBeforePhi.get(scope).get(entry.getKey()));
            }
        }

        // sorted for determinism in tests
        res.sort(Comparator.comparing(o -> o.getKey().getName()));
        return res;
    }

    public void reset() {
        phiVariables = new HashMap<>();
        phiRegisterNeeded = new HashMap<>();
        registersBeforePhi = new HashMap<>();
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
    private HashMap<Scope, HashMap<Variable, Register>> registersBeforePhi = new HashMap<>();
    private HashMap<Scope, Set<Variable>> phiRegisterNeeded = new HashMap<>();

    private PhiManager() {
        reset();
    }

    public static void resetStatic() {
        getInstance().reset();
    }

    public void pushScope(Scope condScope) {
        scopes.push(condScope);
        phiVariables.put(condScope, new HashMap<>());
        registersBeforePhi.put(condScope, new HashMap<>());
        phiRegisterNeeded.put(condScope, new HashSet<>());
    }

    public void popScope() {
        Scope scope = scopes.pop();
        phiVariables.remove(scope);
    }

    public void addPhiRegisterIfNeeded(Variable variable) {
        for (Iterator<Scope> it = scopes.descendingIterator(); it.hasNext(); ) {
            Scope scope = it.next();
            if (!phiVariables.get(scope).containsKey(variable) && !scope.hasVariable(variable)) {
                registersBeforePhi.get(scope).put(variable, scope.getLastVariableRegister(variable));
                Register phiRegister = scope.getNewVariableRegister(variable);
                phiVariables.get(scope).put(variable, phiRegister);
            }
        }
    }

    public void markVariableAsRedefined(Scope scope, Variable variable) {
        if (phiVariables.get(scope) != null && phiVariables.get(scope).containsKey(variable)) {
            phiRegisterNeeded.get(scope).add(variable);
        }
    }
}
