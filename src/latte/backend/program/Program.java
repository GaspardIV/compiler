package latte.backend.program;

import latte.Absyn.*;
import latte.Absyn.Void;
import latte.backend.Block;
import latte.backend.program.global.Classs;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    private final Global global;

    public void addFunction(String name, Type type, ListArg arguments, ListStmt statements) {
        List<Variable> variablesFromArguments = arguments.stream().map(arg -> new Variable(((Ar) arg).ident_, ((Ar) arg).type_, global)).collect(Collectors.toList());
        List<Block> blocksFromStatements = new ArrayList<>();
        Function function = new Function(name, type, variablesFromArguments, blocksFromStatements, global);
        global.add(function);
        ;
        Block block = new Block("entry", function);
        for (latte.Absyn.Stmt stmt : statements) {
            if (shouldCreateNewBlock(stmt) && !block.isEmpty()) {
                // moge tez dla kazdego statementa najpierw zobic blok po prrostu xd a pozniej kazde dwa obok siebie probuje mergowac zachlannie
//                w kazdym bloku mozna trzymac tez pierwotnie zmienne nie w postai ssa , ale ppzniej przeksztaltcic je do SSA podczas mergownaia
                blocksFromStatements.add(block);
                block = new Block("block" + blocksFromStatements.size(), block);
            }
            block.addStatement(stmt);
        }
        blocksFromStatements.add(block);
        function.setBlocks(blocksFromStatements);
//        global.add(new Function(name, type, variablesFromArguments, blocksFromStatements, global));

//            if (stmt instanceof Cond) {
//                blocksFromStatements.add(new Block(((ifStmt)stmt).expr_, ((ifStmt)stmt).stmt_));
//            }
//            blocksFromStatements.add(new Block(stmt));
    }

    private boolean shouldCreateNewBlock(Stmt stmt) {
        return stmt instanceof Cond || stmt instanceof While || stmt instanceof For || stmt instanceof SExp || stmt instanceof Ret || stmt instanceof VRet || stmt instanceof CondElse;
    }
//        statements.stream().map(stmt -> new Block(stmt, global)).collect(Collectors.toList());

    public Global getGlobal() {
        return global;
    }

    public void addClass(Classs classs) {
        global.add(classs);
    }

    public Program() {
        this.global = new Global("global", null);
        this.global.add(new Function("printInt", new Void(), Arrays.asList(new Variable("i", new Int(), null)), Collections.emptyList(), null));
        this.global.add(new Function("printString", new Void(), Arrays.asList(new Variable("s", new Str(), null)), Collections.emptyList(), null));
        this.global.add(new Function("error", new Void(), Collections.emptyList(), Collections.emptyList(), null));
        this.global.add(new Function("readInt", new Int(), Collections.emptyList(), Collections.emptyList(), null));
        this.global.add(new Function("readString", new Str(), Collections.emptyList(), Collections.emptyList(), null));
    }

    @Override
    public String toString() {
        return global.toString();
    }
}
