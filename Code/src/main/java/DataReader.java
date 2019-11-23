import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
    private String dataFile;
    private String startPlace;
    private String wishlist;
    private String currentLine;

    private BufferedReader reader1;
    private BufferedReader reader2;

    public DataReader(String file1, String argument, String file2 ){
        this.dataFile = file1;
        this.startPlace = argument;
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
    }

    private void getWishList(){
        initiateWishListReader();
        advance(reader2);
    }

    private void getConfig(){
        getData();
        getWishList();
    }

    private void parseLineInDataFile(){
        String[] firstSplit = currentLine.split("|");
    }

    private void parseLineInWishListFile(){
        String[] firstSplit = currentLine.split("|");
    }
}
