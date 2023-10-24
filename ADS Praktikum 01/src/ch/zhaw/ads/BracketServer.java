package ch.zhaw.ads;

import java.util.Map;

public class BracketServer implements CommandExecutor {

    private final Map<String, String> pairs = Map.of("]", "[", ")", "(", "}", "{");

    public boolean checkBrackets(String command) {
        Stack stack = new ListStack();

        for (String s : command.split("")) {
            if (pairs.containsValue(s)) {
                stack.push(s);
            } else if (pairs.containsKey(s)) {
                Object o = stack.pop();
                if (o == null || !o.equals(pairs.get(s))) {
                    return false;
                }
            }
        }
        return true;
    }

    public String execute(String command) {
        return String.valueOf(checkBrackets(command));
    }
}