package latte.backend.program.global.classes;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.backend.program.global.Function;
import latte.backend.program.global.Scope;
import latte.backend.program.global.Variable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LLVMClassMethod extends Function {

    public LLVMClassMethod(String name, Type type, List<Variable> arguments, ListStmt statements, Scope scope) {
        super(name, type, arguments, statements, scope);
    }
}
