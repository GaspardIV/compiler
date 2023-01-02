package latte.utils;

import latte.Absyn.Array;
import latte.Absyn.Bool;
import latte.Absyn.Type;
import latte.Internal.Null;
import latte.backend.program.global.Global;

import java.io.*;
import java.util.regex.Pattern;

public class Utils {

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
        }

        throw new RuntimeException("not implemented ????");
    }

    public static String getLLVMType(Type actual) {
        if (actual instanceof Array) {
            return "todo yo mama";
        } else if (actual instanceof Bool) {
            return "i1";
        } else if (actual instanceof latte.Absyn.Int) {
            return "i32";
        } else if (actual instanceof latte.Absyn.Str) {
            return "i8*";
        } else if (actual instanceof latte.Absyn.Void) {
            return "void";
        } else if (actual instanceof latte.Absyn.Class) {
            return ((latte.Absyn.Class) actual).ident_;
        } else if (actual instanceof Null) {
            return "null";
        }

        throw new RuntimeException("not implemented ????");
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
        output.append("\n");

        if (global.usePrintInt == 1) {
            output.append("@._dnl = internal constant [4 x i8] c\"%d\\0A\\00\"\n" +
                    "declare i32 @printf(i8*, ...)\n" +
                    "define void @printInt(i32 %x) {\n" +
                    "       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0\n" +
                    "       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)\n" +
                    "       ret void\n" +
                    "}\n");
        }
        if (global.usePrintString == 1) {
            output.append("declare i32 @puts(i8*)\n" +
                    "define void @printString(i8* %s) {\n" +
                    "entry:  call i32 @puts(i8* %s)\n" +
                    "\tret void\n" +
                    "}\n");
        }
        if (global.useError == 1) {
            output.append("declare void @exit(i32)\n" +
                    "define void @error() {\n" +
                    "entry:  call void @exit(i32 1)\n" +
                    "\tret void\n" +
                    "}\n");
        }
        if (global.useConcat == 1 || global.useReadString == 1) {
            output.append("declare i8* @malloc(i32)\n");
        }
        if (global.useConcat == 1) {
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
        }
        if (global.useReadInt == 1) {

            output.append("@._dnl2 = internal constant [4 x i8] c\"%d\\0A\\00\"\n" +
                    "declare i32 @scanf(i8*, ...)\n" +
                    "define i32 @readInt() {\n" +
                    "entry:\t%res = alloca i32\n" +
                    "        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0\n" +
                    "\tcall i32 (i8*, ...) @scanf(i8* %t1, i32* %res)\n" +
                    "\t%t2 = load i32, i32* %res\n" +
                    "\tret i32 %t2\n" +
                    "}\n");
        }

        if (global.useReadString == 1) {
            output.append("\n" +
                    "declare i8* @gets(i8*)\n" +
                    "define i8* @readString() {\n" +
                    "entry:\n" +
                    "    %t1 = call i8* @malloc(i32 4096)\n" +
                    "    %t2 = call i8* @gets(i8* %t1)\n" +
                    "    ret i8* %t1\n" +
                    "}\n");
        }

        if (global.useCompareString == 1) {
            output.append("declare i32 @strcmp(i8*, i8*)\n" +
                    "define i32 @compare(i8* %str1, i8* %str2) {\n" +
                    "       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)\n" +
                    "       ret i32 %t0\n" +
                    "}\n");
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

//            todo remove lines under !!!!!
        if (new File(inputFileName).exists()) {
            String lliCommand = "lli " + outputFileName + " < " + inputFileName;
            execSystemCommand(lliCommand, true);
        } else {
            String lliCommand = "lli " + outputFileName ;
            execSystemCommand(lliCommand, true);
        }
    }

    private static void execSystemCommand(String command, boolean printOutput) {
        try {
            Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", command}); // todo remove bash !!!
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));
//
//
            String s;
            if (printOutput) {
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(process.getInputStream()));
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }
            }
////
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
        int count = (( str.split( Pattern.quote("\\22" ), -1).length) - 1)*2;
        count += (( str.split( Pattern.quote("\\0A" ), -1).length) - 1) * 2;
        count += (( str.split( Pattern.quote("\\09" ), -1).length) - 1) * 2;
        count += (( str.split( Pattern.quote("\\0D" ), -1).length) - 1) * 2;
        count += (( str.split( Pattern.quote("\\08" ), -1).length) - 1) * 2;
        count += (( str.split( Pattern.quote("\\0C" ), -1).length) - 1) * 2;
        count += (( str.split( Pattern.quote("\\\\" ), -1).length) - 1) ;
        return str.length() - count + 1;
    }

    public static String removeNumber(String name) {
        //remove number from end of string
        return name.replaceAll("[0-9]+$", "");
    }
}
