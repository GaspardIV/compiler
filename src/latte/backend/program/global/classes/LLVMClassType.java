package latte.backend.program.global.classes;

import latte.Internal.ClField;
import latte.utils.Utils;

import java.util.List;

public class LLVMClassType {
    private final List<ClField> fields;
    private final String name;

    public LLVMClassType(String name, List<ClField> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("%").append(name).append(" = type { \n");
        sb.append("\t").append("void (...)**; vtable\n\t");

        for (ClField field : fields) {
            sb.append(",").append(Utils.getLLVMType(field.type_));
            sb.append("; ").append(field.ident_).append(" \n\t");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public int getSize() {
        return fields.size();
    }
}
