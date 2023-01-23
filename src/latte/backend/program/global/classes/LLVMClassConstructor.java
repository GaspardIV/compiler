package latte.backend.program.global.classes;

import latte.Absyn.Class;
import latte.Internal.ClField;
import latte.backend.program.global.Global;
import latte.backend.program.global.Scope;
import latte.backend.programvisitors.RegisterExprVisitor;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;
import latte.utils.Utils;

import java.util.List;

public class LLVMClassConstructor extends LLVMClassMethod {
    private final String name;
    private final List<ClField> fields;

    public LLVMClassConstructor(String name, List<ClField> fields) {
        this.name = name;
        this.fields = fields;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("define void @").append(construtorName(name)).append("(%").append(name).append("* %this) {\n");
        int index = 0;
        for (ClField field : fields) {
            sb.append(new Quadruple(new Register(field.ident_, field.type_), new Quadruple.LLVMOperation.GET_FIELD(new Register("this", new Class(name)), index))).append("\n");
            List<Quadruple> quadruples = Utils.defaultValue(field.type_).accept(new RegisterExprVisitor(), new Block("init", Global.getInstance()));
            if (quadruples.get(0).result.type instanceof Class || quadruples.get(0).result.type instanceof latte.Absyn.Str) {
////                quadruples.get(0).result.type = new Class(name);
                quadruples.get(0).result.name = field.ident_+"tmp";
                sb.append(quadruples.get(0)).append("\n");
            }
            sb.append(new Quadruple(null, new Quadruple.LLVMOperation.STORE(quadruples.get(0).result, new Register(field.ident_, field.type_)))).append("\n");
//            }
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
