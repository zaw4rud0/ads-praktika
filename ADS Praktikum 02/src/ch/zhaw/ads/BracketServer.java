package ch.zhaw.ads;

import java.util.Map;

public class BracketServer implements CommandExecutor {

    private final Map<String, String> pairs = Map.of(
            "]", "[",
            ")", "(",
            "}", "{",
            ">", "<",
            "*/", "/*"
    );

    public boolean checkBrackets(String command) {
        Stack stack = new ListStack();

        for (int i = 0; i < command.length(); i++) {
            char currentChar = command.charAt(i);

            // /*
            if (currentChar == '/' && i < command.length() - 1 && command.charAt(i + 1) == '*') {
                stack.push("/*");
                i++;
            }
            // */
            else if (currentChar == '*' && i < command.length() - 1 && command.charAt(i + 1) == '/') {
                if (!stack.isEmpty() && stack.peek().equals("/*")) {
                    stack.pop();
                    i++;
                } else {
                    return false;
                }
            }

            else if (pairs.containsValue(String.valueOf(currentChar))) {
                stack.push(String.valueOf(currentChar));
            } else if (pairs.containsKey(String.valueOf(currentChar))) {
                if (stack.isEmpty() || !stack.pop().equals(pairs.get(String.valueOf(currentChar)))) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public String execute(String command) {
        return String.valueOf(checkBrackets(command));
    }
}