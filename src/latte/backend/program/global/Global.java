package latte.backend.program.global;

import latte.utils.Utils;

public class Global extends Scope {

    public Global(String contextName, Scope parent) {
        super(contextName, parent);
        if (instance != null) {
            throw new RuntimeException("Global already initialized");
        }
    }

    public int usePrintInt = 0,  usePrintString = 0,  useError = 0,  useConcat = 0,  useReadInt = 0,  useReadString = 0, useCompareString = 0;

    private static Global instance = null;
    public static Global getInstance() {
        if (instance == null) {
            instance = new Global("global", null);
        }
        return instance;
    }

    public static Global reset() {
        instance = null;
        return getInstance();
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                stringBuilder.append(function);
            }
        }
        StringBuilder globalStrings = new StringBuilder();
        this.stringGlobals.forEach((name, value) -> globalStrings.append(globalStringToString(name, value)));

        return globalStrings.toString() + stringBuilder;
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
