package frontend;

import latte_lang.Absyn.Class;
import latte_lang.Absyn.Fun;
import latte_lang.Absyn.Type;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Fun> funDefs;
    private Map<String, Class> classdefs;

    public Context() {
        funDefs = new HashMap<>();
        classdefs = new HashMap<>();
    }

    public void addFunction(String ident, Fun fun) {
        funDefs.put(ident, fun);
    }

    public Fun getFunction(String ident_) {
        return funDefs.get(ident_);
    }
    //    private Map<String, Fun> variables;
}
