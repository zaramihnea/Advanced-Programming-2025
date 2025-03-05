import java.util.Arrays;

public class GraphGenerator {
    private int[][] adjacencyMatrix;
    private int n;
    private int k;
    private int m; // Number of edges
    private int maxDegree; // Maximum degree Δ(G)
    private int minDegree; // Minimum degree δ(G)
    private int[] degrees;
    private boolean isValid;

    public GraphGenerator(int n, int k) {
        this.n = n;
        this.k = k;
        this.adjacencyMatrix = new int[n][n];
        this.degrees = new int[n];

        this.isValid = generateGraph();

        if (isValid) {
            calculateMetrics();
        } else {
            System.out.println("Not possible");
            System.exit(0);
        }
    }

    private boolean generateGraph() {
        if (n < 2*k) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                adjacencyMatrix[i][j] = Math.random() > 0.5 ? 1 : 0;
                adjacencyMatrix[j][i] = adjacencyMatrix[i][j];
            }
        }

        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                adjacencyMatrix[i][j] = 1;
                adjacencyMatrix[j][i] = 1;
            }
        }

        for (int i = k; i < 2*k; i++) {
            for (int j = k; j < 2*k; j++) {
                if (i != j) {
                    adjacencyMatrix[i][j] = 0;
                    adjacencyMatrix[j][i] = 0;
                }
            }
        }

        return true;
    }

    private void calculateMetrics() {
        for (int i = 0; i < n; i++) {
            degrees[i] = 0;
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    degrees[i]++;
                }
            }
        }
        int sumOfDegrees = Arrays.stream(degrees).sum();
        m = sumOfDegrees / 2;
        maxDegree = Arrays.stream(degrees).max().orElse(0);
        minDegree = Arrays.stream(degrees).min().orElse(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (n > 30_000) {
            return "Matrix is too large to display.";
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(adjacencyMatrix[i][j] == 1 ? "■ " : "□ ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void displayMetrics() {
        System.out.println("Number of edges (m): " + m);
        System.out.println("Maximum degree (Δ(G)): " + maxDegree);
        System.out.println("Minimum degree (δ(G)): " + minDegree);

        int sumOfDegrees = Arrays.stream(degrees).sum();

        System.out.println("Sum of degrees: " + sumOfDegrees);
        System.out.println("2 * m: " + (2 * m));
        System.out.println("Verification: Sum of degrees " +
                (sumOfDegrees == 2 * m ? "equals" : "does not equal") + " 2 * m");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java GraphGenerator <n> <k>");
            System.out.println("  n: Matrix size (n x n)");
            System.out.println("  k: Size of clique and stable set");
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);
            int k = Integer.parseInt(args[1]);

            if (k > n) {
                System.out.println("Error: k cannot be greater than n");
                return;
            }

            long startTime = System.currentTimeMillis();

            GraphGenerator generator = new GraphGenerator(n, k);

            if (n <= 30_000) {
                System.out.println(generator);
            }

            generator.displayMetrics();

            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + " ms");

        } catch (NumberFormatException e) {
            System.out.println("Error: Please provide valid integers for n and k");
        } catch (OutOfMemoryError e) {
            System.out.println("Error: Out of memory. Try using JVM options: -Xms4G -Xmx4G");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}