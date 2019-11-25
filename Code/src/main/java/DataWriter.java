import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataWriter {
    public void writeTofile(int[] result, HashMap<Integer, Place> map) throws IOException {
        try {
            BufferedWriter
                    writer = new BufferedWriter(new FileWriter("result.txt"));
            for (int i = 0; i < result.length; i++) {
                if (i == 0){
                    writer.write("Start place:" + map.get(result[i]).getName() + "\n");
                }else
                writer.write("-->" + map.get(result[i]).getName() + "\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
