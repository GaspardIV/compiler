package latte.utils;

import latte.Absyn.Array;
import latte.Absyn.Bool;

public class Utils {
    //equalsT
    public static boolean bothArrays(java.lang.Object o1, java.lang.Object o2) {
        if (o1 == o2) return true;

        return o1 instanceof Array && o2 instanceof Array;
    }

    public static String toString(Array t) {
        return t.type_ + "array";
    }

    public static String toString(Bool t) {
        return "Bool";
    }

    public static String toString(latte.Absyn.Class t) {
        return t.ident_ + "class";
    }

    //int to string
    public static String toString(latte.Absyn.Int t) {
        return "Int";
    }
    //string to string
    public static String toString(latte.Absyn.Str t) {
        return "String";
    }
    //void to string
    public static String toString(latte.Absyn.Void t) {
        return "Void";
    }

    // plus to string
    public static String toString(latte.Absyn.Plus t) {
        return "+";
    }
    // minus to string
    public static String toString(latte.Absyn.Minus t) {
        return "-";
    }





}
