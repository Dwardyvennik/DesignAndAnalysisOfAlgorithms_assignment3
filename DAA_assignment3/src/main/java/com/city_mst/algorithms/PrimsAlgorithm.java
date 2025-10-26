import java.util.*;

public class PrimsAlgorithm {
    public MSTResult findMST(Graph graph) {
        long startTime = System.currentTimeMillis();
        long operationCount = 0;

        // PriorityQueue для хранения ребер, отсортированных по весу
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Set<String> inMST = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        if (graph.getVertices().isEmpty()) {
            return new MSTResult(mstEdges, 0, 0, 0, 0, 0);
        }

        // 1. Начало с произвольной вершины
        String startVertex = graph.getVertices().iterator().next();
        inMST.add(startVertex);

        // Добавляем все ребра, исходящие из начальной вершины
        for (Edge edge : graph.getAdjacencyList().get(startVertex)) {
            pq.add(edge);
            operationCount++; // Считаем добавление в PQ
        }

        // 2. Основной цикл
        while (!pq.isEmpty() && inMST.size() < graph.getVertexCount()) {
            Edge minEdge = pq.poll();
            operationCount++; // Считаем извлечение из PQ (сравнение)

            String nextVertex = minEdge.destination;

            if (inMST.contains(nextVertex)) {
                continue;
            }

            // Добавляем новую вершину и ребро в MST
            inMST.add(nextVertex);
            mstEdges.add(minEdge);
            totalCost += minEdge.weight;

            // 3. Добавляем новые ребра, исходящие из nextVertex
            for (Edge edge : graph.getAdjacencyList().get(nextVertex)) {
                if (!inMST.contains(edge.destination)) {
                    pq.add(edge);
                    operationCount++; // Считаем добавление в PQ
                }
            }
        }

        long endTime = System.currentTimeMillis();
        // Внимание: Здесь operationCount - это упрощенный подсчет. 
        // В реальном анализе нужно считать сравнения в куче.
        return new MSTResult(mstEdges, totalCost, graph.getVertexCount(), graph.getEdgeCount(),
                operationCount, endTime - startTime);
    }
}