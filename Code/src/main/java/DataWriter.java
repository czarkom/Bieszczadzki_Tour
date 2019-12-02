import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataWriter {
    public void writeTofile(int[] result, HashMap<Integer, Place> map, int totalTime, int price) throws IOException {
        try {
            BufferedWriter
                    writer = new BufferedWriter(new FileWriter("output\\result.txt"));
            for (int i = 0; i < result.length; i++) {
                if (i == 0) {
                    writer.write(map.get(result[i]).getName() + "\n");
                } else
                    writer.write("--> " + map.get(result[i]).getName() + "\n");
            }
            int hours = totalTime / 60;
            int minutes = totalTime % 60;
            writer.write("Czas: " + hours + " godzin " + minutes + " minut\n");
            writer.write("Koszt: " + price + " zł");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
