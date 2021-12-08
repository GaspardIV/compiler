package frontend;

import latte_lang.Absyn.*;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private String contextName;
    private Map<String, Type> varDefs;
    private Map<String, FnDef> funDefs;
    private Map<String, ClDefExt> classdefs;
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

    public void initInheristance(Environment avaibleClasses) throws SemanticError {
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
