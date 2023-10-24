package ch.zhaw.ads;

public class WellformedXmlServer implements CommandExecutor {

    public boolean checkWellformed (String xmlString) {
        Stack stack = new ListStack();

        int index = 0;

        while (index < xmlString.length()) {
            char currentChar = xmlString.charAt(index);

            if (currentChar == '<') {
                int endIndex = xmlString.indexOf('>', index);

                if (endIndex == -1) {
                    return false;
                }

                String tag = xmlString.substring(index + 1, endIndex);
                tag = removeAttributes(tag);

                if (tag.startsWith("/")) {
                    Object expectedTag = stack.pop();

                    if (expectedTag == null || !expectedTag.equals(tag.substring(1))) {
                        return false;
                    }
                } else if (!tag.endsWith("/")) { // Ignore self-closing tags
                    stack.push(tag);
                }

                index = endIndex + 1;
            } else {
                index++;
            }
        }

        return stack.isEmpty();
    }

    // Remove attributes, such as href and src, from the tag
    private String removeAttributes(String tag) {
        return tag.split(" ")[0];
    }

    public String execute(String command) {
        return String.valueOf(checkWellformed(command));
    }
}