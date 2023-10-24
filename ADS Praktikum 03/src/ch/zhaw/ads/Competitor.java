package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Competitor implements Comparable<Competitor> {
    private final String name;
    private final String time;
    private int rank;

    public Competitor(int rank, String name, String time) {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    private static long parseTime(String s) {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;
    }

    public String toString() {
        return "" + rank + " " + name + " " + time;
    }

    /**
     * Task 1: Compare the time of two competitors
     */
    @Override
    public int compareTo(Competitor o) {
        return Long.compare(parseTime(time), parseTime(o.time));
    }

    /**
     * Task 1: Implement equals and hashCode methods
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Competitor other && name.equals(other.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, time, rank);
    }
}

/**
 * Task 4: Custom comparator to sort by the names of the competitors
 */
class AlphaComparatorCompetitor implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        String name1 = o1.getName();
        String name2 = o2.getName();

        // If both names are equal, compare by time instead
        if (name1.compareTo(name2) == 0) {
            return o1.compareTo(o2);
        } else {
            return name1.compareTo(name2);
        }
    }
}