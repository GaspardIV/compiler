package frontend;

import latte_lang.Absyn.*;
import latte_lang.Absyn.Void;

import java.util.*;

public class Enviroment {
    //    private
    private Deque<Context> contexts;

    public Enviroment() {
        contexts = new ArrayDeque<>();
        initBuildIns();
    }

    private void initBuildIns() {
        Context buildIns = new Context();
        ListArg args = new ListArg();
        args.add(new Ar(new Int(), "i"));
        buildIns.addFunctionDef("printInt", new FnDef(new Void(), "printInt", args, null));
        args = new ListArg();
        args.add(new Ar(new Str(), "s"));
        buildIns.addFunctionDef("printString", new FnDef(new Void(), "printString", args, null));
        args = new ListArg();
        buildIns.addFunctionDef("readString", new FnDef(new Str(), "readString", args, null));
        args = new ListArg();
        buildIns.addFunctionDef("readInt", new FnDef(new Int(), "readInt", args, null));
        args = new ListArg();
        buildIns.addFunctionDef("error", new FnDef(new Void(), "error", args, null));
        contexts.add(buildIns);
    }

    public FnDef getFunction(String ident_) {
        for (Iterator<Context> i = contexts.descendingIterator(); i.hasNext(); ) {
            Context context = i.next();
            if (context.getFunctionDef(ident_) != null) {
                return context.getFunctionDef(ident_);
            }
        }
        return null;
    }

    public void addFunction(String ident_, FnDef p) {
        contexts.getLast().addFunctionDef(ident_, p);
    }

    public ClDefExt getClassDef(String ident_) {
        for (Iterator<Context> i = contexts.descendingIterator(); i.hasNext(); ) {
            Context context = i.next();
            if (context.getClassDef(ident_) != null) {
                return context.getClassDef(ident_);
            }
        }
        return null;
    }

    public void addClassDef(String ident_, ClDefExt p) {
        contexts.getLast().addClassDef(ident_, p);
    }

    public void initInheristance() throws SemanticError {
        Enviroment avaibleClasses = new Enviroment();
        for (Iterator<Context> i = contexts.iterator(); i.hasNext(); ) {
            Context context = i.next();
            avaibleClasses.addContext(context);
            context.initInheristance(avaibleClasses);
        }
    }

    private void addContext(Context context) {
        this.contexts.push(context);
    }
}
