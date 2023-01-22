package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.Absyn.Void;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.*;
import latte.backend.quadruple.Block;
import latte.errors.SemanticError;
import latte.utils.Utils;

import java.util.*;

public class RegisterExprVisitor implements Expr.Visitor<List<Quadruple>, Block> {
    protected static final String TMP = "tmp.";

    public List<Quadruple> generateExprCode(Expr expr, Block block) {
        if (expr instanceof EOr || expr instanceof EAnd) {
            Block resultBlock = new Block("result", block.getScope());

            Scope scope = resultBlock.getScope();
            Function function = scope.getCurrentFunction();

            Block btrue = new Block(function.nextBlockName(), new Scope("expr.true", scope), "expr.true");
            Block bfalse = new Block(function.nextBlockName(), new Scope("expr.false", scope), "expr.false");
            Block bend = new Block(function.nextBlockName(), scope, "expr.end");

            List<Quadruple> quadruples = new ArrayList<>(expr.accept(new JumpingCodeGenerator(btrue, bfalse), resultBlock));
            resultBlock.addQuadruplesToLastBlock(quadruples);
            resultBlock.addLastBlock(btrue);

            btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(btrue))));
            btrue.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
            btrue.addLastBlock(bfalse);

            bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bfalse))));
            bfalse.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.GOTO(bend))));
            bfalse.addLastBlock(bend);

            bend.addQuadruplesToLastBlock(Collections.singletonList(new Quadruple(null, new Quadruple.LLVMOperation.LABEL(bend))));
            Quadruple phi = new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.BOOL_PHI(btrue, bfalse));
            bend.addQuadruplesToLastBlock(Collections.singletonList(phi));
            return resultBlock.getQuadruplesFromAllBlocks();
        } else {
            return expr.accept(this, block);
        }
    }

    @Override
    public List<Quadruple> visit(ENewArray p, Block block) {
        // todo
        return null;
    }

//    @Override
//    public List<Quadruple> visit(EArrayElem p, Block block) {
//        // todo
//        return null;
//    }

    @Override
    public List<Quadruple> visit(EArrayElemR p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENullArr p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENull p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENil p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(ENew p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EMethod p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EField p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EVar p, Block block) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        Variable variable = block.getVariable(p.ident_);
        Scope scope = block.getScope();
        PhiManager.getInstance().addPhiRegisterIfNeeded(variable);
        Register last = scope.getLastVariableRegister(variable);
        last.setVariable(variable);
        quadruples.add(new Quadruple(last));
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(ELitInt p, Block block) {
        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int(), new ConstValue(p.integer_))));
    }

    @Override
    public List<Quadruple> visit(ELitTrue p, Block block) {

        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(true))));
    }

    @Override
    public List<Quadruple> visit(ELitFalse p, Block block) {
        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(false))));
    }

    @Override
    public List<Quadruple> visit(EApp p, Block block) {
        Function function = Global.getInstance().getFunction(p.ident_);
        function.markAsUsed();
        List<Quadruple> quadruples = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        for (int i = 0; i < p.listexpr_.size(); i++) {
            List<Quadruple> exprQuadruples = new RegisterExprVisitor().generateExprCode(p.listexpr_.get(i), block);
            quadruples.addAll(exprQuadruples);
            registers.add(exprQuadruples.get(exprQuadruples.size() - 1).getRegister());
        }
        Quadruple.LLVMOperation.CALL quadruple = new Quadruple.LLVMOperation.CALL(function.getType(), function.getName(), registers);
        quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), function.getType()), quadruple));
        quadruples.addAll(handleCallErrorFunction(block, function));
        return quadruples;
    }

    private ArrayList<Quadruple> handleCallErrorFunction(Block block, Function function) {
        ArrayList<Quadruple> quadruples = new ArrayList<>();
        if (function.getName().equals("error")) {
            Global.getInstance().usePrintString = true;
            Type functionType = block.getScope().getCurrentFunction().getType();
            if (functionType.equals(new Void())) {
                quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(null)));
            } else if (functionType.equals(new Int())) {
                Register register = new Register(block.getRegisterNumber(TMP), functionType, new ConstValue(0));
                quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(register)));
            } else if (functionType.equals(new Bool())) {
                Register register = new Register(block.getRegisterNumber(TMP), functionType, new ConstValue(false));
                quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(register)));
            } else {
                Quadruple quadruple1 = new Quadruple(new Register(block.getRegisterNumber(TMP), new Str()), new Quadruple.LLVMOperation.GETELEMENTPTRSTR(15, "@._runtime_error", 0, 0));
                quadruples.add(quadruple1);
                quadruples.add(new Quadruple(null, new Quadruple.LLVMOperation.RET(quadruple1.getRegister())));
            }
        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EString p, Block block) {
        String regIdent = Global.getInstance().addStringGlobalRegister(p.string_);
        String escaped = Utils.getEscapedString(p.string_);
        int escapedLength = Utils.getLLVMEscapedStringLength(escaped);
        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Str()), new Quadruple.LLVMOperation.GETELEMENTPTRSTR(escapedLength, regIdent, 0, 0)));
    }

    @Override
    public List<Quadruple> visit(Neg p, Block block) {
        List<Quadruple> right = p.expr_.accept(this, block);
        List<Quadruple> quadruples = new ArrayList<>(right);
        if (right.get(right.size() - 1).getRegister().isConst()) {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int(), new ConstValue(-right.get(right.size() - 1).getRegister().getConstValue().getInt()))));
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int()), new Quadruple.LLVMOperation.NEG(quadruples.get(quadruples.size() - 1).getRegister())));
        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(Not p, Block block) {
        List<Quadruple> right = p.expr_.accept(this, block);
        List<Quadruple> quadruples = new ArrayList<>(right);
        if (right.get(right.size() - 1).getRegister().isConst()) {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(!right.get(right.size() - 1).getRegister().getConstValue().getBool()))));
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.NOT(quadruples.get(quadruples.size() - 1).getRegister())));
        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EMul p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, block);
        List<Quadruple> right = p.expr_2.accept(this, block);
        quadruples.addAll(left);
        quadruples.addAll(right);
        Register leftRegister = left.get(left.size() - 1).getRegister();
        Register rightRegister = right.get(right.size() - 1).getRegister();
        if (rightRegister.isConst() && rightRegister.getConstValue().getInt() == 0) {
            if (p.mulop_ instanceof Div) {
                throw new SemanticError(p.line_num, "Division by zero");
            } else if (p.mulop_ instanceof Mod) {
                throw new SemanticError(p.line_num, "Modulo by zero");
            }
        }
        if (leftRegister.isConst() && rightRegister.isConst()) {
            if (p.mulop_ instanceof Times) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int(), new ConstValue(leftRegister.getConstValue().getInt() * rightRegister.getConstValue().getInt()))));
            } else if (p.mulop_ instanceof Div) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int(), new ConstValue(leftRegister.getConstValue().getInt() / rightRegister.getConstValue().getInt()))));
            } else if (p.mulop_ instanceof Mod) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int(), new ConstValue(leftRegister.getConstValue().getInt() % rightRegister.getConstValue().getInt()))));
            }
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int()), new Quadruple.LLVMOperation.MUL(p.mulop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAdd p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, block);
        List<Quadruple> right = p.expr_2.accept(this, block);
        quadruples.addAll(left);
        quadruples.addAll(right);
        Register leftRegister = left.get(left.size() - 1).getRegister();
        Register rightRegister = right.get(right.size() - 1).getRegister();
        if (leftRegister.isConst() && rightRegister.isConst()) {
            if (p.addop_ instanceof Plus) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), leftRegister.type, new ConstValue(leftRegister.getConstValue().getInt() + rightRegister.getConstValue().getInt()))));
            } else if (p.addop_ instanceof Minus) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), leftRegister.type, new ConstValue(leftRegister.getConstValue().getInt() - rightRegister.getConstValue().getInt()))));
            }
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), leftRegister.type), new Quadruple.LLVMOperation.ADD(p.addop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        }
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(ERel p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, block);
        List<Quadruple> right = p.expr_2.accept(this, block);
        quadruples.addAll(left);
        quadruples.addAll(right);
        Register leftRegister = left.get(left.size() - 1).getRegister();
        Register rightRegister = right.get(right.size() - 1).getRegister();
        if (leftRegister.isConst() && rightRegister.isConst()) {
            if (p.relop_ instanceof LTH) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getInt() < rightRegister.getConstValue().getInt()))));
            } else if (p.relop_ instanceof LE) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getInt() <= rightRegister.getConstValue().getInt()))));
            } else if (p.relop_ instanceof GTH) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getInt() > rightRegister.getConstValue().getInt()))));
            } else if (p.relop_ instanceof GE) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getInt() >= rightRegister.getConstValue().getInt()))));
            } else if (p.relop_ instanceof EQU) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().equals(rightRegister.getConstValue())))));
            } else if (p.relop_ instanceof NE) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(!leftRegister.getConstValue().equals(rightRegister.getConstValue())))));
            }
        } else {
            if (leftRegister.type instanceof Str) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int()), new Quadruple.LLVMOperation.CMP(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
                if (p.relop_ instanceof EQU) {
                    quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, quadruples.get(quadruples.size()-1).getRegister(), new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(0)))));
                } else if (p.relop_ instanceof NE) {
                    quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_,  quadruples.get(quadruples.size()-1).getRegister(), new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(0)))));
                }
            } else {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
            }
        }
        return quadruples;
    }

    @Override
    public List<Quadruple> visit(EAnd p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, block);
        List<Quadruple> right = p.expr_2.accept(this, block);
        quadruples.addAll(left);
        quadruples.addAll(right);
        Register leftRegister = left.get(left.size() - 1).getRegister();
        Register rightRegister = right.get(right.size() - 1).getRegister();
        if (leftRegister.isConst() && rightRegister.isConst()) {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getBool() && rightRegister.getConstValue().getBool()))));
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.AND(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        }
        return quadruples;

    }

    @Override
    public List<Quadruple> visit(EOr p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        List<Quadruple> left = p.expr_1.accept(this, block);
        List<Quadruple> right = p.expr_2.accept(this, block);
        quadruples.addAll(left);
        quadruples.addAll(right);
        Register leftRegister = left.get(left.size() - 1).getRegister();
        Register rightRegister = right.get(right.size() - 1).getRegister();
        if (leftRegister.isConst() && rightRegister.isConst()) {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(leftRegister.getConstValue().getBool() || rightRegister.getConstValue().getBool()))));
        } else {
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.OR(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        }
        return quadruples;
    }
}
