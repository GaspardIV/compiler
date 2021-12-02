package frontend;

import latte_lang.Absyn.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Context {
    private String contextName;
    private Map<String, Type> varDefs;
    private Map<String, FnDef> funDefs;
    private Map<String, ClDefExt> classdefs;

    public Context(String contextName) {
        this.contextName = contextName;
        funDefs = new HashMap<>();
        classdefs = new HashMap<>();
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

    public void initInheristance(Enviroment avaibleClasses) throws SemanticError {
        for (String classIdent:classdefs.keySet()) {
            ClDefExt i = classdefs.get(classIdent);
            i.initInheristance(avaibleClasses);
        }
    }
}
