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
        sb.append("\t").append("void (...)**");

        for (ClField field : fields) {
            sb.append(",\n\t").append(Utils.getLLVMType(field.type_));
            sb.append("; ").append(field.ident_).append(" \n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public int getSize() {
        return fields.size();
    }
}
