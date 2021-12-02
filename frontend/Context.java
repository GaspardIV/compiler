package frontend;

import latte_lang.Absyn.Fun;
import latte_lang.Absyn.Type;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Type> defs;

    public Context() {
        defs = new HashMap<>();
    }

    public void add(String ident, Fun fun) {
        defs.put(ident, fun);
    }
    //    private Map<String, Fun> variables;
}
