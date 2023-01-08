package latte.frontend.environment;

import latte.Absyn.Class;
import latte.Absyn.Void;
import latte.Absyn.*;
import latte.Internal.LatteClass;
import latte.Internal.Null;
import latte.errors.SemanticError;

import java.util.*;

public class Environment {
    private final Deque<Context> contexts;

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

        buildInArrayFields.put("length", new Int());
    }

    public boolean isFunctionGlobal(String ident) {
        return contexts.getFirst().getFunctionDef(ident) != null;
    }

    public boolean isFunctionGlobalErrorFunction(String ident) {
        return ident.equals("error");
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

    public LatteClass getClassDef(String ident_) {
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

    public void initInheristance() {
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
        this.contexts.addLast(context);
    }

    public void popContext() {
        this.contexts.removeLast();
    }

    public void addVariableWithErrorCheck(String ident_, Type type_, int line_num) {
        if (this.currentContextContainsVar(ident_)) {
            throw new SemanticError.VariableAlreadyDeclaredInCurrentContext(line_num, ident_);
        }
        if (new Void().equals(type_)) {
            throw new SemanticError.VariableWithVoidType(line_num, ident_);
        }
        if (type_ instanceof latte.Absyn.Class) { // todo dac .equalsT(new Class(null))
            latte.Absyn.Class typeClass = (latte.Absyn.Class) type_;
            if (this.getClassDef(typeClass.ident_) == null) {
                throw new SemanticError.ClassNotDeclared(line_num, typeClass.ident_);
            }
        }
        if (type_ instanceof latte.Absyn.Array) {
            latte.Absyn.Array typeArray = (latte.Absyn.Array) type_;
            this.checkArrayType(typeArray.type_, line_num);
        }
        addVariable(ident_, type_);
    }

    private void addVariable(String ident_, Type type_) {
        contexts.getLast().addVarDef(ident_, type_);
    }

    public boolean currentContextContainsVar(String ident_) {
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

    private static final HashMap<String, Type> buildInArrayFields = new HashMap<>();

    public Map<String, Type> buildInArrayFields() {
        return buildInArrayFields;
    }

    public void setWasReturn(boolean wasReturn) {
        contexts.getLast().setWasReturn(wasReturn);
    }

    public Boolean wasReturn() {
        return contexts.getLast().wasReturn();
    }

    public boolean areTypesEqualRegardingInheritance(Type AMightExtendsB, Type B) {
        if (AMightExtendsB instanceof Null && B instanceof Array) {
            return true;
        }
        if (B instanceof Null && AMightExtendsB instanceof Array) {
            return true;
        }
        if (AMightExtendsB instanceof Null && B instanceof Class) {
            return true;
        }
        if (B instanceof Null && AMightExtendsB instanceof Class) {
            return true;
        }
        if (AMightExtendsB instanceof Class && B instanceof Class) {
            LatteClass classA = getClassDef(((Class) AMightExtendsB).ident_);
            LatteClass classB = getClassDef(((Class) B).ident_);
            if (classA == null || classB == null) {
                return false;
            }

            if (AMightExtendsB.equals(B)) {
                return true;
            }

            return classA.doesExtends(classB);
        }
        return AMightExtendsB.equals(B);
    }

    public void checkArrayType(Type type, int line_num) {
        if (type instanceof Void) {
            throw new SemanticError.ArrayOfVoid(line_num);
        }
        if (type instanceof Class) {
            Class classType = (Class) type;
            if (this.getClassDef(classType.ident_) == null) {
                throw new SemanticError.ClassNotDeclared(line_num, classType.ident_);
            }
        }
        if (type instanceof latte.Absyn.Array) {
            latte.Absyn.Array typeArray = (latte.Absyn.Array) type;
            checkArrayType(typeArray.type_, line_num);
        }
    }
}
