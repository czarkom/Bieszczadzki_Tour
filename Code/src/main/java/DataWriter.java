import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataWriter {
    public void writeTofile(int[] result, HashMap<Integer, Place> map) throws IOException {
        try {
            BufferedWriter
                    writer = new BufferedWriter(new FileWriter("C:\\Maciej_general\\Maciej studia\\3 semestr\\Algorytmy i Struktury Danych\\Projekt Indywidualny\\2019Z_AISD_git_proj_ind_gr6\\Code\\Result\\result.txt"));
            for (int i = 0; i < result.length; i++) {
                if (i == 0){
                    writer.write(map.get(result[i]).getName() + "\n");
                }else
                writer.write("--> " + map.get(result[i]).getName() + "\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
