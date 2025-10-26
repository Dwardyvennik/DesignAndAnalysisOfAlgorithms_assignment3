public class Edge implements Comparable<Edge> {
    public final String source;
    public final String destination;
    public final double weight;

    public Edge(String source, String destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Необходимо для сортировки в Kruskal's и PriorityQueue в Prim's
    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "(" + source + " -- " + destination + ", Cost: " + weight + ")";
    }
}