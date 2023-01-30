package latte.backend.quadruple;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.Absyn.Void;
import latte.backend.program.global.Function;
import latte.backend.program.global.Global;
import latte.backend.program.global.classes.LLVMClassConstructor;
import latte.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Quadruple {
    public Register result;
    public LLVMOperation op;

    public Quadruple(Register result) {
        this.result = result;
        this.op = null;
    }

    public Quadruple(Register result, LLVMOperation op) {
        this.result = result;
        this.op = op;
    }

    public String toString() {
        if (op == null) {
            return "";
        }
        if (result == null) {
            return op + "";
        }
        if (result.type.equals(new Void())) {
            return "\t" + op.toString();
        }
        return "\t" + result.toString() + " = " + op.toString();
    }

    public Register getRegister() {
        return result;
    }


    public Register getDefinedRegister() {
        if (result == null || result.isConst()) {
            return null;
        } else {
            if (op == null) {
                return null;
            }
            return result;
        }
    }

    public Collection<Register> getUsedRegisters() {
        if (op == null) {
            return new ArrayList<>();
        }
        return op.getUsedRegisters().stream().filter(r -> !r.isConst()).collect(Collectors.toList());
    }

    public boolean hasSideEffects() {
        if (op == null) {
            return false;
        }
        return op.hasSideEffects();
    }

    public boolean isDefinition() {
        if (op == null) {
            return false;
        }
        return op.isDefinition();
    }

    public abstract static class LLVMOperation {

        public abstract Collection<Register> getUsedRegisters();

        public abstract boolean hasSideEffects();

        public boolean isDefinition() {
            return !hasSideEffects();
        }

        public abstract String toString();

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof LLVMOperation) {
                return toString().equals(obj.toString());
            } else {
                return false;
            }
        }

        public static class CALLPointer extends LLVMOperation {
            private final Type type;
            public Register functionPointer;
            public List<Register> args;

            public CALLPointer(Type type, Register functionPointer, List<Register> registers) {
                this.args = registers;
                this.functionPointer = functionPointer;
                this.type = type;
            }

            public String toString() {
                StringBuilder argsString = new StringBuilder();
                for (int i = 0; i < args.size(); i++) {
                    argsString.append(args.get(i).getLLVMType()).append(" ").append(args.get(i).toString());
                    if (i != args.size() - 1) {
                        argsString.append(", ");
                    }
                }
                return "call " + Utils.getLLVMType(type) + " " + functionPointer + "(" + argsString + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                List<Register> registers = new ArrayList<>();
                registers.add(functionPointer);
                registers.addAll(args);
                return registers;
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }
        public static class CALL extends LLVMOperation {
            private final Type type;
            public String name;
            public List<Register> args;

            public CALL(Type type, String name, List<Register> registers) {
                this.name = name;
                this.args = registers;
                this.type = type;
            }

            public String toString() {
                StringBuilder argsString = new StringBuilder();
                for (int i = 0; i < args.size(); i++) {
                    argsString.append(args.get(i).getLLVMType()).append(" ").append(args.get(i).toString());
                    if (i != args.size() - 1) {
                        argsString.append(", ");
                    }
                }
                return "call " + Utils.getLLVMType(type) + " @" + Function.nameFromLabel(name) + "(" + argsString + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return args;
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class NEG extends LLVMOperation {
            public Register register;

            public NEG(Register register) {
                this.register = register;
            }

            public String toString() {
                return "sub i32 0, " + register.toString();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singleton(register);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class NOT extends LLVMOperation {
            public Register register;

            public NOT(Register register) {
                this.register = register;
            }

            public String toString() {
                return "xor i1 1, " + register.toString();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singleton(register);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }


        public static class MUL extends LLVMOperation {
            public MulOp op;
            public Register register1;
            public Register register2;

            int m2;

            public MUL(MulOp mulop_, Register register1, int m2) {
                this.op = mulop_;
                this.register1 = register1;
                this.register2 = null;
                this.m2 = m2;
            }

            public MUL(MulOp mulop_, Register register1, Register register2) {
                this.op = mulop_;
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                if (op instanceof Times) {
                    return "mul i32 " + register1.toString() + ", " + (register2 == null ? Integer.toString(m2) : register2.toString());
                } else if (op instanceof Div) {
                    return "sdiv i32 " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof Mod) {
                    return "srem i32 " + register1.toString() + ", " + register2.toString();
                } else {
                    throw new RuntimeException("Unknown MulOp");
                }
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                if (register2 == null) {
                    return Collections.singleton(register1);
                } else {
                    return Arrays.asList(register1, register2);
                }
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class ADD extends LLVMOperation {
            private int m2 = 0;
            public AddOp op;
            public Register register1;
            public Register register2;

            public ADD(AddOp addop_, Register register1, int m2) {
                this.op = addop_;
                this.register1 = register1;
                this.register2 = null;
                this.m2 = m2;
            }

            public ADD(AddOp addop_, Register register1, Register register2) {
                this.op = addop_;
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                String r2 = register2 == null ? Integer.toString(m2) : register2.toString();
                if (op instanceof Plus) {
                    if (register1.type instanceof Str) {
                        Global.getInstance().useConcat = true;
                        return "call i8* @._concat(i8* " + register1 + ", i8* " + r2 + ")";
                    }
                    return "add " + register1.getLLVMType() + " " + register1.toString() + ", " + r2;
                } else {
                    return "sub " + register1.getLLVMType() + " " + register1.toString() + ", " + r2;
                }
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                if (register2 == null) {
                    return Collections.singleton(register1);
                } else {
                    return Arrays.asList(register1, register2);
                }
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class REL extends LLVMOperation {
            public RelOp op;
            public Register register1;
            public Register register2;

            public REL(RelOp relop_, Register register1, Register register2) {
                op = relop_;
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                if (op instanceof EQU) {
                    return "icmp eq " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof NE) {
                    return "icmp ne " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof LTH) {
                    return "icmp slt " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof LE) {
                    return "icmp sle " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof GTH) {
                    return "icmp sgt " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof GE) {
                    return "icmp sge " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                }
                return null;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class AND extends LLVMOperation {
            public Register register1;
            public Register register2;

            public AND(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                return "and i1 " + register1.toString() + ", " + register2.toString();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class OR extends LLVMOperation {
            public Register register1;
            public Register register2;

            public OR(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                return "or i1 " + register1.toString() + ", " + register2.toString();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class GETELEMENTPTRSTR extends LLVMOperation {
            private final int length;
            private final Type type;
            private final String ident;
            private final int i;
            private final int j;


            public GETELEMENTPTRSTR(int length, String ident, int i, int i1) {
                this.length = length;
                this.type = new Str(); // bo to jest [length x i8], czyli tablica stringow
                this.ident = "@.str." + ident;
                this.i = i;
                this.j = i1;
            }

            @Override
            public String toString() {
                String type1 = Utils.getLLVMType(type).replaceFirst("\\*", "");
                return "getelementptr [" + length + " x " + type1 + "], [" + length + " x " + type1 + "]* " + ident + ", i32 " + i + ", i32 " + j;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList(); // todo if uzywane do czegos tinnego niz string
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class GOTO extends LLVMOperation {
            public final Block block;

            public GOTO(Block block) {
                this.block = block;
            }

            @Override
            public String toString() {
                if (block == null) {
                    return "";
                }
                return "\tbr label %" + block.getIdentifier();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList();
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class IF extends LLVMOperation {
            private final Register register;
            public final Block block1;
            public final Block block2;

            public IF(Register register, Block label, Block labal2) {
                this.register = register;
                this.block1 = label;
                this.block2 = labal2;
            }

            @Override
            public String toString() {
                if (block1.getIdentifier().equals(block2.getIdentifier())) {
                    return "\tbr label %" + block1.getIdentifier();
                }
                return "\tbr i1 " + register.toString() + ", label %" + block1.getIdentifier() + ", label %" + block2.getIdentifier();
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class LABEL extends LLVMOperation {
            public final Block label;

            public LABEL(Block label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return label.getIdentifier() + ":";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList();
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class RET extends LLVMOperation {
            private final Register register;

            public RET() {
                this.register = null;
            }

            public RET(Register register) {
                this.register = register;
            }

            @Override
            public String toString() {
                if (register == null) {
                    return "\tret void";
                }
                return "\tret " + register.getLLVMType() + " " + register;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                if (register == null) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class PHI extends LLVMOperation {
            public Block block2;
            public final Register register1;

            public Block block1;
            public final Register register2;

            public PHI(Register oldsRegister, Block entry, Register register, Block block2) {
                this.register1 = oldsRegister;
                this.block1 = entry;
                this.register2 = register;
                this.block2 = block2;
            }

            @Override
            public String toString() {
                return "phi " + register1.getLLVMType() + " [" + register1.toString() + ", %" + block1.getIdentifier() + "], [" + register2.toString() + ", %" + block2.getIdentifier() + "]";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class BOOL_PHI extends PHI {

            public BOOL_PHI(Block identifier, Block identifier1) {
                super(null, identifier, null, identifier1);
            }

            @Override
            public String toString() {
                return "phi i1 [true, %" + block1.getIdentifier() + "], [false, %" + block2.getIdentifier() + "]";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList();
            }
        }

        public static class CMP extends LLVMOperation {
            private final Register register1;
            private final Register register2;

            public CMP(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }

            @Override
            public String toString() {
                Global.getInstance().useCompareString = true;
                return "call i32 @._strcmp(i8* " + register1 + ", i8* " + register2.toString() + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class ALLOCA extends LLVMOperation {
            private final int sizeInBytes;


            public ALLOCA(int size) {
                Global.getInstance().useMalloc = true;
                this.sizeInBytes = size;
            }

            @Override
            public String toString() {
                return "call i8* @malloc(i32 " + sizeInBytes * 8 + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList();
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }


        public static class CALL_CONSTRUCTOR extends LLVMOperation {
            private final Register register;
            private final Class type;

            public CALL_CONSTRUCTOR(Register register, Class type) {
                this.register = register;
                this.type = type;
            }

            @Override
            public String toString() {
                return "call void @" + LLVMClassConstructor.construtorName(type.ident_) + "(" + Utils.getLLVMType(type) + " " + register + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class GET_FIELD extends LLVMOperation {
            private final Register register;
            private final Register register2;
            private final int index;
            private int level = 1;


            public GET_FIELD(Register register, Register index) {
                this.register = register;
                this.register2 = index;
                this.index = -1;
            }

            public GET_FIELD(Register register, int index) {
                this.register = register;
                this.index = index;
                this.register2 = null;
            }

            public GET_FIELD(Register register, int level,  int index) {
                this.register = register;
                this.index = index;
                this.register2 = null;
                this.level = level;
            }

            @Override
            public String toString() {
                String withoutStar = register.getLLVMType().replaceFirst("\\*", "");
                if (this.register2 != null) {
                    return "getelementptr " + withoutStar + ", " + withoutStar + "* " + register + ", i32 " + register2;
                } else {
                    if (level == 0) {
                        return "getelementptr " + withoutStar + ", " + withoutStar + "* " + register + ", i32 " + index;
                    } else {
                        return "getelementptr " + withoutStar + ", " + withoutStar + "* " + register + ", i32 0, i32 " + index;
                    }
                }
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                if (register2 == null) {
                    return Collections.singletonList(register);
                } else {
                    return Arrays.asList(register, register2);
                }
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class LOAD extends LLVMOperation {
            private final Register register;
            private final Type type;

            public LOAD(Register register) {
                this.register = register;
                this.type = register.type;
            }

            @Override
            public String toString() {
                return "load " + Utils.getLLVMType(type) + ", " + Utils.getLLVMType(type) + "* " + register;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class STORE extends LLVMOperation {
            private final Register register1;
            private final Register register2;

            public STORE(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }

            @Override
            public String toString() {
                String withoutStar = register1.getLLVMType();

                return "\tstore " + withoutStar + " " + register1 + ", " + withoutStar + "* " + register2;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Arrays.asList(register1, register2);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class NULL extends LLVMOperation {
            private final Type type;

            public NULL(Type type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "bitcast i32* null to " + Utils.getLLVMType(type);
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.emptyList();
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }
        }

        public static class BITCAST extends LLVMOperation {
            private final Register register;
            private final Type type;
            private final Type from;

            public BITCAST(Register register, Type from, Type type) {
                this.register = register;
                this.from = from;
                this.type = type;
            }

            @Override
            public String toString() {
                return "bitcast " + Utils.getLLVMType(from) + " " + register + " to " + Utils.getLLVMType(type);
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class CALLOC extends LLVMOperation {
            private final Register size;

            public CALLOC(Register register) {
                this.size = register;
                Global.getInstance().useCalloc = true;
            }

            @Override
            public String toString() {
                return "call i8* @calloc(i32 1, i32 " + size + ")";
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(size);
            }

            @Override
            public boolean hasSideEffects() {
                return true;
            }
        }

        public static class GETELEMENTPTR extends LLVMOperation {
            private final Register register;
            private final int index;

            public GETELEMENTPTR(Register register, int index) {
                this.register = register;
                this.index = index;
            }

            @Override
            public Collection<Register> getUsedRegisters() {
                return Collections.singletonList(register);
            }

            @Override
            public boolean hasSideEffects() {
                return false;
            }

            @Override
            public String toString() {
                String withoutStar = register.getLLVMType().replaceFirst("\\*", "");
                return "getelementptr " + withoutStar + ", " + withoutStar + "* " + register + ", i32 " + index;
            }
        }
    }
}

