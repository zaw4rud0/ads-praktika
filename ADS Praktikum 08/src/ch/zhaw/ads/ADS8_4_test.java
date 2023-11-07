package ch.zhaw.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author K Rege
 * @version 1.01 2021/8/1
 */
public class ADS8_4_test {
    LabyrinthServer labyrinthServer;
    Graph<DijkstraNode, Edge> graph;

    @BeforeEach
    public void init() {
        String labyrinth = """
                0-6 4-6
                4-6 7-6
                7-6 9-6
                7-6 7-4
                7-4 6-4
                7-4 9-4
                9-4 9-1
                7-4 7-1
                7-1 5-1
                4-6 4-4
                4-4 4-3
                4-4 1-4
                1-4 1-1
                1-1 3-1
                3-1 3-2
                3-1 3-0
                """;
        labyrinthServer = new LabyrinthServer();
        graph = labyrinthServer.createGraph(labyrinth);
    }

    private void testEdge(String startName, String destName) {
        DijkstraNode node = graph.findNode(startName);
        for (Edge edge : node.getEdges()) {
            if (edge.getDest().getName().equals(destName)) {
                return;
            }
        }
        fail(startName + " not connected to " + destName);
    }

    @Test
    public void testCreateGraph() {
        testEdge("0-6", "4-6");
        testEdge("4-6", "0-6");
        testEdge("1-4", "1-1");
        testEdge("3-1", "3-0");
        testEdge("3-0", "3-1");
    }

    private void testPath(String trace, String path) {
        if (!trace.contains(path)) {
            fail(path + " not found");
        }
    }

    @Test
    public void testDrawLabyrinth() {
        labyrinthServer.drawLabyrinth(graph);
        String trace = ServerGraphics.instance().getTrace();
        testPath(trace, "<rect x=\"0.5455\" y=\"0.3636\" width=\"0.1818\" height=\"0.0909\"");
        testPath(trace, "<rect x=\"0.2727\" y=\"0.0909\" width=\"0.0909\" height=\"0.1818\"");
    }

    @Test
    public void testDrawRoute() {
        labyrinthServer.drawRoute(graph, "0-6", "3-0");
        String trace = ServerGraphics.instance().getTrace();
        testPath(trace, "<line x1=\"0.3182\" y1=\"0.0455\" x2=\"0.3182\" y2=\"0.1364\"/>");
        testPath(trace, "<line x1=\"0.1364\" y1=\"0.1364\" x2=\"0.3182\" y2=\"0.1364\"/>");
        testPath(trace, "<line x1=\"0.1364\" y1=\"0.1364\" x2=\"0.1364\" y2=\"0.4091\"/>");
        testPath(trace, "<line x1=\"0.1364\" y1=\"0.4091\" x2=\"0.4091\" y2=\"0.4091\"/>");
        testPath(trace, "<line x1=\"0.4091\" y1=\"0.4091\" x2=\"0.4091\" y2=\"0.5909\"/>");
        testPath(trace, "<line x1=\"0.0455\" y1=\"0.5909\" x2=\"0.4091\" y2=\"0.5909\"/>");
    }
}
