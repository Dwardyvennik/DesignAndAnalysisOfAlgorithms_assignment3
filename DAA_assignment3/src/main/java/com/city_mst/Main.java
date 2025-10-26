package com.city_mst;

import com.city_mst.algorithms.KruskalsAlgorithm;
import com.city_mst.algorithms.PrimsAlgorithm;
import com.city_mst.models.Graph;
import com.city_mst.models.MSTResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

// Вспомогательный класс для парсинга JSON
class InputData {
    public List<Map<String, Object>> datasets;
}

public class Main {
    private static final String INPUT_FILE = "src/main/resources/assign_3_input.json";
    private static final String OUTPUT_FILE = "assign_3_output.json";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> allResults = new ArrayList<>();

        try {
            // 1. Чтение входных данных
            InputData input = mapper.readValue(new File(INPUT_FILE), InputData.class);

            PrimsAlgorithm prims = new PrimsAlgorithm();
            KruskalsAlgorithm kruskals = new KruskalsAlgorithm();

            // 2. Итерация по наборам данных
            for (Map<String, Object> dataset : input.datasets) {
                String name = (String) dataset.get("name");

                // Создание объекта Graph
                Graph graph = buildGraphFromDataset(dataset);

                System.out.println("Processing: " + name + " (V=" + graph.getVertexCount() + ", E=" + graph.getEdgeCount() + ")");

                // 3. Запуск и запись результатов Prim's
                MSTResult primsResult = prims.findMST(graph);
                allResults.add(formatResult(name, "Prim's", primsResult));

                // 4. Запуск и запись результатов Kruskal's
                MSTResult kruskalsResult = kruskals.findMST(graph);
                allResults.add(formatResult(name, "Kruskal's", kruskalsResult));
            }

            // 5. Запись всех результатов в выходной JSON
            Map<String, List<Map<String, Object>>> finalOutput = new HashMap<>();
            finalOutput.put("test_results", allResults);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(OUTPUT_FILE), finalOutput);

            System.out.println("\n✅ Results successfully written to " + OUTPUT_FILE);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error processing files. Check if " + INPUT_FILE + " exists and is correctly formatted.");
        }
    }

    // --- Вспомогательные методы ---
    private static Graph buildGraphFromDataset(Map<String, Object> dataset) {
        Set<String> vertices = new HashSet<>((List<String>) dataset.get("vertices"));
        List<Map<String, Object>> edgeMaps = (List<Map<String, Object>>) dataset.get("edges");
        List<com.city_mst.models.Edge> edges = new ArrayList<>();

        for (Map<String, Object> map : edgeMaps) {
            String src = (String) map.get("source");
            String dest = (String) map.get("destination");
            // Jackson парсит числа как Integer или Double, нужно унифицировать
            Number costNum = (Number) map.get("cost");
            double cost = costNum.doubleValue();
            edges.add(new com.city_mst.models.Edge(src, dest, cost));
        }
        return new Graph(vertices, edges);
    }

    private static Map<String, Object> formatResult(String datasetName, String algoName, MSTResult result) {
        Map<String, Object> map = new HashMap<>();
        map.put("dataset", datasetName);
        map.put("algorithm", algoName);
        map.put("v_count", result.vertexCount);
        map.put("e_count", result.edgeCount);
        map.put("total_cost", String.format("%.2f", result.totalCost));
        map.put("execution_time_ms", result.executionTimeMillis);
        map.put("operation_count", result.operationCount);
        map.put("mst_edges", result.mstEdges.stream().map(Object::toString).toList());
        return map;
    }
}