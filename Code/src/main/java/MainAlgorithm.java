import java.io.IOException;

public class MainAlgorithm {
    int[] finalResult = {3,4,2,3,1};
    DataReader reader;


    public static void main(String[] args) throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader(args[0], args[1], args[2]);
        int[][] matrix = program.reader.getTimeMatrix();
        program.printMatrix(matrix);

        WarshallAlgorithm warshall = new WarshallAlgorithm();
        int[][] result = warshall.findShortestPaths(matrix);
        program.printMatrix(result);
        program.writeResult();


    }

    public void writeResult() throws IOException {
        DataWriter writer = new DataWriter();
        writer.writeTofile(finalResult, reader.getPlacesMapForWriting());
    }

    public void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
