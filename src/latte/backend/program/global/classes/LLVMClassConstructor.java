package latte.backend.program.global.classes;

import latte.Absyn.Array;
import latte.Absyn.Class;
import latte.Internal.ClField;
import latte.Internal.MethodPointerPointer;
import latte.backend.program.global.Global;
import latte.backend.programvisitors.RegisterExprVisitor;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.utils.Utils;

import java.util.List;

public class LLVMClassConstructor {
    private final String name;
    private final List<ClField> fields;
    private final int vtableSize;

    public LLVMClassConstructor(String name, List<ClField> fields, int vtableSize) {
        this.name = name;
        this.fields = fields;
        this.vtableSize = vtableSize;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("define void @").append(construtorName(name)).append("(%").append(name).append("* %this) {\n");
        sb.append("\t").append("%this.class.vtable = ").append("bitcast [").append(vtableSize).append(" x void (...)*]* @").append(LLVMClassVTable.vTableName(name)).append(" to void (...)**\n");
        Register vtable = new Register("this.vtable", new MethodPointerPointer());
        sb.append(new Quadruple(vtable, new Quadruple.LLVMOperation.GET_FIELD(new Register("this", new Class(name)), 0))).append("\n");
        sb.append(new Quadruple(null, new Quadruple.LLVMOperation.STORE(new Register("this.class.vtable", new MethodPointerPointer()), vtable))).append("\n");
        int index = 1;
        for (ClField field : fields) {
            sb.append(new Quadruple(new Register(field.ident_, field.type_), new Quadruple.LLVMOperation.GET_FIELD(new Register("this", new Class(name)), index))).append("\n");
            List<Quadruple> quadruples = Utils.defaultValue(field.type_).accept(new RegisterExprVisitor(), new Block("init", Global.getInstance()));
            if (quadruples.get(0).result.type instanceof Class || quadruples.get(0).result.type instanceof latte.Absyn.Str || quadruples.get(0).result.type instanceof Array) { // todo pewnie dodac obsluge arrayow
                quadruples.get(0).result.name = field.ident_+"tmp";
                sb.append(quadruples.get(0)).append("\n");
            }
            sb.append(new Quadruple(null, new Quadruple.LLVMOperation.STORE(quadruples.get(0).result, new Register(field.ident_, field.type_)))).append("\n");
            index++;
        }
        sb.append("\tret void\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static String construtorName(String name) {
        return name + ".constructor";
    }
}
