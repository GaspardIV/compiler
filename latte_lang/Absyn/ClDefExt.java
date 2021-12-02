// File generated by the BNF Converter (bnfc 2.9.3).

package latte_lang.Absyn;

import frontend.Enviroment;
import frontend.SemanticError;

import java.util.HashSet;
import java.util.Set;

public class ClDefExt extends TopDef {
    public final String ident_1, ident_2;
    public final ClBlock clblock_;
    public boolean inheritanceInitialized;
    public int line_num, col_num, offset;

    public ClDefExt(String p1, String p2, ClBlock p3) {
        ident_1 = p1;
        ident_2 = p2;
        clblock_ = p3;
    }

    public <R, A> R accept(latte_lang.Absyn.TopDef.Visitor<R, A> v, A arg) throws SemanticError {
        return v.visit(this, arg);
    }

    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o instanceof latte_lang.Absyn.ClDefExt) {
            latte_lang.Absyn.ClDefExt x = (latte_lang.Absyn.ClDefExt) o;
            return this.ident_1.equals(x.ident_1) && this.ident_2.equals(x.ident_2) && this.clblock_.equals(x.clblock_);
        }
        return false;
    }

    public int hashCode() {
        return 37 * (37 * (this.ident_1.hashCode()) + this.ident_2.hashCode()) + this.clblock_.hashCode();
    }


    public void initInheristance(Enviroment avaibleClasses) throws SemanticError {
        if (this.inheritanceInitialized) {
            return;
        }
        Set<String> visited = new HashSet<>();
        String extendsCl = this.ident_2;
        visited.add(this.ident_1);
        while (extendsCl != null) {
            ClDefExt next = avaibleClasses.getClassDef(extendsCl);
            if (next == null) {
                throw new SemanticError(line_num, "Class not found");
            } else {
                extendsCl = next.ident_2;
            }

            if (visited.contains(next.ident_1)) {
                throw new SemanticError(line_num, "Inheritance loop");
            } else {
                visited.add(next.ident_1);
            }
        }
        if (this.ident_2 != null) {
            ClDefExt superClass = avaibleClasses.getClassDef(this.ident_2);
            superClass.initInheristance(avaibleClasses);
            ((ClBlk) this.clblock_).listclmember_.addAll(((ClBlk) superClass.clblock_).listclmember_);
            // todo zmienic to usunac stad, te pola powinny byc chyba w kompilatorze sprawdzane...
            // todo allow of duplications when overriding?
        }
        this.inheritanceInitialized = true;
    }
}
