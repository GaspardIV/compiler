package latte.frontend.environment;

import latte.Absyn.ClDefExt;
import latte.Absyn.FnDef;
import latte.Absyn.Type;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final String contextName;
    private final Map<String, Type> varDefs;
    private final Map<String, FnDef> funDefs;
    private final Map<String, ClDefExt> classdefs;
    private Type expectedReturnType;
    private Boolean wasReturn;

    public Context(String contextName) {
        this.contextName = contextName;
        funDefs = new HashMap<>();
        classdefs = new HashMap<>();
        varDefs = new HashMap<>();
        wasReturn = false;
    }

    public void addFunctionDef(String ident, FnDef fun) {
        funDefs.put(ident, fun);
    }

    public FnDef getFunctionDef(String ident_) {
        return funDefs.get(ident_);
    }

    public ClDefExt getClassDef(String ident_) {
        return classdefs.get(ident_);
    }

    public void addClassDef(String ident_, ClDefExt p) {
        classdefs.put(ident_, p);
    }
    public void addVarDef(String ident_, Type t) {
        varDefs.put(ident_, t);
    }
    public Type getVarType(String ident_) {
        return varDefs.get(ident_);
    }

    public void initInheristance(Environment avaibleClasses) {
        for (String classIdent:classdefs.keySet()) {
            ClDefExt i = classdefs.get(classIdent);
            i.initInheristance(avaibleClasses);
        }
    }

    public void setExpectedReturnType(Type type_) {
        this.expectedReturnType = type_;
    }

    public Type getExpectedReturnType() {
        return this.expectedReturnType;
    }

    public void setWasReturn(boolean wasReturn) {
        this.wasReturn = wasReturn;
    }

    public Boolean wasReturn() {
        return wasReturn;
    }
}
