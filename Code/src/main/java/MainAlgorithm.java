import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainAlgorithm {
    int[] finalResult = {1,2,3};
    DataReader reader;
    int[][] warshallResult;
    ArrayList<String> wishlist;
    HashMap<Integer, Place> smallerMap = new HashMap<>();
    private int[][] intermediateResult;
    int totalTimeInMinutes = 0;
    int[][] timeMatrix;
    String startPlace;


    public static void main(String[] args) throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader(args[0], args[1], args[2]);
        program.runAlgorithm();
        //int[][] matrix = program.reader.getTimeMatrix();
        //program.printMatrix(matrix);
        //program.wishlist = program.reader.getWishArrayList();

        //WarshallAlgorithm warshall = new WarshallAlgorithm();
        //program.warshallResult = warshall.findShortestPaths(matrix);
        //program.printMatrix(program.warshallResult);

        //System.out.println(program.reader.getWishArrayList());
        //System.out.println(program.isDirectionPlaceOnWishlist(0));
        //program.intermediateResult = program.createMapWithPlacesFromWishlist(program.warshallResult);
        //program.printMatrix(program.intermediateResult);
//      System.out.println(program.smallerMap.get(3).getName());
        //program.finalResult = program.tsp(program.intermediateResult, 1);
        //program.writeResult();

    }

    public void runAlgorithm() throws IOException {
        timeMatrix = reader.getTimeMatrix();
        printMatrix(timeMatrix);
        wishlist = reader.getWishArrayList();
        startPlace = reader.getStartPlace();
        WarshallAlgorithm warshall = new WarshallAlgorithm();
        warshallResult = warshall.findShortestPaths(timeMatrix);
        printMatrix(warshallResult);

        System.out.println(reader.getWishArrayList());

        intermediateResult = createMapWithPlacesFromWishlist(warshallResult);
        printMatrix(intermediateResult);

        finalResult = tsp(intermediateResult, findStartPlaceIndexInSmallerMap(smallerMap));
        writeResult();
    }

    public void writeResult() throws IOException {
        DataWriter writer = new DataWriter();
        writer.writeTofile(finalResult, smallerMap, totalTimeInMinutes);
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
        //wishlist = reader.getWishArrayList();
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
                l++;
            }
            k = 0;
        }
        return result;
    }

    private int[] tsp(int[][] times, int startIndex){
        int[] visited = new int[times.length + 1];
        for(int i = 0; i < visited.length; i++) visited[i] = Integer.MAX_VALUE;
        visited[0] = startIndex;
        int nextline = startIndex;
        for (int i = 0; i < times.length; i++){
            int lowestValue = Integer.MAX_VALUE;
            for (int j = 0; j < times.length; j++){
                boolean isVisited = false;
                for( int k = 0; k < times.length; k++){
                    if (j == visited[k]) isVisited = true;
                }
                if (!isVisited && lowestValue > times[nextline][j]){
                    lowestValue = times[nextline][j];
                    visited[i + 1] = j;
                }
            }
            if( i == times.length - 1){
                totalTimeInMinutes += times[nextline][startIndex];
            }else totalTimeInMinutes += lowestValue;
            System.out.println(visited[i]);
            System.out.println(totalTimeInMinutes);
            if(i + 1 < times.length ) {
                nextline = visited[i + 1];
            }
        }
        visited[times.length] = startIndex;
        System.out.println(Arrays.toString(visited));
        return visited;
    }

    public int findStartPlaceIndexInSmallerMap(HashMap<Integer, Place> map){
        int index = -1;
        for (int i = 0; i < intermediateResult.length; i++){
            if(map.get(i).getId().equals(startPlace)){
                index = i;
                break;
            }
        }
        return index;
    }
}
