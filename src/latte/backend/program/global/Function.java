package latte.backend.program.global;

import latte.Absyn.Array;
import latte.Absyn.Type;
import latte.backend.Block;
import latte.utils.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Function extends Scope {
    List<Block> statements = new ArrayList<>();
    List<Variable> arguments;
    Type type;

    public Function(String name, Type type, List<Variable> arguments, List<Block> statements, Scope scope) {
        super(name, scope);
        this.type = type;
        for (Variable variable : arguments) {
            this.variables.put(variable.getName(), variable);
        }
        this.arguments = arguments;
        this.statements = statements;
    }

    @Override
    public String toString() {
        String body = statements.toString();
        String argsStr = String.join("", arguments.stream().map((Variable v) -> (Utils.toLLVMString(v.type) + " %" + v.contextName)).collect(Collectors.toList()));
        return MessageFormat.format("\ndefine {0} @{1}({2}) '{' \n{3}\n'}'\n", Utils.toLLVMString(type), this.contextName, argsStr, body);
    }
}
