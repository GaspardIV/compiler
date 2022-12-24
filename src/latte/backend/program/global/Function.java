package latte.backend.program.global;

import latte.Absyn.ListStmt;
import latte.Absyn.Type;
import latte.backend.Block;
import latte.backend.programvisitors.StatementVisitor;
import latte.backend.quadruple.Quadruple;
import latte.utils.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Function extends Scope {

    ListStmt statements;

    List<Quadruple> quadruples = new ArrayList<>();
    List<Variable> arguments;

    public Function(String name, Type type, List<Variable> arguments, ListStmt statements, Scope scope) {
        super(name, scope, type);
        for (Variable variable : arguments) {
            this.variables.put(variable.getName(), variable);
        }
        this.arguments = arguments;
        this.statements = statements;
    }

    public void convertToQuadruples() {
        Block firstBlock = new Block(this.nextBlockName(), this);
        for (latte.Absyn.Stmt stmt : statements) {
            List<Quadruple> quadruples = stmt.accept(new StatementVisitor(), firstBlock);
//            this.quadruples.addAll(quadruples);
            firstBlock.addQuadruplesToLastBlock(quadruples);
        }

        this.quadruples.addAll(firstBlock.getQuadruplesFromAllBlocks());
    }

    @Override
    public String toString() {
        String body = quadruples.stream().map(Quadruple::toString).collect(Collectors.joining("\n"));
        String argsStr = arguments.stream().map((Variable v) -> (Utils.getLLVMType(v.getType()) + " %" + v.contextName)).collect(Collectors.joining(""));
        return MessageFormat.format("\ndefine {0} @{1}({2}) '{' \n{3}\n'}'\n", Utils.getLLVMType(this.getType()), this.contextName, argsStr, body);
    }
}
