package latte.backend.quadruple;

public class Quadruple {
    public Register result;
    public String op;
    public QuadrupleArg arg1;
    public QuadrupleArg arg2;

    public Quadruple( Register result) {
        this.result = result;
        this.op = null;
        this.arg1 = null;
        this.arg2 = null;
    }
    public Quadruple( Register result, QuadrupleArg arg1) {
        this.result = result;
        this.op = null;
        this.arg1 = arg1;
        this.arg2 = null;
    }
    public Quadruple( Register result, String op, QuadrupleArg arg1) {
        this.result = result;
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = null;
    }
    public Quadruple( Register result, String op, QuadrupleArg arg1, QuadrupleArg arg2) {
        this.result = result;
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public String toString() {
        if (op == null) {
            if (arg1 == null) {
                return "";
            } else {
                return result.toString() + " = " + arg1.toString();
            }
        } else if (arg2 == null) {
            return result.toString() + " = " + op + " " + arg1.toString();
        } else {
            return result.toString() + " = " + arg1.toString() + " " + op + " " + arg2.toString();
        }
    }
//        if (op == null) {
//            return result.toString();
//        } else if (arg2 == null) {
//            return result.toString() + " = " + op + " " + arg1.toString();
//        } else {
//            return result.toString() + " = " + arg1.toString() + " " + op + " " + arg2.toString();
//        }
//        return result + " = " + arg1 + " " + op + " " + arg2;
//    }

    public Register getRegister() {
        return result;
    }
}
