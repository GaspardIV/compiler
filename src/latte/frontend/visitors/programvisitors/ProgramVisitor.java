package latte.frontend.visitors.programvisitors;

import latte.Absyn.*;
import latte.backend.program.Program;
import latte.backend.program.global.Classs;
import latte.backend.program.global.Scope;
import latte.frontend.environment.Environment;

import java.lang.Void;
import java.util.HashMap;
import java.util.Map;

//
public class ProgramVisitor implements TopDef.Visitor<Void, Void> {
    private final Program program;
    private final Environment env;
    Map<String, Classs> classesMap = new HashMap<String, Classs>();
//    Map<String, Function> functionsMap = new HashMap<String, Function>();

    private Classs getOrCreateClass(String name, Scope parent) {
        if (classesMap.containsKey(name)) {
            return classesMap.get(name);
        } else {
            Classs classs = new Classs(name, parent);
            classesMap.put(name, classs);
            return classs;
        }
    }


    public ProgramVisitor(Environment environment, Program program) {
        this.env = environment;
        this.program = program;
    }

    @Override
    public Void visit(FnDef p, Void arg) {
        program.addFunction(p.ident_, p.type_, p.listarg_, ((Blk)p.block_).liststmt_);
        return null;
    }

    @Override
    public Void visit(ClDef p, Void arg) {
//        Classs classs = getOrCreateClass(p.ident_, arg.getGlobal());
//        arg.addClass(classs);
        return null;
    }

    @Override
    public Void visit(ClDefExt p, Void arg) {
        if (p == null) return null;
//        this.visit(env.getClassDef(p.ident_2).classDef, arg);
//        Classs classs = new Classs(p.ident_1, getOrCreateClass(p.ident_2));
        //dla kazdej z bezposrednich metod dodaj do classs scopa (nadpisuje metode z nadklasy)
        // dla kazdej z bezposrednich zmiennych dodaj do classs scopa (nadpisuje zmienna z nadklasy)

//        arg.addClass(classs);
        return null;
    }
}
