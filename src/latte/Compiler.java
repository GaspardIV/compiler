// File generated by the BNF Converter (bnfc 2.9.3).

package latte;

import latte.Absyn.Program;
import latte.errors.SemanticError;
import latte.frontend.SematicAnalyst;
import latte.parser.Yylex;
import latte.parser.parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class Compiler {
    Yylex l;
    parser p;

    public Compiler(String[] args) {
        try {
            Reader input;
            if (args.length == 0) input = new InputStreamReader(System.in);
            else input = new FileReader(args[0]);
            l = new Yylex(input);
        } catch (IOException e) {
            System.err.println("ERROR\nFile not found: " + args[0]);
            System.exit(1);
        }
        p = new parser(l, l.getSymbolFactory());
    }

    public latte.Absyn.Program parse() throws Exception {
        return p.pProgram();
    }

    public static void main(String[] args) {
        Compiler t = new Compiler(args);

        Program ast;
        try {
            ast = t.parse();
        } catch (Throwable e) {
            System.err.println("ERROR");
            System.err.println("Parser error at line: " + t.l.line_num() + ", at offset: "+ t.l.left_loc().getOffset() +".\nError with parsing \"" + t.l.buff() + "\":");
            System.err.println("     " + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            SematicAnalyst analyst = new SematicAnalyst();
            analyst.checkTypes(ast);
            System.err.println("OK\n");
        } catch (SemanticError e) {
            System.err.println("ERROR\n");
            System.err.println("Semantic error at line " + e.getLineNum() + " :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        }
    }
}
