package latte;

import latte.Absyn.Program;
import latte.errors.SemanticError;
import latte.frontend.SematicAnalyst;
import latte.parser.Yylex;
import latte.parser.parser;
import latte.utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class Compiler {
    Yylex l;
    parser p;

    String fileName = null;

    public Compiler(String[] args) {
        try {
            Reader input;
            if (args.length == 0) input = new InputStreamReader(System.in);
            else {input = new FileReader(args[0]); fileName = args[0];}
            l = new Yylex(input);
        } catch (IOException e) {
            System.err.println("ERROR\nFile not found: " + args[0]);
            System.exit(1);
        }
        p = new parser(l, l.getSymbolFactory());
    }
    public Compiler(String input) {
        l = new Yylex(new java.io.StringReader(input));
        p = new parser(l, l.getSymbolFactory());
    }

    public latte.Absyn.Program parse() throws Exception {
        return p.pProgram();
    }

    public static void main(String[] args) {
        Compiler t = new Compiler(args);
        t.compile();
    }

    public void compile() {
        Program ast;
        try {
            ast = this.parse();
        } catch (Throwable e) {
            System.err.println("ERROR");
            System.err.println("Parser error at line: " + this.l.line_num() + ", at offset: "+ this.l.left_loc().getOffset() +".\nError with parsing \"" + this.l.buff() + "\":");
            System.err.println("     " + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            SematicAnalyst analyst = new SematicAnalyst();
            latte.backend.program.Program program = analyst.checkTypes(ast);
            System.err.println("OK\n");
            Utils.generateOutput(fileName, program.getGlobal().toString());
            Utils.generateBytecode(fileName);
        } catch (SemanticError e) {
            System.err.println("ERROR\n");
            System.err.println("Semantic error at line " + e.getLineNum() + " :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
