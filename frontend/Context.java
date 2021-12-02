package frontend;

import latte_lang.Absyn.*;
import latte_lang.Absyn.Class;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, FnDef> funDefs;
    private Map<String, ClDefExt> classdefs;

    public Context() {
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
}
