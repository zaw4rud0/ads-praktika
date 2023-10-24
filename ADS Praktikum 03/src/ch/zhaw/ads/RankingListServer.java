package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RankingListServer implements CommandExecutor {

    /**
     * Task 2: Read input with competitors and parse it
     */
    public List<Competitor> createList(String rankingText) {
        List<Competitor> list = new ArrayList<>();

        // Regex to split a String by newlines and ignore empty lines
        String regex = "\\s*\\n+";
        String[] lines = rankingText.split(regex);

        for (String line : lines) {
            Competitor competitor = parseInput(line);
            list.add(competitor);
        }

        return list;
    }

    /**
     * Task 2: Parses the input to a Competitor object
     */
    private Competitor parseInput(String line) {
        String[] parts = line.split(";");
        return new Competitor(0, parts[0], parts[1]);
    }

    /**
     * Task 2: Method to print the list for checking purposes
     */
    public void printUnsortedList(List<Competitor> competitorList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Competitor competitor : competitorList) {
            stringBuilder.append(competitor.getName());
            stringBuilder.append(" ");
            stringBuilder.append(competitor.getTime());
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }

    /**
     * Task 3: Sort list by time and assign a rank to each competitor
     */
    public String createSortedText(List<Competitor> competitorList) {
        Collections.sort(competitorList);

        for (Competitor competitor : competitorList) {
            int rank = competitorList.indexOf(competitor) + 1;
            competitor.setRank(rank);
        }

        return joinListToString(competitorList);
    }

    /**
     * Task 4: Sort list by name
     */
    public String createNameList(List<Competitor> competitorList) {
        competitorList.sort(new AlphaComparatorCompetitor());
        return joinListToString(competitorList);
    }

    private String joinListToString(List<Competitor> list) {
        return list.stream().map(Competitor::toString).collect(Collectors.joining("\n"));
    }

    public String execute(String rankingList) {
        List<Competitor> competitorList = createList(rankingList);

        printUnsortedList(competitorList);

        return "Rangliste\n" + createSortedText(competitorList) + "\n\nNamensliste\n" + createNameList(competitorList);
    }
}