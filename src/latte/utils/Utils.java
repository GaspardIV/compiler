package latte.utils;

import latte.Absyn.Array;
import latte.Absyn.Bool;
import latte.Absyn.Type;
import latte.Internal.Null;

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
}
