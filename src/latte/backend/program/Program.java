package latte.backend.program;

import latte.Absyn.Void;
import latte.Absyn.*;
import latte.Internal.LatteClass;
import latte.backend.program.global.classes.LLVMClass;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Variable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Program {
    private final Global global;

    public void addFunction(String name, Type type, ListArg arguments, ListStmt statements) {
        List<Variable> variablesFromArguments = arguments.stream().map(arg -> new Variable(((Ar) arg).ident_, ((Ar) arg).type_, global)).collect(Collectors.toList());
        Function function = new Function(name, type, variablesFromArguments, statements, global);
        global.add(function);
    }
    public void convertToQuadruples() {
        global.convertToQuadruples();
    }

    public Global getGlobal() {
        return global;
    }

    public void addClass(LLVMClass LLVMClass) {
        global.add(LLVMClass);
    }

    public Program() {
        this.global = Global.reset();
        this.global.add(new Function("printInt", new Void(), Collections.singletonList(new Variable("i", new Int(), null)), new ListStmt(), null));
        this.global.add(new Function("printString", new Void(), Collections.singletonList(new Variable("s", new Str(), null)), new ListStmt(), null));
        this.global.add(new Function("error", new Void(), Collections.emptyList(), new ListStmt(), null));
        this.global.add(new Function("readInt", new Int(), Collections.emptyList(), new ListStmt(), null));
        this.global.add(new Function("readString", new Str(), Collections.emptyList(), new ListStmt(), null));
    }

    @Override
    public String toString() {
        return global.toString();
    }

    public void setClassDefs(Map<String, LatteClass> classDefs) {
        global.setClassDefs(classDefs);
    }
}
