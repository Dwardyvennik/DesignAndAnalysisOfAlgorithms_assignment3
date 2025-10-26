import java.util.List;

public class MSTResult {
    public final List<Edge> mstEdges;
    public final double totalCost;
    public final int vertexCount;
    public final int edgeCount;
    public final long operationCount; // Для сравнений, union/find, и т.п.
    public final long executionTimeMillis;

    public MSTResult(List<Edge> mstEdges, double totalCost, int vCount, int eCount, long ops, long time) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.vertexCount = vCount;
        this.edgeCount = eCount;
        this.operationCount = ops;
        this.executionTimeMillis = time;
    }

    // Добавьте геттеры для тестов и вывода...
}