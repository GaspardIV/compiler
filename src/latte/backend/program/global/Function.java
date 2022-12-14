package latte.backend.program.global;

import latte.Absyn.Type;
import latte.backend.Block;
import latte.utils.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Function extends Scope {
    public void setBlocks(List<Block> statements) {
        this.statements = statements;
    }

    List<Block> statements = new ArrayList<>();
    List<Variable> arguments;

    public Function(String name, Type type, List<Variable> arguments, List<Block> statements, Scope scope) {
        super(name, scope, type);
//        this.type = type;
        for (Variable variable : arguments) {
            this.variables.put(variable.getName(), variable);
        }
        this.arguments = arguments;
        this.statements = statements;
    }

    @Override
    public String toString() {
        String body = statements.stream().map(Block::toString).collect(Collectors.joining("\n"));
        String argsStr = String.join("", arguments.stream().map((Variable v) -> (Utils.toLLVMString(v.getType()) + " %" + v.contextName)).collect(Collectors.toList()));
        return MessageFormat.format("\ndefine {0} @{1}({2}) '{' \n{3}\n'}'\n", Utils.toLLVMString(this.getType()), this.contextName, argsStr, body);
    }
}
