package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCompetitor implements Comparable<MyCompetitor> {
    private final String name;
    private final String time;
    private int rank;

    public MyCompetitor(int rank, String name, String time) {
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

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return "" + rank + " " + name + " " + time;
    }

    @Override
    public int compareTo(MyCompetitor other) {
        int rankComparison = Integer.compare(this.rank, other.rank);
        if (rankComparison != 0) {
            return rankComparison;
        }
        long thisTime = parseTime(this.time);
        long otherTime = parseTime(other.time);
        return Long.compare(thisTime, otherTime);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + rank;
        hash = hash * 17 + name.hashCode();
        hash = hash * 31 + time.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyCompetitor other = (MyCompetitor) obj;
        return this.rank == other.rank
                && this.name.equals(other.name)
                && this.time.equals(other.time);
    }
}