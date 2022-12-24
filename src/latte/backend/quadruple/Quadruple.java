package latte.backend.quadruple;

import latte.Absyn.*;
import latte.Absyn.Void;
import latte.utils.Utils;

import java.util.List;

public class Quadruple {
    public Register result;
    public LLVMOperation op;
    public Quadruple(Register result) {
        this.result = result;
        this.op = null;
    }
    public Quadruple( Register result, LLVMOperation op) {
        this.result = result;
        this.op = op;
    }

    public String toString() {
        if (op == null) {
            return " ; " + result.toString() +"\n";
        }
        if (result == null) {
            return  op.toString() +"\n";
        }
        if (result.type.equals(new Void())) {
            return op.toString() + "\n";
        }
        return result.toString() + " = " + op.toString() + "\n";
    }

    public Register getRegister() {
        return result;
    }

    public static class LLVMOperation {

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
//                join args with comma as a separator without trailing comma
                for (int i = 0; i < args.size(); i++) {
                    argsString.append(args.get(i).getLLVMType()).append(" ").append(args.get(i).toString());
                    if (i != args.size() - 1) {
                        argsString.append(", ");
                    }
                }
                return "call " + Utils.getLLVMType(type) + " @" + name + "(" + argsString + ")";
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
        }

        public static class NOT extends LLVMOperation {
            public Register register;

            public NOT(Register register) {
                this.register = register;
            }

            public String toString() {
                return "xor i1 1, " + register.toString();
            }
        }


        public static class MUL extends LLVMOperation {
            public MulOp op;
            public Register register1;
            public Register register2;

            public MUL(MulOp mulop_, Register register1, Register register2) {
                this.op = mulop_;
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                if (op instanceof Times) {
                    return "mul i32 " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof Div) {
                    return "sdiv i32 " + register1.toString() + ", " + register2.toString();
                } else if (op instanceof Mod) {
                    return "srem i32 " + register1.toString() + ", " + register2.toString();
                } else {
                    throw new RuntimeException("Unknown MulOp");
                }
            }
        }

        public static class ADD extends LLVMOperation {
            public AddOp op;
            public Register register1;
            public Register register2;

            public ADD(AddOp addop_, Register register1, Register register2) {
                this.op = addop_;
                this.register1 = register1;
                this.register2 = register2;
            }

            public String toString() {
                if (op instanceof Plus) {
                    if (register1.type instanceof Str) {
                        return "call i8* @concat(i8* " + register1.toString() + ", i8* " + register2.toString() + ")";
                    }
                    return "add " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                } else {
                    return "sub " + register1.getLLVMType() + " " + register1.toString() + ", " + register2.toString();
                }
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
        }

        public static class ASSIGN extends LLVMOperation {
            public Register register;

            public ASSIGN(Register register) {
                this.register = register;
            }

            public String toString() {
                return register.toString();
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
                String type1 = Utils.getLLVMType(type).replace("*", "");
                return "getelementptr [" + length + " x " + type1 + "], [" + length + " x " + type1 + "]* " + ident + ", i32 " + i + ", i32 " + j;
            }
        }

        public static class GOTO extends LLVMOperation {
            private final String label;

            public GOTO(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return "br label %" + label;
            }
        }

        public static class IF extends LLVMOperation {
            private final Register register;
            private final String label;
            private final String label2;

            public IF(Register register, String label, String labal2) {
                this.register = register;
                this.label = label;
                this.label2 = labal2;
            }

            @Override
            public String toString() {
                return "br i1 " + register.toString() + ", label %" + label + ", label %" + label2;
            }
        }

        public static class LABEL extends LLVMOperation {
            private final String label;

            public LABEL(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return label + ":";
            }
        }

        public static class VRET extends LLVMOperation {
            private final Register register;

            public VRET() {
                this.register = null;
            }
            public VRET(Register register) {
                this.register = register;
            }

            @Override
            public String toString() {
                if (register == null) {
                    return "ret void";
                }
                return "ret " + register.getLLVMType() + " " + register.toString();
            }
        }
    }
}
