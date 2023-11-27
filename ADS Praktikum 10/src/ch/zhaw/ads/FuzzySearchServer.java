package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzySearchServer implements CommandExecutor {

    protected static final List<String> names = new ArrayList<>(); // List of all names
    protected static final Map<String, List<Integer>> trigrams = new HashMap<>(); // List of all Trigrams
    protected static final Map<Integer, Integer> counts = new HashMap<>(); // Key: index of

    /**
     * Loads unique names from a given string into the names list.
     * Each name in the input string should be separated by a newline and a semicolon.
     *
     * @param nameString The string containing names to load.
     */
    public static void loadNames(String nameString) {
        String[] entries = nameString.split("\n");
        for (String entry : entries) {
            String name = entry.split(";")[0].trim();
            if (!names.contains(name)) {
                names.add(name);
            }
        }
    }

    /**
     * Adds a single trigram to the trigrams map. If the trigram already exists,
     * it updates the list of name indexes associated with this trigram.
     *
     * @param nameIdx The index of the name in the names list.
     * @param trig    The trigram to be added.
     */
    public static void addToTrigrams(int nameIdx, String trig) {
        List<Integer> indices = trigrams.getOrDefault(trig, new ArrayList<>());
        if (!indices.contains(nameIdx)) {
            indices.add(nameIdx);
        }
        trigrams.put(trig, indices);
    }


    // Works better for flipped and short names if " " added and lowercase
    private static String normalize(String name) {
        return " " + name.toLowerCase().trim() + " ";
    }

    /**
     * Constructs a list of trigrams for a given name. The name is first normalized
     * by converting to lowercase and trimming spaces.
     *
     * @param name The name for which to construct trigrams.
     * @return A list of trigrams for the given name.
     */
    public static List<String> trigramForName(String name) {
        name = normalize(name);
        List<String> trigs = new ArrayList<>();
        for (int i = 0; i < name.length() - 2; i++) {
            trigs.add(name.substring(i, i + 3));
        }
        return trigs;
    }

    public static void constructTrigramIndex(List<String> names) {
        for (int nameIdx = 0; nameIdx < names.size(); nameIdx++) {
            List<String> trigs = trigramForName(names.get(nameIdx));
            for (String trig : trigs) {
                addToTrigrams(nameIdx, trig);
            }
        }
    }

    private static void incCount(int cntIdx) {
        Integer c = counts.get(cntIdx);
        c = (c == null) ? 1 : c + 1;
        counts.put(cntIdx, c);
    }

    /**
     * Finds the index of the name in the names list that has the most corresponding
     * trigrams to the given name. If no trigram/name matches, returns -1.
     *
     * @param name The name to search for in the trigram index.
     * @return The index of the best-matching name, or -1 if no match is found.
     */
    public static int findIdx(String name) {
        counts.clear();
        int maxIdx = -1;
        int maxCount = 0;

        List<String> searchTrigrams = trigramForName(name);
        for (String trig : searchTrigrams) {
            List<Integer> nameIndices = trigrams.get(trig);
            if (nameIndices != null) {
                for (Integer idx : nameIndices) {
                    incCount(idx);
                    int count = counts.get(idx);
                    if (count > maxCount) {
                        maxCount = count;
                        maxIdx = idx;
                    }
                }
            }
        }
        return maxIdx;
    }

    // finde Namen gebe "" zurück wenn gefundener Name nicht grösser als verlangter score ist.
    public static String find(String searchName, int scoreRequired) {
        int found = findIdx(searchName);
        String foundName = "";
        if (found >= 0 && score(found) >= scoreRequired) {
            foundName = names.get(found);
        }
        return foundName;
    }

    private static int score(int found) {
        String foundName = names.get(found);
        return (int) (100.0 * Math.min(counts.get(found), foundName.length()) / foundName.length());
    }

    public String execute(String searchName) {
        int found = findIdx(searchName);
        if (found >= 0) {
            int score = score(found);
            String foundName = names.get(found);
            return searchName + " -> " + foundName + " " + score + "%\n";
        } else {
            return "nothing found\n";
        }
    }

    public static void main(String[] args) {
        FuzzySearchServer fs = new FuzzySearchServer();
        System.out.println(fs.execute("Kiptum Daniel"));
        System.out.println(fs.execute("Daniel Kiptum"));
        System.out.println(fs.execute("Kip Dan"));
        System.out.println(fs.execute("Dan Kip"));
    }

    static {
        String rangliste = "Mueller Stefan;02:31:14\n" +
                "Marti Adrian;02:30:09\n" +
                "Kiptum Daniel;02:11:31\n" +
                "Ancay Tarcis;02:20:02\n" +
                "Kreibuhl Christian;02:21:47\n" +
                "Ott Michael;02:33:48\n" +
                "Menzi Christoph;02:27:26\n" +
                "Oliver Ruben;02:32:12\n" +
                "Elmer Beat;02:33:53\n" +
                "Kuehni Martin;02:33:36\n";
        loadNames(rangliste);
        constructTrigramIndex(names);
    }
}
