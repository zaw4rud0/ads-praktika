package ch.zhaw.ads;

import java.util.concurrent.atomic.AtomicInteger;

public class RankingTreeServer implements CommandExecutor {

    public Tree<Competitor> createTree(String rankingText) {
        SortedBinaryTree<Competitor> tree = new SortedBinaryTree<>();

        String[] lines = rankingText.split("\n");
        AtomicInteger rank = new AtomicInteger(1);

        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length == 2) {
                String name = parts[0];
                String time = parts[1];
                Competitor competitor = new Competitor(rank.getAndIncrement(), name, time);
                tree.add(competitor);
            }
        }

        return tree;
    }

    public String createSortedText(Tree<Competitor> competitorTree) {
        StringBuilder sb = new StringBuilder();

        // Inorder traversal to print competitors in sorted order
        AtomicInteger rank = new AtomicInteger(1);
        competitorTree.traversal().inorder(competitor -> {
            competitor.setRank(rank.getAndIncrement());
            sb.append(competitor).append("\n");
        });

        return sb.toString();
    }

    public String execute(String rankingList) {
        Tree<Competitor> competitorTree = createTree(rankingList);
        return "Rangliste (Tree)\n" + createSortedText(competitorTree);
    }

    public static void main(String[] args) {
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
        RankingTreeServer server = new RankingTreeServer();
        System.out.println(server.execute(rangliste));
    }
}