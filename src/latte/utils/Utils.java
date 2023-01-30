package latte.utils;

import latte.Absyn.*;
import latte.Absyn.Class;
import latte.Internal.Null;
import latte.backend.program.global.Global;
import latte.backend.program.global.classes.MethodPointerPointer;
import latte.backend.programvisitors.MethodPointerType;
import latte.backend.quadruple.Block;
import latte.backend.quadruple.Quadruple;
import latte.backend.quadruple.Register;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static latte.backend.programvisitors.RegisterExprVisitor.TMP;

public class Utils {

    public static List<Quadruple> castObjectToSuperClassIfNeeded(Register object, Type destinationClass, Block block) {
        List<Quadruple> res = new ArrayList<>();
        // todo cast null
        if (object.type instanceof Class && destinationClass instanceof Class || object.type instanceof Null) {
            if (!Utils.getLLVMType(object.type).equals(Utils.getLLVMType(destinationClass))) {
                res.add(new Quadruple(new Register(block.getRegisterNumber(TMP), destinationClass), new Quadruple.LLVMOperation.BITCAST(object, object.type, destinationClass)));
            }
        }
        return res;
    }

    public static String toString(Type actual) {
        if (actual instanceof Array) {
            return "Array";
        } else if (actual instanceof Bool) {
            return "Bool";
        } else if (actual instanceof latte.Absyn.Int) {
            return "Int";
        } else if (actual instanceof latte.Absyn.Str) {
            return "String";
        } else if (actual instanceof latte.Absyn.Void) {
            return "Void";
        } else if (actual instanceof latte.Absyn.Class) {
            return ((latte.Absyn.Class) actual).ident_;
        } else if (actual instanceof Null) {
            return "Null";
        } else {
            return "null";
        }
    }

    public static String getLLVMType(Type actual) {
        if (actual instanceof Array) {
            return getLLVMType(((Array) actual).type_) + "*";
        } else if (actual instanceof Bool) {
            return "i1";
        } else if (actual instanceof latte.Absyn.Int) {
            return "i32";
        } else if (actual instanceof latte.Absyn.Str) {
            return "i8*";
        } else if (actual instanceof latte.Absyn.Void) {
            return "void";
        } else if (actual instanceof latte.Absyn.Class) {
            return "%"+((latte.Absyn.Class) actual).ident_+"*";
        } else if (actual instanceof Null) {
            return "null";
        } else  if (actual instanceof MethodPointerPointer) {
            if (((MethodPointerPointer) actual).llvmType == null) {
                return "void (...)**";
            } else {
                return ((MethodPointerPointer) actual).llvmType + "**";
            }
        } else if (actual instanceof MethodPointerType) {
            if (((MethodPointerType) actual).llvmType != null) {
                return ((MethodPointerType) actual).llvmType + "*";
            } else {
                return "void (...)*";
            }
        } else {
            return "null";
        }
    }

    public static void writeToFile(String output, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(output);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void generateOutput(String fileName, Global global) {
        String outputFileName = Utils.withExtension(fileName, "ll");
        StringBuilder output = new StringBuilder();
        output.append(global);
        output.append("\n\n; ====================================================\n");
        output.append("; ====================================================\n");
        output.append("; ====================================================\n\n");

        if (global.usePrintInt) {
            output.append("@._dnl = internal constant [4 x i8] c\"%d\\0A\\00\"\n" +
                    "declare i32 @printf(i8*, ...)\n" +
                    "define void @printInt(i32 %x) {\n" +
                    "       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0\n" +
                    "       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)\n" +
                    "       ret void\n" +
                    "}\n");
            output.append("\n");
        }
        if (global.usePrintString) {
            output.append("declare i32 @puts(i8*)\n" +
                    "define void @printString(i8* %s) {\n" +
                    "entry:  call i32 @puts(i8* %s)\n" +
                    "\tret void\n" +
                    "}\n");
            output.append("\n");
        }
        if (global.useError) {
            output.append("@._runtime_error = internal constant [15 x i8] c\"runtime error\\0A\\00\"\n" +
                    "declare void @exit(i32)\n" +
                    "define void @error() {\n" +
                    "entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0\n" +
                    "call void @printString(i8* %t0)\n\n" +
                    "call void @exit(i32 1)\n" +
                    "\tret void\n" +
                    "}\n");
            output.append("\n");
        }
        if (global.useCalloc) {
            output.append("declare i8* @calloc(i32, i32)\n");
        }
        if (global.useConcat || global.useReadString || global.useMalloc) {
            output.append("declare i8* @malloc(i32)\n");
        }
        if (global.useConcat) {
            output.append("\n" +
                    "declare i8* @strcat(i8*, i8*)\n" +
                    "declare i8* @strcpy(i8*, i8*)\n" +
                    "declare i32 @strlen(i8*)\n" +
                    "define i8* @._concat(i8* %s1, i8* %s2) {\n" +
                    "%1 = call i32 @strlen(i8* %s1)\n" +
                    "%2 = call i32 @strlen(i8* %s2)\n" +
                    "%3 = add i32 %1, %2\n" +
                    "%4 = add i32 %3, 1\n" +
                    "%5 = call i8* @malloc(i32 %4)\n" +
                    "%6 = call i8* @strcpy(i8* %5, i8* %s1)\n" +
                    "%7 = call i8* @strcat(i8* %5, i8* %s2)\n" +
                    "ret i8* %7\n" +
                    "}\n");
            output.append("\n");
        }
        if (global.useReadInt) {

            output.append("@._dnl2 = internal constant [4 x i8] c\"%d\\0A\\00\"\n" +
                    "declare i32 @scanf(i8*, ...)\n" +
                    "define i32 @readInt() {\n" +
                    "entry:\t%res = alloca i32\n" +
                    "        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0\n" +
                    "\tcall i32 (i8*, ...) @scanf(i8* %t1, i32* %res)\n" +
                    "\t%t2 = load i32, i32* %res\n" +
                    "\tret i32 %t2\n" +
                    "}\n");
            output.append("\n");
        }

        if (global.useReadString) {
            output.append("\n" +
                    "declare i8* @gets(i8*)\n" +
                    "define i8* @readString() {\n" +
                    "entry:\n" +
                    "    %t1 = call i8* @malloc(i32 4096)\n" +
                    "    %t2 = call i8* @gets(i8* %t1)\n" +
                    "    ret i8* %t1\n" +
                    "}\n");
            output.append("\n");
        }

        if (global.useCompareString) {

            output.append("declare i32 @strcmp(i8*, i8*)\n" +
                    "define i32 @._strcmp(i8* %str1, i8* %str2) {\n" +
                    "       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)\n" +
                    "       ret i32 %t0\n" +
                    "}\n");
            output.append("\n");
        }

        Utils.writeToFile(output.toString(), outputFileName);
    }


    private static String withExtension(String fileName, String extension) {
        return withoutExtension(fileName) + "." + extension;
    }


    public static String withoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static void generateBytecode(String fileName) {
        String llFileName = Utils.withExtension(fileName, "ll");
        String outputFileName = Utils.withExtension(fileName, "bc");
        String inputFileName = Utils.withExtension(fileName, "input");

        String command = "llvm-as -o " + outputFileName + " " + llFileName;
        execSystemCommand(command, false);

        if (System.getProperty("run_bytecode") != null) {
            if (new File(inputFileName).exists()) {
                String lliCommand = "lli " + outputFileName + " < " + inputFileName;
                execSystemCommand(lliCommand, true);
            } else {
                String lliCommand = "lli " + outputFileName;
                execSystemCommand(lliCommand, true);
            }
        }
    }

    private static void execSystemCommand(String command, boolean printOutput) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            String s;
            if (printOutput) {
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(process.getInputStream()));
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }
            }

            StringBuilder error = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                error.append(s);
            }
            process.waitFor();
            process.exitValue();
            if (process.exitValue() != 0) {
                throw new RuntimeException(command + " failed with error: \n" + error);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while generating bytecode.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEscapedString(String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\22").replace("\n", "\\0A").replace("\t", "\\09").replace("\r", "\\0D").replace("\b", "\\08").replace("\f", "\\0C");
    }

    public
    static int getLLVMEscapedStringLength(String str) {
        int count = ((str.split(Pattern.quote("\\22"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\0A"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\09"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\0D"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\08"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\0C"), -1).length) - 1) * 2;
        count += ((str.split(Pattern.quote("\\\\"), -1).length) - 1);
        return str.length() - count + 1;
    }

    public static String toString(AddOp addop_) {
        if (addop_ instanceof Plus) {
            return "+";
        } else if (addop_ instanceof Minus) {
            return "-";
        } else {
            return "";
        }
    }

    public static String toString(MulOp mulop_) {
        if (mulop_ instanceof Times) {
            return "*";
        } else if (mulop_ instanceof Div) {
            return "/";
        } else if (mulop_ instanceof Mod) {
            return "%";
        } else {
            return "";
        }
    }

    public static Expr defaultValue(Type type) {
        return type.equals(new Int()) ? new ELitInt(0) : type.equals(new Bool()) ? new ELitFalse() : (type instanceof Class) ? new ENull(((Class) type).ident_) : (type instanceof Array) ? new ENullArr(((Array) type).type_) : new EString("");
    }

    public static int getLLVMTypeSize(Type type_) {
        if (type_ instanceof Int) {
            return 4;
        } else if (type_ instanceof Bool) {
            return 1;
        } else if (type_ instanceof Class) {
            return 8;
        } else if (type_ instanceof Array) {
            return 8;
        } else if (type_ instanceof Str) {
            return 8;
        } else {
            return 0;
        }
    }

    public static Expr nilExprReplace(Expr expr,Type type) {
        if (expr instanceof ENil) {
            if (type instanceof Class) {
                return new ENull(((Class) type).ident_);
            }
        }
        return expr;
    }
}
