package frontend;

import latte_lang.Absyn.*;
import latte_lang.Absyn.Void;

import java.util.*;

public class Environment {
    //    private
    private Deque<Context> contexts;

    public Environment() {
        contexts = new ArrayDeque<>();
        initBuildIns();
    }

    private void initBuildIns() {
        Context buildIns = new Context("global");
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
        Environment avaibleClasses = new Environment();
        for (Iterator<Context> i = contexts.iterator(); i.hasNext(); ) {
            Context context = i.next();
            avaibleClasses.addContext(context);
            context.initInheristance(avaibleClasses);
        }
    }

    public void addNewContext(String contextName) {
        addContext(new Context(contextName));
    }
    private void addContext(Context context) {
        this.contexts.push(context);
    }

    public void popContext() {
        this.contexts.pop();
    }

    public void addVariable(String ident_, Type type_) {
        contexts.getLast().addVarDef(ident_, type_);
    }

    public boolean actContextContainsVar(String ident_) {
        return contexts.getLast().getVarType(ident_) != null;
    }

    public Type getVarType(String ident_) {
        for (Iterator<Context> i = contexts.descendingIterator(); i.hasNext(); ) {
            Context context = i.next();
            if (context.getVarType(ident_) != null) {
                return context.getVarType(ident_);
            }
        }
        return null;
    }

    public Type getExpectedReturnType() {
        for (Iterator<Context> i = contexts.descendingIterator(); i.hasNext(); ) {
            Context context = i.next();
            if (context.getExpectedReturnType() != null) {
                return context.getExpectedReturnType();
            }
        }
        return null;
    }

    public void setExpectedReturnType(Type type_) {
        contexts.getLast().setExpectedReturnType(type_);
    }

    // merge two contexts in one
//    private void mergeC
}
