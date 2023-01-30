package latte.backend.program.global.classes;

import latte.backend.program.global.Function;
import latte.backend.programvisitors.MethodPointerType;

import java.util.HashMap;
import java.util.List;

public class LLVMClassVTable {
    private final List<String> orderedMethods;
    private final HashMap<String, LLVMClassMethod> methodPointers;
    private final String className;

    public LLVMClassVTable(String name, List<String> orderedMethods, HashMap<String, LLVMClassMethod> methodPointers) {
        this.className = name;
        this.orderedMethods = orderedMethods;
        this.methodPointers = methodPointers;
    }

    @Override
    public String toString() {
        return createVTable() + "\n";
    }

    private String createVTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(vTableName(className)).append(" = global ").append("[").append(orderedMethods.size()).append(" x void (...)*] [\n");
        for (int i = 0; i < orderedMethods.size(); i++) {
            sb.append("\t").append(methodPointers.get(orderedMethods.get(i)).getLLVMTypeAndName()).append(" ");
            if (i != orderedMethods.size() - 1) {
                sb.append(", ");
            }
            sb.append("; ").append(orderedMethods.get(i)).append(" \n");
        }
        sb.append("]\n");
        return sb.toString();
    }
    public static String vTableName(String className) {
        return className + ".vtable";
    }

    public int getMethodIndex(String methodName) {
        return orderedMethods.indexOf(methodName);
    }

    public MethodPointerType getMethodType(String methodName) {
        return new MethodPointerType(methodPointers.get(methodName).getLLVMType());
    }

    public Function getMethod(String method) {
        return methodPointers.get(method);
    }
}
