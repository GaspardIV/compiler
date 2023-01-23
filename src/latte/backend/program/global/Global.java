package latte.backend.program.global;

import latte.Internal.LatteClass;
import latte.backend.program.global.classes.LLVMClass;
import latte.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Global extends Scope {

    private Map<String, LatteClass> classDefs;

    public Global(String contextName, Scope parent) {
        super(contextName, parent);
        stringGlobals = new HashMap<>();
        if (instance != null) {
            throw new RuntimeException("Global already initialized");
        }
        functions = new HashMap<>();
        classes = new HashMap<>();
    }

    public boolean usePrintInt = false, usePrintString = false, useError = false, useConcat = false, useReadInt = false, useReadString = false, useCompareString = false;

    private static Global instance = null;
    final Map<String, String> stringGlobals;

    final Map<String, Function> functions;
    final Map<String, LLVMClass> classes;


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

    public void markIfRuntimeFunction(String name) {
        switch (name) {
            case "printInt":
                usePrintInt = true;
                break;
            case "printString":
                usePrintString = true;
                break;
            case "error":
                useError = true;
                break;
            case "readInt":
                useReadInt = true;
                break;
            case "readString":
                useReadString = true;
                break;
        }
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (LLVMClass LLVMClass : classes.values()) {
            stringBuilder.append(" ; --- Class ").append(LLVMClass.getName()).append(" ---\n");
            stringBuilder.append(LLVMClass.toString());

        }
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                stringBuilder.append(function);
            }
        }
        StringBuilder globalStrings = new StringBuilder();
        this.stringGlobals.forEach((name, value) -> globalStrings.append(globalStringToString(name, value)));
        return globalStrings.toString() + stringBuilder;
    }

    private String globalStringToString(String k, String v) {
        k = Utils.getEscapedString(k);
        return String.format("@.str.%s = private unnamed_addr constant [%d x i8] c\"%s\\00\", align 1", v, Utils.getLLVMEscapedStringLength(k), k);
    }

    public void convertToQuadruples() {
        for (LLVMClass LLVMClass : classes.values()) {
            LLVMClass.convertToLLVM();
        }
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                function.convertToQuadruples();
            }
        }
    }

    public String addStringGlobalRegister(String string) {
        if (!stringGlobals.containsKey(string)) {
            stringGlobals.put(string, "str" + stringGlobals.size());
        }
        return getStringGlobalRegister(string);
    }

    private String getStringGlobalRegister(String string) {
        if (stringGlobals.containsKey(string)) {
            return stringGlobals.get(string);
        } else {
            return addStringGlobalRegister(string);
        }
    }


    public Function getFunction(String ident_) {
        return functions.getOrDefault(ident_, null);
    }

    public void add(Function function) {
        functions.put(function.getName(), function);
    }

    public void add(LLVMClass LLVMClass) {
        classes.put(LLVMClass.getName(), LLVMClass);
    }

    public void setClassDefs(Map<String, LatteClass> classDefs) {
        this.classDefs = classDefs;
        for (String name : classDefs.keySet()) {
            LatteClass latteClass = classDefs.get(name);
            LLVMClass LLVMClass = new LLVMClass(name, this, latteClass);
            add(LLVMClass);
        }
    }

    public LLVMClass getLLVMClass(String name) {
        return classes.get(name);
    }
}
