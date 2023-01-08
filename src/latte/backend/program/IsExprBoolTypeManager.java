package latte.backend.program;

import latte.Absyn.Expr;

import java.util.Map;
import java.util.Set;

public class IsExprBoolTypeManager {
    // shared instance
    Set<Expr> boolExprs;
    private static IsExprBoolTypeManager instance = null;

    public static IsExprBoolTypeManager getInstance() {
        if (instance == null) {
            instance = new IsExprBoolTypeManager();
        }
        return instance;
    }
    public static IsExprBoolTypeManager reset() {
        instance = null;
        return getInstance();
    }

    private IsExprBoolTypeManager() {
        this.boolExprs = new java.util.HashSet<>();
    }

    public void add(Expr expr) {
        this.boolExprs.add(expr);
    }

    public boolean isBool(Expr expr) {
        return this.boolExprs.contains(expr);
    }
}
