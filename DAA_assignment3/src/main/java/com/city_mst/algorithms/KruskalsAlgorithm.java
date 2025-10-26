import java.util.*;

public class KruskalsAlgorithm {
    public MSTResult findMST(Graph graph) {
        long startTime = System.currentTimeMillis();
        long totalOperations = 0;

        // 1. Копирование и сортировка всех ребер
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges); // Использует Edge.compareTo

        totalOperations += sortedEdges.size() * Math.log(sortedEdges.size()) / Math.log(2); // Оценка операций сортировки

        DisjointSet dsu = new DisjointSet(graph.getVertices());
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        // 2. Итерация по отсортированным ребрам
        for (Edge edge : sortedEdges) {
            if (mstEdges.size() == graph.getVertexCount() - 1) break;

            // 3. Проверка на цикл и объединение
            if (dsu.union(edge.source, edge.destination)) {
                mstEdges.add(edge);
                totalCost += edge.weight;
            }
        }

        long endTime = System.currentTimeMillis();
        totalOperations += dsu.operationCount; // Добавляем операции Union-Find

        return new MSTResult(mstEdges, totalCost, graph.getVertexCount(), graph.getEdgeCount(),
                totalOperations, endTime - startTime);
    }
}