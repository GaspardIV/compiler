package latte.utils;

import latte.Absyn.Array;
import latte.Absyn.Bool;
import latte.Absyn.Type;

public class Utils {
    //equalsT
    public static boolean bothArrays(java.lang.Object o1, java.lang.Object o2) {
        if (o1 == o2) return true;

        return o1 instanceof Array && o2 instanceof Array;
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
        } else if (actual instanceof latte.Absyn.Null) {
            return "Null";
        }

        throw new RuntimeException("not implemented ????");
    }
}
