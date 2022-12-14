package latte.backend.program.global;

public class Global extends Scope{

    public Global(String contextName, Scope parent) {
        super(contextName, parent);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : this.functions.keySet()) {
            Function function = this.functions.get(name);
            if (function.hasParent()) {
                stringBuilder.append(function.toString());
            }
        }
        return stringBuilder.toString();
    }
}
