import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();
    public long operationCount = 0; // Счетчик операций find/union

    public DisjointSet(Set<String> vertices) {
        for (String v : vertices) {
            parent.put(v, v); // Каждая вершина - родитель сама себе
        }
    }

    // Find с оптимизацией сжатия пути (Path Compression)
    public String find(String i) {
        operationCount++; // Считаем каждую операцию find
        if (parent.get(i).equals(i)) {
            return i;
        }
        String root = find(parent.get(i));
        parent.put(i, root); // Path Compression
        return root;
    }

    // Union (без оптимизации по рангу/размеру для простоты, но лучше добавить!)
    public boolean union(String i, String j) {
        String rootI = find(i);
        String rootJ = find(j);

        if (!rootI.equals(rootJ)) {
            parent.put(rootI, rootJ);
            return true; // Union произошел
        }
        return false; // Цикл найден
    }
}