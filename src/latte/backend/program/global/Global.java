package latte.backend.program.global;

import latte.utils.Utils;

public class Global extends Scope {

    public Global(String contextName, Scope parent) {
        super(contextName, parent);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                stringBuilder.append(function.toString());
            }
        }
        StringBuilder globalStrings = new StringBuilder();
        this.stringGlobals.forEach((name, value) -> globalStrings.append(globalStringToString(name, value)));

        return globalStrings.toString() + stringBuilder.toString();
//        return stringBuilder.toString();
    }

    private String globalStringToString(String k, String v) {
        k = Utils.getEscapedString(k);
        return String.format("@.str.%s = private unnamed_addr constant [%d x i8] c\"%s\\00\", align 1", v, Utils.getLLVMEscapedStringLength(k), k);
    }

    public void convertToQuadruples() {
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                function.convertToQuadruples();
            }
        }
    }
}
