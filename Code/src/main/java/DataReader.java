import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataReader {
    private String dataFile;
    private String startPlace;
    private String wishlist;
    private String currentLine;

    private BufferedReader reader1;
    private BufferedReader reader2;

    private ArrayList<Place> listOfPlaces = new ArrayList<>();

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
            if (currentLine.equals("### Czas przejścia")){
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
            advance(reader1);
        }
    }

    private void getWishList(){
        initiateWishListReader();
        advance(reader2);

        while (this.currentLine != null){
            System.out.println(currentLine);
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
        System.out.println(Arrays.asList(firstSplit));
        System.out.println(firstSplit[0].trim());
        String firstPart = "" + firstSplit[0].trim().charAt(0);
        int placeNumericId = Integer.parseInt(firstPart) - 1;
        System.out.println(placeNumericId);

    }

    private void parseLineInDataFileTimes(){
        String[] firstSplit = currentLine.split("|");
    }

    private void parseLineInWishListFile(){
        String[] firstSplit = currentLine.split("|");
    }

    public static void main(String[] args){
        System.out.println(args[0]);
        DataReader reader = new DataReader(args[0], args[1]);
    }
}
