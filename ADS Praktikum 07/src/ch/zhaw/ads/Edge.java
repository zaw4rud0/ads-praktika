package ch.zhaw.ads;

public class Edge {
    protected Node dest;
    protected double weight;

    public Edge() {
    }

    public Edge(Node dest, double weight) {
        this.dest = dest;
        this.weight = weight;
    }

    public void setDest(Node node) {
        this.dest = node;
    }

    public Node getDest() {
        return dest;
    }

    public void setWeight(double w) {
        this.weight = w;
    }

    public double getWeight() {
        return weight;
    }
}
