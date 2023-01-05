package latte.backend.program.global;

import latte.Absyn.ListStmt;
import latte.Absyn.Type;
import latte.Absyn.Void;
import latte.backend.Block;
import latte.backend.programvisitors.StatementVisitor;
import latte.backend.quadruple.Quadruple;
import latte.utils.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Function extends Scope {

    ListStmt statements;

    List<Quadruple> quadruples = new ArrayList<>();
    List<Variable> arguments;
    private boolean isUsed = false;

    public Block getFirstBlock() {
        return firstBlock;
    }

    private Block firstBlock;

    public Function(String name, Type type, List<Variable> arguments, ListStmt statements, Scope scope) {
        super(name, scope, type);
        for (Variable variable : arguments) {
            this.variables.put(variable.getName(), variable);
        }
        this.arguments = arguments;
        this.statements = statements;
        if (name.equals("main")) {
            isUsed = true;
        }
    }
    private void markIfruntimeFunction(String name) {
        if (name.equals("printInt")) {
            Global.getInstance().usePrintInt = 1;
        } else if (name.equals("printString")) {
            Global.getInstance().usePrintString = 1;
        } else if (name.equals("error")) {
            Global.getInstance().useError = 1;
        } else if (name.equals("readInt")) {
            Global.getInstance().useReadInt = 1;
        } else if (name.equals("readString")) {
            Global.getInstance().useReadString = 1;
        }
    }

    public void convertToQuadruples() {
        Block firstBlock = new Block(this.nextBlockName(), this, "entry");
        this.firstBlock = firstBlock;
        for (Variable variable : arguments) {
            firstBlock.setLastRegisterOfVariable(variable.getName(), variable.getLastRegister());
        }
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(firstBlock.getIdentifier())));
        for (latte.Absyn.Stmt stmt : statements) {
            List<Quadruple> quadruples = stmt.accept(new StatementVisitor(), this);
//            this.quadruples.addAll(quadruples);
            firstBlock.addQuadruplesToLastBlock(quadruples);
        }

        if (getType().equals(new Void())) {
            boolean  s = statements.stream().anyMatch(stmt -> stmt instanceof latte.Absyn.VRet);
            if (!s) {
                firstBlock.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.RET())));
            }
        }
        firstBlock.removeDeadCode();

        this.quadruples.addAll(firstBlock.getQuadruplesFromAllBlocks());
    }

    @Override
    public String toString() {
        String body = quadruples.stream().map(Quadruple::toString).collect(Collectors.joining("\n"));
        String argsStr = arguments.stream().map((Variable v) -> (Utils.getLLVMType(v.getType()) + " %" + v.contextName)).collect(Collectors.joining(", "));
        if (isUsed) {
            return MessageFormat.format("\ndefine {0} @{1}({2}) '{' \n{3}\n'}'\n", Utils.getLLVMType(this.getType()), nameFromLabel(this.contextName), argsStr, body);
        } else {
            return "";
        }
    }

    public static String nameFromLabel(String label) {
        if (isLibraryFunction(label)) {
            return label+".user";
        } else {
            return label;
        }
    }
    public static boolean isLibraryFunction(String name) {
        return name.equals("printf") || name.equals("scanf") || name.equals("strcat") || name.equals("strcpy") || name.equals("malloc") || name.equals("strlen") || name.equals("puts") || name.equals("gets") || name.equals("exit") || name.equals("strcmp");
    }

    public void markAsUsed() {
        markIfruntimeFunction(this.contextName);
        this.isUsed = true;
    }
}
