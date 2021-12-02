package frontend;

import latte_lang.Absyn.*;
import latte_lang.Absyn.Void;

import java.util.ArrayDeque;
import java.util.Deque;

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
        buildIns.add("printInt", new Fun(new Void(), args));
        args = new ListType();
        args.add(new Str());
        buildIns.add("printString", new Fun(new Void(), args));
        args = new ListType();
        buildIns.add("readString", new Fun(new Str(), args));
        args = new ListType();
        buildIns.add("readInt", new Fun(new Int(), args));
        args = new ListType();
        buildIns.add("error", new Fun(new Void(), args));
        contexts.add(buildIns);
    }
}
