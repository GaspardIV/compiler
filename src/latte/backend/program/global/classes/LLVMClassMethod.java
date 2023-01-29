package latte.backend.program.global.classes;

import latte.Absyn.*;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.utils.Utils;

import java.util.List;

public class LLVMClassMethod extends Function {

    public LLVMClassMethod(String name, Type type, List<Variable> arguments, ListStmt statements, Scope scope) {
        super(name, type, arguments, statements, scope);
        markAsUsed();
    }

    public String getLLVMTypeAndName() {
        return "void (...)* bitcast (" + getLLVMType() + "* @" + getName() + " to void (...)*)";

    }

    public String getLLVMType() {
        return getLLVMReturnType() + " (" +
                getLLVMArgumentsType() +
                ")";
    }

    private String getLLVMReturnType() {
        return Utils.getLLVMType(getType());
    }

    private String getLLVMArgumentsType() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getArguments().size(); i++) {
            sb.append(Utils.getLLVMType(getArguments().get(i).getType()));
            if (i != getArguments().size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
