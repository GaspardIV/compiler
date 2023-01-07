package latte.backend.program.global;

import latte.Absyn.ListStmt;
import latte.Absyn.Type;
import latte.Absyn.Void;
import latte.backend.quadruple.Block;
import latte.backend.programvisitors.StatementVisitor;
import latte.backend.quadruple.Quadruple;
import latte.utils.Utils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Function extends Scope {

    ListStmt statements;

    final Map<String, Integer> identsLastUseNumber;
    public String getNewIdentUseNumber(String ident_) {
        if (identsLastUseNumber.containsKey(ident_)) {
            identsLastUseNumber.put(ident_, identsLastUseNumber.get(ident_) + 1);
        } else {
            identsLastUseNumber.put(ident_, 0);
        }

        if (identsLastUseNumber.get(ident_) == 0) {
            return ident_;
        } else {
            return ident_ + "." + identsLastUseNumber.get(ident_);
        }
    }


    List<Quadruple> quadruples = new ArrayList<>();
    List<Variable> arguments;
    private boolean isUsed = false;

    public Block getLastBlock() {
        return firstBlock.getLastBlock();
    }

    private Block firstBlock;

    public Function(String name, Type type, List<Variable> arguments, ListStmt statements, Scope scope) {
        super(name, scope, type);
        for (Variable variable : arguments) {
            this.addVariable(variable);
        }
        this.arguments = arguments;
        this.statements = statements;
        identsLastUseNumber = new HashMap<>();
        if (name.equals("main")) {
            isUsed = true;
        }
    }
    private void markIfRuntimeFunction(String name) {
        switch (name) {
            case "printInt":
                Global.getInstance().usePrintInt = 1;
                break;
            case "printString":
                Global.getInstance().usePrintString = 1;
                break;
            case "error":
                Global.getInstance().useError = 1;
                break;
            case "readInt":
                Global.getInstance().useReadInt = 1;
                break;
            case "readString":
                Global.getInstance().useReadString = 1;
                break;
        }
    }

    public void convertToQuadruples() {
        Block firstBlock = new Block(this.nextBlockName(), this, "entry");
        this.firstBlock = firstBlock;
        for (Variable variable : arguments) {
            firstBlock.setLastRegisterOfVariable(variable.getName(), this.getLastVariableRegister(variable));
        }
        quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(firstBlock.getIdentifier())));
        for (latte.Absyn.Stmt stmt : statements) {
            stmt.accept(new StatementVisitor(), getLastBlock());
//            firstBlock.addQuadruplesToLastBlock(quadruples); // todo mozna to robic w visitorze!
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
        //remove empty lines

        String body = quadruples.stream().map(Quadruple::toString).filter(s -> !s.isEmpty()).collect(Collectors.joining("\n"));
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
        markIfRuntimeFunction(this.contextName);
        this.isUsed = true;
    }

    public String nextBlockName() {
        return getNewIdentUseNumber(contextName);
    }
}
