import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataReader {
    private String dataFile;
    private String startPlace;
    private String wishlist;
    private String currentLine;
    private int placesCounter = 0;

    private static final int NO_CONNECTION = 100000;

    private BufferedReader reader1;
    private BufferedReader reader2;

    private HashMap<String, Place> placesMap = new HashMap<>();
    private int[][] timeMatrix;

    public DataReader(String file1, String file2 ){
        this.dataFile = file1;
        //this.startPlace = argument;
        this.wishlist = file2;
        getConfig();
    }

    private void initiateDataReader(){
        try{
            this.reader1 = new BufferedReader(new FileReader(dataFile));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initiateWishListReader(){
        try{
            this.reader2 = new BufferedReader(new FileReader(wishlist));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void advance(BufferedReader reader){
        try{
            this.currentLine = reader.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getData(){
        initiateDataReader();
        advance(reader1);

        while (this.currentLine != null){
            if (currentLine.equals("### Miejsca podróży") || currentLine.equals("")
            || currentLine.equals("Lp. | ID_miejsca | Nazwa miejsca | Opis miejsca |")){
                advance(reader1);
                continue;
            }
            else if (currentLine.equals("### Czas przejścia")){
                timeMatrix = new int[placesCounter][placesCounter];
                getTimes();
                break;
            }
            System.out.println(currentLine);
            parseLineInDataFilePlaces();
            advance(reader1);
        }
    }

    private void getTimes() {
        //System.out.println(currentLine);
        while (this.currentLine != null){
            if (currentLine.equals("### Czas przejścia")
            || currentLine.equals("Lp. | ID_miejsca_początkowego (S) | ID_miejsca_końcowego (E) | Czas S -> E | Czas E -> S | Jednorazowa opłata za przejście trasą (zł) |")){
                advance(reader1);
                continue;
            }
            System.out.println(currentLine);
            parseLineInDataFileTimes();
            advance(reader1);
        }
    }

    private void getWishList(){
        initiateWishListReader();
        advance(reader2);

        while (this.currentLine != null){
            System.out.println(currentLine);
            parseLineInWishListFile();
            advance(reader2);
        }
        System.out.println("Doszedłem");
    }

    private void getConfig(){
        getData();
        getWishList();
    }

    private void parseLineInDataFilePlaces(){
        Place place = new Place();
        String[] firstSplit = currentLine.split("\\|");
        //System.out.println(Arrays.asList(firstSplit));
        //System.out.println(firstSplit[0].trim());
        String firstPart = "" + firstSplit[0].trim().charAt(0);
        int placeNumericId = Integer.parseInt(firstPart) - 1;
        place.setNumericId(placeNumericId);
        String id = firstSplit[1].trim();
        place.setId(id);
        String name = firstSplit[2].trim();
        place.setName(name);
        String description = firstSplit[3].trim();
        place.setTypeOfPlace(description);
        placesMap.put(id, place );
        placesCounter++;

    }

    private void parseLineInDataFileTimes(){
        String[] firstSplit = currentLine.split("\\|");
        //System.out.println(Arrays.asList(firstSplit));
        String a = firstSplit[1].trim();
        String b = firstSplit[2].trim();
        String timeFromAToB = firstSplit[3].trim();
        String timeFromBToA = firstSplit[4].trim();
        int aNumericId = placesMap.get(a).getNumericId();
        int bNumericId = placesMap.get(b).getNumericId();

    }

    private void parseLineInWishListFile(){
        String[] firstSplit = currentLine.split("\\|");
    }

    public static void main(String[] args){
        System.out.println(args[0]);
        DataReader reader = new DataReader(args[0], args[1]);
        //System.out.println(reader.placesMap.get("B").getTypeOfPlace());
    }
}