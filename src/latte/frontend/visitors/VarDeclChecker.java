package latte.frontend.visitors;

import latte.Absyn.NoInit;
import latte.Absyn.Type;
import latte.errors.SemanticError;
import latte.frontend.environment.Environment;

public class VarDeclChecker implements latte.Absyn.Item.Visitor<String, Environment> {
    Type itemType;

    public VarDeclChecker(Type itemType) {
        this.itemType = itemType;
    }

    public String visit(latte.Absyn.NoInit p, Environment arg) {
        if (arg.actContextContainsVar(p.ident_)) {
            throw new SemanticError.VariableAlreadyDeclared(p.line_num, p.ident_);
        } else {
            arg.addVariable(p.ident_, itemType);
        }
        return p.ident_;
    }

    public String visit(latte.Absyn.Init p, Environment arg) {
        NoInit itemNoInit = new NoInit(p.ident_);
        itemNoInit.line_num = p.line_num;
        this.visit(itemNoInit, arg);

        Type exprType = p.expr_.accept(new ExprChecker(), arg);

        if (!arg.areTypesEqualRegardingInheritance(exprType, itemType)) {
            throw new SemanticError.AssingingWrongType(p.line_num, itemType, exprType);
        }

        return p.ident_;
    }
}
