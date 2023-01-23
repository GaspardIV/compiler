package latte.backend.program.global.classes;

import latte.Internal.ClField;
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
            sb.append("\t%").append(field.ident_).append(" = getelementptr %").append(name).append(", %").append(name).append("* %this, i32 0, i32 ").append(index).append("\n");
            sb.append("\tstore ").append(Utils.getLLVMType(field.type_)).append(" ").append(Utils.defaultValue(field.type_)).append(", ").append(Utils.getLLVMType(field.type_)).append("* %").append(field.ident_).append("\n");
            sb.append("\tret void\n");
            index++;
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static String construtorName(String name) {
        return name + ".constructor";
    }
}
