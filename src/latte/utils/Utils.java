package latte.utils;

import latte.Absyn.Array;
import latte.Absyn.Bool;
import latte.Absyn.Type;
import latte.Internal.Null;

import java.io.*;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class Utils {
    private static final String LLVM_TEMPLATE_RESOURCE = "llvm.template";

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

    public static String toLLVMString(Type actual) {
        if (actual instanceof Array) {
            return "";
        } else if (actual instanceof Bool) {
            return "Bool";
        } else if (actual instanceof latte.Absyn.Int) {
            return "i32";
        } else if (actual instanceof latte.Absyn.Str) {
            return "i8*";
        } else if (actual instanceof latte.Absyn.Void) {
            return "void";
        } else if (actual instanceof latte.Absyn.Class) {
            return ((latte.Absyn.Class) actual).ident_;
        } else if (actual instanceof Null) {
            return "Null";
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

    public static String loadTemplate(String templateResourceName) {
        InputStream stream = Utils.class.getClassLoader().getResourceAsStream(templateResourceName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String template = reader.lines().collect(Collectors.joining("\n"));
        return template;
    }

    public static void generateOutput(String fileName, String global) {
        String outputFileName = Utils.withExtension(fileName, "ll");
        String template = Utils.loadTemplate(LLVM_TEMPLATE_RESOURCE);
        String output = MessageFormat.format(template, global.toString());
        Utils.writeToFile(output, outputFileName);
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
        String command = "llvm-as -o " + outputFileName + " " + llFileName;
        execSystemCommand(command, false);

//            todo remove
        String lliCommand = "lli " + outputFileName;
        execSystemCommand(lliCommand, true);
    }

    private static void execSystemCommand(String command, boolean printOutput) {
        try {
            Process process = Runtime.getRuntime().exec(command);
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

        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while generating bytecode.");
            e.printStackTrace();
        }
    }
}
