package ch.zhaw.ads;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class RouteServer implements CommandExecutor {
    public RouteServer() {
    }

    /**
     * build the graph given a text file with the topology
     */
    public Graph<DijkstraNode, Edge> createGraph(String input) {
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);

        for (String line : input.split("\n")) {
            String[] parts = line.split(" ");

            String from = parts[0];
            String to = parts[1];
            double distance = Double.parseDouble(parts[2]);

            try {
                graph.addEdge(from, to, distance);
                graph.addEdge(to, from, distance);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return graph;
    }

    /**
     * Apply Dijkstra's algorithm on the given graph
     */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        DijkstraNode start = graph.findNode(from);
        DijkstraNode dest = graph.findNode(to);

        if (start == null || dest == null) {
            System.out.println("Source or destination node not found in the graph.");
            return;
        }

        for (DijkstraNode node : graph.getNodes()) {
            node.setMark(false);
            node.setDist(Double.MAX_VALUE);
            node.setPrev(null);
        }

        start.setDist(0);

        PriorityQueue<DijkstraNode> unvisitedNodes = new PriorityQueue<>();
        unvisitedNodes.add(start);

        while (!unvisitedNodes.isEmpty()) {
            DijkstraNode currentNode = unvisitedNodes.poll();
            if (currentNode.getMark()) continue;

            currentNode.setMark(true);

            for (Edge edge : currentNode.getEdges()) {
                DijkstraNode neighbor = (DijkstraNode) edge.getDest();

                if (!neighbor.getMark()) {
                    double newDist = currentNode.getDist() + edge.getWeight();
                    if (newDist < neighbor.getDist()) {
                        neighbor.setDist(newDist);
                        neighbor.setPrev(currentNode);
                        unvisitedNodes.add(neighbor);
                    }
                }
            }

            if (currentNode == dest) {
                break;
            }
        }
    }

    /**
     * find the route in the graph after applied dijkstra
     * the route should be returned with the start town first
     */
    public List<DijkstraNode> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode> route = new LinkedList<>();
        DijkstraNode town = graph.findNode(to);
        do {
            route.add(0, town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String input) {
        Graph<DijkstraNode, Edge> graph = createGraph(input);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuilder builder = new StringBuilder();
        for (DijkstraNode rt : route) builder.append(rt).append("\n");
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of("./src/ch.zhaw.ads/Swiss.txt"));
        RouteServer server = new RouteServer();
        System.out.println(server.execute(input));
    }
}