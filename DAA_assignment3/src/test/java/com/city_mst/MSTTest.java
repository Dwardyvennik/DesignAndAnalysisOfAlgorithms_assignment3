import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MSTTest {

    // Вспомогательный метод для создания тестового графа (Small Graph)
    private Graph createTestGraph() {
        Set<String> vertices = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 3), new Edge("A", "C", 5),
                new Edge("B", "D", 8), new Edge("C", "D", 2),
                new Edge("D", "E", 1)
        );
        return new Graph(vertices, edges);
    }

    @Test
    void test_Correctness_TotalCostAndEdges() {
        Graph graph = createTestGraph();
        PrimsAlgorithm prims = new PrimsAlgorithm();
        KruskalsAlgorithm kruskals = new KruskalsAlgorithm();

        MSTResult primsResult = prims.findMST(graph);
        MSTResult kruskalsResult = kruskals.findMST(graph);

        // a. Correctness Test 1: Total Cost must be identical
        assertEquals(primsResult.totalCost, kruskalsResult.totalCost, 0.001,
                "Total MST cost must be the same for both algorithms.");

        // Ожидаемая стоимость (1+2+3+5 = 11)
        assertEquals(11.0, primsResult.totalCost, 0.001, "MST cost is incorrect.");

        // a. Correctness Test 2: Number of edges must equal V - 1 (для связного графа)
        int expectedEdges = graph.getVertexCount() - 1;
        assertEquals(expectedEdges, primsResult.mstEdges.size(), "Prim's MST must have V-1 edges.");
        assertEquals(expectedEdges, kruskalsResult.mstEdges.size(), "Kruskal's MST must have V-1 edges.");
    }

    @Test
    void test_Performance_NonNegativeTimeAndOps() {
        Graph graph = createTestGraph();
        PrimsAlgorithm prims = new PrimsAlgorithm();
        KruskalsAlgorithm kruskals = new KruskalsAlgorithm();

        MSTResult primsResult = prims.findMST(graph);
        MSTResult kruskalsResult = kruskals.findMST(graph);

        // b. Performance Test 1: Execution time is non-negative
        assertTrue(primsResult.executionTimeMillis >= 0);
        assertTrue(kruskalsResult.executionTimeMillis >= 0);

        // b. Performance Test 2: Operation counts are non-negative
        assertTrue(primsResult.operationCount >= 0);
        assertTrue(kruskalsResult.operationCount >= 0);
    }

    @Test
    void test_DisconnectedGraphHandling() {
        // Создаем граф из двух несвязанных компонент (A-B и C-D)
        Set<String> vertices = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
        List<Edge> edges = Arrays.asList(new Edge("A", "B", 10), new Edge("C", "D", 5));
        Graph disconnectedGraph = new Graph(vertices, edges);

        PrimsAlgorithm prims = new PrimsAlgorithm();
        MSTResult primsResult = prims.findMST(disconnectedGraph);

        // Для несвязного графа MST невозможно, количество ребер должно быть < V-1
        assertEquals(1, primsResult.mstEdges.size(), "Disconnected graph should not form a full MST (V-1).");

        // Добавьте аналогичный тест для Kruskal's
    }

    // ... Добавьте больше тестов для Medium и Large графов
}