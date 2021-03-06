public class WarshallAlgorithm {
    private int[][] priceMatrix;

    public WarshallAlgorithm(int[][] matrix) {
        this.priceMatrix = matrix;
    }

    public int[][] findShortestPaths(int[][] graph) {
        int[][] result = graph.clone();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                for (int k = 0; k < result.length; k++) {
                    if (j == k) result[j][k] = DataReader.INFINITE_DISTANCE;
                    else if (result[j][i] + result[i][k] < result[j][k]) {
                        result[j][k] = result[j][i] + result[i][k];
                        priceMatrix[j][k] = priceMatrix[j][i] + priceMatrix[i][k];
                    }
                }
            }
        }
        return result;
    }

    public int[][] getPriceMatrix() {
        return priceMatrix;
    }
}
