package latte.backend.quadruple;

import latte.Absyn.AddOp;
import latte.Absyn.MulOp;
import latte.Absyn.RelOp;

import java.util.List;

public class Quadruple {
    public Register result;
    public LLVMOperation op;
    public Quadruple( Register result) {
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
        return result.toString() + " = " + op.toString() + "\n";
    }

    public Register getRegister() {
        return result;
    }

    public static class LLVMOperation {

        public static class CALL extends LLVMOperation {
            public String name;
            public List<Register> args;
            public CALL(String name, List<Register> registers) {
                this.name = name;
                this.args = registers;
            }
        }

        public static class NEG extends LLVMOperation {
            public Register register;
            public NEG(Register register) {
                this.register = register;
            }
        }

        public static class NOT extends LLVMOperation {
            public Register register;
            public NOT(Register register) {
                this.register = register;
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
        }

        public static class ADD extends LLVMOperation {
            public AddOp op;
            public Register register1;
            public Register register2;
            public ADD(AddOp addop_, Register register1, Register register2) {
                super();
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
        }

        public static class AND extends LLVMOperation {
            public Register register1;
            public Register register2;
            public AND(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }
        }

        public static class OR extends LLVMOperation {
            public Register register1;
            public Register register2;
            public OR(Register register1, Register register2) {
                this.register1 = register1;
                this.register2 = register2;
            }
        }

        public static class ASSIGN extends LLVMOperation {
            public Register register;
            public ASSIGN(Register register) {
                this.register = register;
            }
        }

        public static class VALUE extends LLVMOperation {
            public VALUE(Value value) {
                super();
            }
        }
    }
}
