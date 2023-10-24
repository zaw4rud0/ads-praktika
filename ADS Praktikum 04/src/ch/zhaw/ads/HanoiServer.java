package ch.zhaw.ads;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HanoiServer implements CommandExecutor {

    private void moveDisk() {

    }

    @Override
    public String execute(String command) throws Exception {
        Input input = parseInput(command);

        if (input == null) {
            return null;
        }

        return "WIP";
    }

    private Input parseInput(String inputString) {
        String regex = "Bewegt (\\d+) Scheiben von Turm (\\w) zu Turm (\\w)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);

        Input input = null;

        if (matcher.find()) {
            try {
                int quantity = Integer.parseInt(matcher.group(1));
                String from = matcher.group(2);
                String to = matcher.group(3);

                input = new Input(quantity, from, to);
            } catch (NumberFormatException e) {
                System.err.println("Couldn't parse: " + matcher.group(1));
            }
        } else {
            System.err.println("Invalid input: " + inputString);
        }

        return input;
    }
}

record Input(int diskCount, String from, String to) {
}