import java.util.*;

public class Graph {
    private final Set<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList; // Для Prim's

    public Graph(Set<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.adjacencyList = new HashMap<>();
        for (String v : vertices) {
            adjacencyList.put(v, new ArrayList<>());
        }
        for (Edge edge : edges) {
            // Граф неориентированный
            adjacencyList.get(edge.source).add(edge);
            adjacencyList.get(edge.destination).add(new Edge(edge.destination, edge.source, edge.weight));
        }
    }

    // Геттеры для доступа к данным
    public Set<String> getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public Map<String, List<Edge>> getAdjacencyList() { return adjacencyList; }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }
}