import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainAlgorithm {
    int[] finalResult = {3,4,2,3,1};
    DataReader reader;
    int[][] warshallResult;
    ArrayList<String> wishlist;
    HashMap<Integer, Place> smallerMap = new HashMap<>();


    public static void main(String[] args) throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader(args[0], args[1], args[2]);
        int[][] matrix = program.reader.getTimeMatrix();
        program.printMatrix(matrix);

        WarshallAlgorithm warshall = new WarshallAlgorithm();
        program.warshallResult = warshall.findShortestPaths(matrix);
        program.printMatrix(program.warshallResult);
        program.writeResult();
        System.out.println(program.reader.getWishArrayList());
        System.out.println(program.isDirectionPlaceOnWishlist(0));
        program.printMatrix(program.createMapWithPlacesFromWishlist(program.warshallResult));
        System.out.println(program.smallerMap.get(3).getName());

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

    private boolean isDirectionPlaceOnWishlist(int x){
        boolean flag = false;
        wishlist = reader.getWishArrayList();
        for(int i = 0; i < wishlist.size(); i++){
            if(wishlist.get(i).equals(reader.getPlacesMapForWriting().get(x).getId())) flag = true;
        }
        return flag;
    }

    private int[][] createMapWithPlacesFromWishlist(int[][] matrix){
        int[][] result = new int[wishlist.size()][wishlist.size()];
        int k = 0;
        int l = 0;

        for(int i = 0; i < matrix.length; i++ ) {
            if(isDirectionPlaceOnWishlist(i)){
                for (int j = 0; j < matrix.length; j++) {
                    if (isDirectionPlaceOnWishlist(j)) {
                        result[l][k] = matrix[i][j];
                        k++;
                    } else {
                        continue;
                    }
                }
                smallerMap.put(l, reader.getPlacesMapForWriting().get(i));
                //smallerMap.put(l, reader.getPlacesMap().get((reader.getPlacesMapForWriting().get(l).getId())));
                l++;
            }
            k = 0;
        }
        return result;
    }
}
