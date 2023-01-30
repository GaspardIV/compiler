package latte.backend.programvisitors;

import latte.Absyn.Class;
import latte.Absyn.Void;
import latte.Absyn.*;
import latte.Internal.Null;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;
import latte.backend.program.global.classes.MethodPointerPointer;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.*;
import latte.errors.SemanticError;
import latte.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterExprVisitor implements Expr.Visitor<List<Quadruple>, Block> {
    public static final String TMP = "tmp.";

    public boolean loadField = true;

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
        int size = Global.getTypeSize(p.type_);
        int i32Size = Global.getTypeSize(new Int());
        List<Quadruple> result = new ArrayList<>(p.expr_.accept(this, block));
        Register sizeReg = result.get(result.size() - 1).result;
        Register sizeReg2 = new Register(block.getRegisterNumber(TMP), new Int());
        result.add(new Quadruple(sizeReg2, new Quadruple.LLVMOperation.MUL(new Times(), sizeReg, size)));
        Register bytesToAlloc = new Register(block.getRegisterNumber(TMP), new Int());
        result.add(new Quadruple(bytesToAlloc, new Quadruple.LLVMOperation.ADD(new Plus(), sizeReg2, i32Size)));
        Register negativePtr = new Register(block.getRegisterNumber(TMP), new Str());
        result.add(new Quadruple(negativePtr, new Quadruple.LLVMOperation.CALLOC(bytesToAlloc)));
        Register sizePtr = new Register(block.getRegisterNumber(TMP), new Int());
        result.add(new Quadruple(sizePtr, new Quadruple.LLVMOperation.BITCAST(negativePtr, negativePtr.type, new Array(new Int()))));
        result.add(new Quadruple(null, new Quadruple.LLVMOperation.STORE(sizeReg, sizePtr)));
        Register castPointer = new Register(block.getRegisterNumber(TMP), new Str());
        result.add(new Quadruple(castPointer, new Quadruple.LLVMOperation.GETELEMENTPTR(negativePtr, i32Size)));
        Register arrayPointer = new Register(block.getRegisterNumber(TMP), new Array(p.type_));
        result.add(new Quadruple(arrayPointer, new Quadruple.LLVMOperation.BITCAST(castPointer, castPointer.type, new Array(p.type_))));

        return result;
    }

    @Override
    public List<Quadruple> visit(EArrayElem p, Block block) {
        List<Quadruple> result = new ArrayList<>();
        RegisterExprVisitor visitor = new RegisterExprVisitor();
        visitor.loadField = true;
        List<Quadruple> quadruples = p.expr_1.accept(visitor, block);
        List<Quadruple> quadruples2 = p.expr_2.accept(visitor, block);
        result.addAll(quadruples);
        result.addAll(quadruples2);
        Quadruple last = quadruples2.get(quadruples2.size() - 1);
        Quadruple variable  = quadruples.get(quadruples.size() - 1);
//        Variable variable  = quadruples.get(quadruples.size() - 1).result.getVariable();
//        Array type = (Array) variable.getType();
        Array type = (Array) variable.result.type;
        Register register = new Register(block.getRegisterNumber(TMP), type.type_);
        result.add(new Quadruple(register, new Quadruple.LLVMOperation.GET_FIELD(variable.result, last.result)));
        if (loadField) {
            result.add(new Quadruple(new Register(block.getRegisterNumber(TMP), type.type_), new Quadruple.LLVMOperation.LOAD(register)));
        }
        return result;
    }

    @Override
    public List<Quadruple> visit(EArrayElemR p, Block block) {
        List<Quadruple> result = new ArrayList<>();
        RegisterExprVisitor visitor = new RegisterExprVisitor();
        visitor.loadField = true;
        List<Quadruple> quadruples = p.expr_.accept(visitor, block);
        result.addAll(quadruples);
        Quadruple last = quadruples.get(quadruples.size() - 1);
        Variable variable  = block.getVariable(p.ident_);
        Array type = (Array) variable.getType();
        Register register = new Register(block.getRegisterNumber(TMP), type.type_);
        result.add(new Quadruple(register, new Quadruple.LLVMOperation.GET_FIELD(block.getScope().getLastVariableRegister(variable), last.result)));
        if (loadField) {
            result.add(new Quadruple(new Register(block.getRegisterNumber(TMP), type.type_), new Quadruple.LLVMOperation.LOAD(register)));
        }
        return result;
    }

    @Override
    public List<Quadruple> visit(ENullArr p, Block block) {
        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Array(p.type_)), new Quadruple.LLVMOperation.NULL(new Array(p.type_))));
    }

    @Override
    public List<Quadruple> visit(ENull p, Block block) {
        return Collections.singletonList(new Quadruple(new Register(block.getRegisterNumber(TMP), new Class(p.ident_)), new Quadruple.LLVMOperation.NULL(new Class(p.ident_))));
    }

    @Override
    public List<Quadruple> visit(ENil p, Block block) {
        throw new RuntimeException("should never happen");
    }

    @Override
    public List<Quadruple> visit(ENew p, Block block) {
        String className = ((Class)p.type_).ident_;
        List<Quadruple> result = new ArrayList<>();
        Class type = new Class(className);
        int classSize = Global.getClassSize(className);
        Register register = new Register(block.getRegisterNumber(TMP), new Str());
        result.add(new Quadruple(register, new Quadruple.LLVMOperation.ALLOCA(classSize)));
        Register register2 = new Register(block.getRegisterNumber(TMP), type);
        result.add(new Quadruple(register2, new Quadruple.LLVMOperation.BITCAST(register, register.type, type)));
        result.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Void()), new Quadruple.LLVMOperation.CALL_CONSTRUCTOR(register2, type)));
        result.add(new Quadruple(register2, null));
        return result;
    }

    @Override
    public List<Quadruple> visit(EMethod p, Block block) {
        RegisterExprVisitor visitor = new RegisterExprVisitor();
        visitor.loadField = true;
        List<Quadruple> quadruples = p.expr_.accept(visitor, block);
        Quadruple classObject = quadruples.get(quadruples.size() - 1);
        Class type = (Class) classObject.getRegister().type;
        Function function = Global.getMethod(type.ident_, p.ident_);

        quadruples.addAll(this.getMethod(block, classObject.result, p.ident_));
        Register methodPointer = quadruples.get(quadruples.size() - 1).result;
        List<Quadruple> castQuadruples = Utils.castObjectToSuperClassIfNeeded(classObject.result, function.getArguments().get(0).getType(), block);
        if (!castQuadruples.isEmpty()) {
            quadruples.addAll(castQuadruples);
            classObject = quadruples.get(quadruples.size() - 1);
        }
        List<Register> registers = new ArrayList<>();
        registers.add(classObject.getRegister());
        for (int i = 0; i < p.listexpr_.size(); i++) {
            List<Quadruple> exprQuadruples = new RegisterExprVisitor().generateExprCode(p.listexpr_.get(i), block);
            Register exprLast = exprQuadruples.get(exprQuadruples.size() - 1).getRegister();
            castQuadruples = Utils.castObjectToSuperClassIfNeeded(exprLast, function.getArguments().get(i+1).getType(), block);
            if (!castQuadruples.isEmpty()) {
                exprQuadruples.addAll(castQuadruples);
            }
            quadruples.addAll(exprQuadruples);
            registers.add(exprQuadruples.get(exprQuadruples.size() - 1).getRegister());
        }

        Quadruple.LLVMOperation.CALLPointer quadruple = new Quadruple.LLVMOperation.CALLPointer(function.getType(), methodPointer, registers);
        quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), function.getType()), quadruple));
        return quadruples;
    }

    private List<Quadruple> getMethod(Block block, Register aClassObject, String methodName) {
        List<Quadruple> result = new ArrayList<>();
        Class aClass = (Class) aClassObject.type;
        Register vtablePointer = new Register(block.getRegisterNumber(TMP), new MethodPointerPointer());
        result.add(new Quadruple(vtablePointer, new Quadruple.LLVMOperation.GET_FIELD(aClassObject, 0)));
        Register loadVtablePointer = new Register(block.getRegisterNumber(TMP), new MethodPointerPointer());
        result.add(new Quadruple(loadVtablePointer, new Quadruple.LLVMOperation.LOAD(vtablePointer)));
        Register methodPointer = new Register(block.getRegisterNumber(TMP), new MethodPointerPointer());
        result.add(new Quadruple(methodPointer, new Quadruple.LLVMOperation.GET_FIELD(loadVtablePointer, 0, Global.getMethodIndex(aClass.ident_, methodName))));
        MethodPointerType methodPointerType = Global.getMethodPointerType(aClass.ident_, methodName);

        Register castedMethodPointer = new Register(block.getRegisterNumber(TMP), methodPointerType);
        result.add(new Quadruple(castedMethodPointer, new Quadruple.LLVMOperation.BITCAST(methodPointer, methodPointer.type, methodPointerType.toPointer())));
        Register loadedMethod = new Register(block.getRegisterNumber(TMP), methodPointerType);
        result.add(new Quadruple(loadedMethod, new Quadruple.LLVMOperation.LOAD(castedMethodPointer)));
        return result;
    }

    @Override
    public List<Quadruple> visit(EField p, Block block) {
        List<Quadruple> result = new ArrayList<>();
        RegisterExprVisitor visitor = new RegisterExprVisitor();
        visitor.loadField = true;
        List<Quadruple> quadruples = p.expr_.accept(visitor, block);
        result.addAll(quadruples);
        Quadruple last = quadruples.get(quadruples.size() - 1);
        if (last.result.type instanceof Array) {
            Register negativePtr = new Register(block.getRegisterNumber(TMP), new Str());
            Register arrayPointer = last.result;
            int i32Size = Global.getTypeSize(new Int());
            Register castPointer = new Register(block.getRegisterNumber(TMP), new Str());
            result.add(new Quadruple(castPointer, new Quadruple.LLVMOperation.BITCAST(arrayPointer, arrayPointer.type, new Str())));
            result.add(new Quadruple(negativePtr, new Quadruple.LLVMOperation.GETELEMENTPTR(castPointer, -i32Size)));
            Register sizePtr = new Register(block.getRegisterNumber(TMP), new Int());
            result.add(new Quadruple(sizePtr, new Quadruple.LLVMOperation.BITCAST(negativePtr, negativePtr.type, new Array(new Int()))));
            Register resultReg = new Register(block.getRegisterNumber(TMP), new Int());
            result.add(new Quadruple(resultReg, new Quadruple.LLVMOperation.LOAD(sizePtr)));
        } else {
            Class type = (Class) last.getRegister().type;
            int idx = Global.getInstance().getLLVMClass(type.ident_).getFieldIndex(p.ident_);
            Type fieldType = Global.getInstance().getLLVMClass(type.ident_).getFieldType(p.ident_);
            Register register = new Register(block.getRegisterNumber(TMP), fieldType);
            result.add(new Quadruple(register, new Quadruple.LLVMOperation.GET_FIELD(last.getRegister(), idx)));
            if (loadField) {
                result.add(new Quadruple(new Register(block.getRegisterNumber(TMP), fieldType), new Quadruple.LLVMOperation.LOAD(register)));
            }
        }
        return result;
    }

    @Override
    public List<Quadruple> visit(EVar p, Block block) {
        List<Quadruple> quadruples = new ArrayList<>();
        Variable variable = block.getVariable(p.ident_);
        if (variable == null) {
            quadruples = new EField(new EVar("self"), p.ident_).accept(this, block);
        } else {
            Scope scope = block.getScope();
            PhiManager.getInstance().addPhiRegisterIfNeeded(variable);
            Register last = scope.getLastVariableRegister(variable);
            last.setVariable(variable);
            quadruples.add(new Quadruple(last));
        }
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
        if (function == null) {
            return new EMethod(new EVar("self"), p.ident_, p.listexpr_).accept(this, block);
        }
        function.markAsUsed();
        List<Quadruple> quadruples = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        for (int i = 0; i < p.listexpr_.size(); i++) {

            List<Quadruple> exprQuadruples = new RegisterExprVisitor().generateExprCode(p.listexpr_.get(i), block);
            Register exprLast = exprQuadruples.get(exprQuadruples.size() - 1).getRegister();
            List<Quadruple> castQuadruples = Utils.castObjectToSuperClassIfNeeded(exprLast, function.getArguments().get(i).getType(), block);
            if (!castQuadruples.isEmpty()) {
                exprQuadruples.addAll(castQuadruples);
            }
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
        List<Quadruple> right = Utils.nilExprReplace(p.expr_2, left.get(left.size()-1).getRegister().type).accept(this, block);
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
            if (leftRegister.type instanceof Class || rightRegister.type instanceof Class) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
            } else if (leftRegister.type instanceof Str) {
                quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Int()), new Quadruple.LLVMOperation.CMP(left.get(left.size() - 1).getRegister(), right.get(right.size() - 1).getRegister())));
                if (p.relop_ instanceof EQU) {
                    quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, quadruples.get(quadruples.size() - 1).getRegister(), new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(0)))));
                } else if (p.relop_ instanceof NE) {
                    quadruples.add(new Quadruple(new Register(block.getRegisterNumber(TMP), new Bool()), new Quadruple.LLVMOperation.REL(p.relop_, quadruples.get(quadruples.size() - 1).getRegister(), new Register(block.getRegisterNumber(TMP), new Bool(), new ConstValue(0)))));
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
