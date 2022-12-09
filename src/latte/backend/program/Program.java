package latte.backend.program;

import latte.Absyn.*;
import latte.backend.Block;
import latte.backend.program.global.Classs;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    public <R, A> R accept(latte.backend.program.Program.Visitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    public void addFunction(String name, Type type, ListArg arguments, ListStmt statements) {
        List<Variable> variablesFromArguments = arguments.stream().map(arg -> new Variable(((Ar)arg).ident_, ((Ar)arg).type_, global)).collect(Collectors.toList());
        List<Block> blocksFromStatements = new ArrayList<>();
        for(latte.Absyn.Stmt stmt : statements) {
            if (stmt instanceof Cond) {
//                blocksFromStatements.add(new Block(((ifStmt)stmt).expr_, ((ifStmt)stmt).stmt_));
            }
//            blocksFromStatements.add(new Block(stmt));
        }
//        statements.stream().map(stmt -> new Block(stmt, global)).collect(Collectors.toList());
//       // todo merge statements to blocks (create graph of blocks)
        global.add(new Function(name, type, variablesFromArguments, blocksFromStatements, global));
    }

    public Global getGlobal() {
        return global;
    }

    public void addClass(Classs classs) {
        global.add(classs);
    }


    public interface Visitor<R, A> {
        public R visit(Program p, A arg);

    }

    public Program() {
        this.global = new Global("global", null);
    }

    @Override
    public String toString() {
        return global.toString();
    }

    private Global global;
}
