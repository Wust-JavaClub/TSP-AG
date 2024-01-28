import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author DURUII
 * @date 2024/1/28 13:50
 */

public class Problem {
    private final String benchmark;
    private int dimension;
    private int[][] graph;

    public Problem(String benchmark) {
        this.benchmark = benchmark;
        this.dimension = -1;
        loadParseTspFile();
    }

    public String getBenchmark() {
        return benchmark;
    }

    public int getDimension() {
        return dimension;
    }

    private void loadParseTspFile() {
        String filepath = "benchmark/" + benchmark + ".tsp";
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("DIMENSION")) {
                    this.dimension = Integer.parseInt(line.split(":")[1].trim());
                    graph = new int[this.dimension + 1][this.dimension + 1];
                    break;
                }
            }

            if (this.dimension == -1) {
                throw new IllegalArgumentException("File does not contain a 'DIMENSION' line.");
            }

            System.out.println("BENCHMARK: " + this.benchmark);

            // Load node coordinates
            while ((line = reader.readLine()) != null) {
                if (!line.equals("NODE_COORD_SECTION")) {
                    reader.readLine();
                    break;
                }
            }

            double[][] coordinates = new double[this.dimension + 1][2];

            for (int i = 1; i <= this.dimension; i++) {
                String[] words = reader.readLine().split(" ");
                List<String> parts = new ArrayList<>();
                for (String w : words) {
                    if (w != null && w.length() != 0) {
                        parts.add(w);
                    }
                }

                int nodeId = Integer.parseInt(parts.get(0));
                assert i == nodeId;
                double x = Double.parseDouble(parts.get(1));
                double y = Double.parseDouble(parts.get(2));
                coordinates[i][0] = x;
                coordinates[i][1] = y;
            }

            // Calculate distances
            for (int i = 1; i <= this.dimension; i++) {
                double x = coordinates[i][0];
                double y = coordinates[i][1];
                for (int j = 1; j < i; j++) {
                    double x2 = coordinates[j][0];
                    double y2 = coordinates[j][1];
                    int weight = (int) Math.round(Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2)));
                    graph[i][j] = weight;
                    graph[j][i] = weight;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getDistanceBetween(int i, int j) {
        assert i >= 1 && i <= this.dimension && j >= 1 && j <= this.dimension && i != j;
        return this.graph[i][j];
    }

    public int calculateLength(List<Integer> tour) {
        if (tour.size() != this.dimension) {
            throw new IllegalArgumentException("Tour Incomplete!");
        }

        int length = 0;
        for (int i = 0; i < tour.size(); i++) {
            int src = tour.get((i - 1 + tour.size()) % tour.size());
            if (src > this.dimension || src < 1) {
                throw new IllegalArgumentException("Node Illegal!");
            }
            int dst = tour.get(i);
            if (dst > this.dimension || dst < 1) {
                throw new IllegalArgumentException("Node Illegal!");
            }
            System.out.println(src + " " + dst + " " + getDistanceBetween(src, dst));
            length += this.getDistanceBetween(src, dst);
        }

        return length;
    }

}
