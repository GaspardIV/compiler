package latte.backend.program.global;

import java.util.regex.Pattern;

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
        StringBuilder globalStrings = new StringBuilder();
        this.stringGlobals.forEach((name, value) ->globalStrings.append(globalStringToString(name, value)));

        return globalStrings.toString() + stringBuilder.toString();
//        return stringBuilder.toString();
    }

    private String globalStringToString(String k, String v) {

        String res =  k.replace("\\", "\\\\").replace("\"", "\\22").replace("\n", "\\0A").replace("\t", "\\09").replace("\r", "\\0D").replace("\b", "\\08").replace("\f", "\\0C");
        int count = (( res.split( Pattern.quote("\\22" ), -1).length) - 1)*2;
        count += (( res.split( Pattern.quote("\\0A" ), -1).length) - 1) * 2;
        count += (( res.split( Pattern.quote("\\09" ), -1).length) - 1) * 2;
        count += (( res.split( Pattern.quote("\\0D" ), -1).length) - 1) * 2;
        count += (( res.split( Pattern.quote("\\08" ), -1).length) - 1) * 2;
        count += (( res.split( Pattern.quote("\\0C" ), -1).length) - 1) * 2;


        return String.format("@.str.%s = internal constant [%d x i8] c\"%s\\00\"", v, res.length() + 1 - count, res);
    }

}
