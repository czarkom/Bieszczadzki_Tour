import java.io.IOException;

public class MainAlgorithm {
    public static void main(String[] args) throws IOException {
        int[] finalResult = {5,4,2,3,1};
        DataReader reader = new DataReader(args[0], args[1], args[2]);
        int[][] matrix = reader.getTimeMatrix();
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println(reader.getStartPlace() + "\n\n\n");
        WarshallAlgorithm warshall = new WarshallAlgorithm();
        int[][] result = warshall.findShortestPaths(matrix);
        for (int i = 0; i < result.length; i++){
            for (int j = 0; j < result.length; j++){
                System.out.print(result[i][j] + "\t");
            }
            System.out.println();
        }
        DataWriter writer = new DataWriter();
        writer.writeTofile(finalResult, reader.getPlacesMapForWriting());
    }
}
