package latte.backend.programvisitors;

import latte.Absyn.*;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.ConstValue;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.utils.Utils;

import java.util.*;

public class RegisterExprVisitor implements Expr.Visitor<List<Quadruple>, Block> {
    private static final String TMP = "tmp.";

    @Override
    public List<Quadruple> visit(ENewArray p, Block block) {
        // todo
        return null;
    }

    @Override
    public List<Quadruple> visit(EArrayElem p, Block block) {
        // todo
        return null;
    }

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
        Register last = scope.getLastVariableRegister(variable);
        if (block.markPhiVariables && !block.hasPhiRegisterOfVariable(p.ident_)) {
            Register phiRegister =scope.getNewVariableRegister(variable);
            block.setPhiRegisterOfVariable(p.ident_, phiRegister);
            last = phiRegister;
        }
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
            List<Quadruple> exprQuadruples = p.listexpr_.get(i).accept(this, block);
            quadruples.addAll(exprQuadruples);
            registers.add(exprQuadruples.get(exprQuadruples.size() - 1).getRegister());
        }
        Quadruple.LLVMOperation.CALL quadruple = new Quadruple.LLVMOperation.CALL(function.getType(), function.getName(), registers);
//        if (new Void().equals(function.getType())){
//            quadruples.add(new Quadruple(null, quadruple));
//        }else{
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), function.getType()), quadruple));
//        }
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
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP),new Bool(), new ConstValue(!right.get(right.size() - 1).getRegister().getConstValue().getBool()))));
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
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP),leftRegister.type, new ConstValue(leftRegister.getConstValue().getInt() - rightRegister.getConstValue().getInt()))));
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
            quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        }
//        quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
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
//        quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.AND(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
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
//        quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.OR(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
        return quadruples;
    }
}
