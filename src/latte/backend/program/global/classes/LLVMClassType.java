package latte.backend.program.global.classes;

import latte.Internal.ClField;
import latte.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

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
        for(int i = 0; i < fields.size(); i++) {
            sb.append("\t").append(Utils.getLLVMType(fields.get(i).type_));
            if(i != fields.size() - 1) {
                sb.append(", ");
            }
            sb.append("; ").append(fields.get(i).ident_).append(" \n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
