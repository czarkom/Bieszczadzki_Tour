import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainAlgorithmTest {

    @Test
    public void checkPriceForTarnica_smallFileWithoutWishlist() throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader("src\\test\\resources\\Tarnica_small.txt",  "ustrzyki_g");
        program.runAlgorithm();
        assertEquals(program.getPrice(), 10);
    }

    @Test
    public void checkTimeForTarnica_smallFileWithoutWishlist() throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader("src\\test\\resources\\Tarnica_small.txt", "ustrzyki_g");
        program.runAlgorithm();
        assertEquals(program.getTime(), 375);
    }

    @Test
    public void checkTimeForTarnica_smallFileWithOnlyTarnicaOnWishlist() throws IOException {
        MainAlgorithm program = new MainAlgorithm();
        program.reader = new DataReader("src\\test\\resources\\Tarnica_small.txt", "ustrzyki_g", "src\\test\\resources\\correctWishlistForTarnica_smallFile" );
        program.runAlgorithm();
        assertEquals(program.getTime(), 345);
    }
}