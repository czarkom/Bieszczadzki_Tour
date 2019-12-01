import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainAlgorithm {

    DataReader reader;
    WarshallAlgorithm warshallAlgorithm;

    ArrayList<String> wishlist;
    HashMap<Integer, Place> smallerMap = new HashMap<>();
    int[] finalResult;
    int[][] warshallResult;
    private int totalTimeInMinutes = 0;
    private int[][] intermediateResult;
    private int[][] timeMatrix;
    private int[][] priceMatrix;
    private int[][] smallerPriceMatrix;

    private String startPlace;

    private int price = 0;


    public static void main(String[] args) throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        if (args.length == 3) program.reader = new DataReader(args[0], args[1], args[2]);
        else if (args.length == 2) program.reader = new DataReader(args[0], args[1]);
        else {
            throw new IllegalArgumentException("Proszę podać odpowiednie pliki jako dane wejściowe dla programu!");
        }
        program.runAlgorithm();
    }

    public void runAlgorithm() throws IOException {
        timeMatrix = reader.getTimeMatrix();
        priceMatrix = reader.getPriceMatrix();

        wishlist = reader.getWishArrayList();
        startPlace = reader.getStartPlace();
        if (wishlist.size() == 1 && wishlist.get(0).equals(startPlace))
            throw new IllegalArgumentException("Miejsce startowe nie może być jedynym miejscem przez które odbędzie się podróż. " +
                    "Zweryfikuj listę wybranych miejsc!");

        warshallAlgorithm = new WarshallAlgorithm(priceMatrix);
        warshallResult = warshallAlgorithm.findShortestPaths(timeMatrix);


        intermediateResult = createMapWithPlacesFromWishlist(warshallResult);


        finalResult = tsp(intermediateResult, findStartPlaceIndexInSmallerMap(smallerMap));
        writeResult();
    }

    private void writeResult() throws IOException {
        DataWriter writer = new DataWriter();
        price = calculateTotalPrice(finalResult);
        writer.writeTofile(finalResult, smallerMap, totalTimeInMinutes, price);
    }

    private int calculateTotalPrice(int[] result) {
        int total = 0;

        for (int i = 0; i < result.length - 2; i++) {
            total += smallerPriceMatrix[i][i + 1];
        }

        total += smallerPriceMatrix[result.length - 2][0];
        return total;
    }

    private boolean isDirectionPlaceOnWishlist(int x) {
        boolean flag = false;
        wishlist = reader.getWishArrayList();

        for (int i = 0; i < wishlist.size(); i++) {
            if (wishlist.get(i).equals(reader.getPlacesMapIntegersKeys().get(x).getId())) flag = true;
        }

        return flag;
    }

    private int[][] createMapWithPlacesFromWishlist(int[][] matrix) {
        int[][] result = new int[wishlist.size()][wishlist.size()];
        smallerPriceMatrix = new int[wishlist.size()][wishlist.size()];
        int k = 0;
        int l = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (isDirectionPlaceOnWishlist(i)) {
                for (int j = 0; j < matrix.length; j++) {
                    if (isDirectionPlaceOnWishlist(j)) {
                        result[l][k] = matrix[i][j];
                        smallerPriceMatrix[l][k] = priceMatrix[i][j];
                        k++;
                    } else {
                        continue;
                    }
                }
                smallerMap.put(l, reader.getPlacesMapIntegersKeys().get(i));
                l++;
            }
            k = 0;
        }
        return result;
    }

    private int[] tsp(int[][] times, int startIndex) {
        int[] visited = new int[times.length + 1];

        for (int i = 0; i < visited.length; i++) visited[i] = Integer.MAX_VALUE;

        visited[0] = startIndex;
        int nextline = startIndex;

        for (int i = 0; i < times.length; i++) {
            int lowestValue = Integer.MAX_VALUE;
            for (int j = 0; j < times.length; j++) {
                boolean isVisited = false;
                for (int k = 0; k < times.length; k++) {
                    if (j == visited[k]) isVisited = true;
                }
                if (!isVisited && lowestValue > times[nextline][j]) {
                    lowestValue = times[nextline][j];
                    visited[i + 1] = j;
                }
            }
            if (i == times.length - 1) {
                totalTimeInMinutes += times[nextline][startIndex];
            } else totalTimeInMinutes += lowestValue;

            if (i + 1 < times.length) {
                nextline = visited[i + 1];
            }
        }

        visited[times.length] = startIndex;
        System.out.println(Arrays.toString(visited));
        return visited;
    }

    private int findStartPlaceIndexInSmallerMap(HashMap<Integer, Place> map) {
        int index = -1;

        for (int i = 0; i < intermediateResult.length; i++) {
            try {
                if (map.get(i).getId().equals(startPlace)) {
                    index = i;
                    break;
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Niepoprawne ID miejsca startowego!");
            }
        }

        return index;
    }

    public int getPrice() {
        return price;
    }

    public int getTime() {
        return totalTimeInMinutes;
    }
}
