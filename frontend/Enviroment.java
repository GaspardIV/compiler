package frontend;

import latte_lang.Absyn.*;
import latte_lang.Absyn.Void;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Enviroment {
//    private
    private Deque<Context> contexts;

    public Enviroment() {
        contexts = new ArrayDeque<>();
        initBuildIns();
    }

    private void initBuildIns() {
        Context buildIns = new Context();
        ListType args = new ListType();
        args.add(new Int());
        buildIns.addFunction("printInt", new Fun(new Void(), args));
        args = new ListType();
        args.add(new Str());
        buildIns.addFunction("printString", new Fun(new Void(), args));
        args = new ListType();
        buildIns.addFunction("readString", new Fun(new Str(), args));
        args = new ListType();
        buildIns.addFunction("readInt", new Fun(new Int(), args));
        args = new ListType();
        buildIns.addFunction("error", new Fun(new Void(), args));
        contexts.add(buildIns);
    }

    public Fun getFunction(String ident_) {
        for (Iterator<Context> i = contexts.descendingIterator(); i.hasNext();) {
            Context context = i.next();
            if (context.getFunction(ident_) != null){
                return context.getFunction(ident_);
            }
        }
        return null;
    }

    public void addFunction(String ident_, Fun p) {
        contexts.getLast().addFunction(ident_, p);
    }
}
