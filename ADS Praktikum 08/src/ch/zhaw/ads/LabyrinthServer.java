package ch.zhaw.ads;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LabyrinthServer implements CommandExecutor {
    ServerGraphics g = new ServerGraphics();

    public Graph<DijkstraNode, Edge> createGraph(String input) {
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);

        for (String line : input.split("\n")) {
            String[] parts = line.trim().split(" ");

            String from = parts[0];
            String to = parts[1];
            double distance = calculateDistance(from, to);

            try {
                graph.addEdge(from, to, distance);
                graph.addEdge(to, from, distance);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return graph;
    }

    private double calculateDistance(String from, String to) {
        String[] fromCoords = from.split("-");
        String[] toCoords = to.split("-");
        int x1 = Integer.parseInt(fromCoords[0]);
        int y1 = Integer.parseInt(fromCoords[1]);
        int x2 = Integer.parseInt(toCoords[0]);
        int y2 = Integer.parseInt(toCoords[1]);

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void drawLabyrinth(Graph<DijkstraNode, Edge> graph) {
        List<Edge> drawnEdges = new ArrayList<>();

        for (DijkstraNode node : graph.getNodes()) {
            for (Edge edge : node.edges) {
                if (!drawnEdges.contains(edge)) {
                    g.setColor(Color.GRAY);
                    g.drawPath(node.getName(), edge.getDest().getName(), false);
                    drawnEdges.add(edge);
                }
            }
        }
    }

    /**
     * Method to check whether a way from the current node to the goal exists using Dijkstra's algorithm.
     */
    private boolean search(Graph<DijkstraNode, Edge> graph, DijkstraNode start, DijkstraNode target) {
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();

        for (DijkstraNode node : graph.getNodes()) {
            node.setDist(Double.MAX_VALUE);
            node.setPrev(null);
            node.setMark(false);
        }

        start.setDist(0);
        queue.add(start);

        while (!queue.isEmpty()) {
            DijkstraNode current = queue.poll();
            current.setMark(true);

            if (current.equals(target)) {
                return true;
            }

            for (Edge edge : current.edges) {
                DijkstraNode neighbour = (DijkstraNode) edge.getDest();

                if (!neighbour.getMark()) {
                    double newDist = current.getDist() + edge.getWeight();
                    if (newDist < neighbour.getDist()) {
                        neighbour.setDist(newDist);
                        neighbour.setPrev(current);
                        queue.remove(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }

        return false;
    }

    public void drawRoute(Graph<DijkstraNode, Edge> graph, String startNode, String zielNode) {
        DijkstraNode start = graph.findNode(startNode);
        DijkstraNode target = graph.findNode(zielNode);

        if (start == null || target == null) {
            return;
        }

        if (search(graph, start, target)) {
            DijkstraNode current = target;
            while (current.getPrev() != null) {
                g.setColor(Color.RED);
                g.drawPath(current.getName(), current.getPrev().getName(), true);
                current = current.getPrev();
            }
        }
    }

    public String execute(String s) {
        Graph<DijkstraNode, Edge> graph = createGraph(s);
        drawLabyrinth(graph);
        drawRoute(graph, "0-6", "3-0");
        return g.getTrace();
    }
}