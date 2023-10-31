package ch.zhaw.ads;

/**
 * DijkstraNode represents a node in a graph used for Dijkstra's algorithm.
 * It extends the base Node class and includes additional fields and methods
 * required for Dijkstra's algorithm.
 */
public class DijkstraNode extends Node implements Comparable<DijkstraNode> {
    boolean mark;
    DijkstraNode prev;
    double dist;

    public DijkstraNode() {
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public void setMark(boolean m) {
        mark = m;
    }

    public boolean getMark() {
        return mark;
    }

    public void setPrev(DijkstraNode p) {
        prev = p;
    }

    public DijkstraNode getPrev() {
        return prev;
    }

    public String toString() {
        return getName() + " " + getDist();
    }

    public int compareTo(DijkstraNode n) {
        return (int) (dist - n.dist);
    }
}