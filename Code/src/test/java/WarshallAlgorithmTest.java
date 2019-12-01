import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WarshallAlgorithmTest {

    @Test
    public void findShortestPathFrom0To1IndexTest() {
        int[][] priceMatrix = {
                {0, 2, 100},
                {0, 0, 5},
                {0, 200, 0}
        };

        WarshallAlgorithm warshallAlgorithm = new WarshallAlgorithm(priceMatrix);
        int[][] timeMatrix = {
                {0, 150, 5},
                {0, 0, 5},
                {0, 10, 0}
        };
        int[][] result = warshallAlgorithm.findShortestPaths(timeMatrix);
        assertEquals(result[0][1], 15);
        assertEquals(priceMatrix[0][1], 300);

    }
}