import org.junit.Test;

public class DataReaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForInsertingAlreadyExistingIDInDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\alreadyExistingIDDataFile", "D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForInsertingNonExistingIDTimesDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\nonExistingIDTimesDataFile", "D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForInsertingNonExistingIDOnWishlist() {
        DataReader dataReader = new DataReader("src\\test\\resources\\Bieszczady.txt", "D", "src\\test\\resources\\nonExistingIDWishlist");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWhitespaceErrorWishlist() {
        DataReader dataReader = new DataReader("src\\test\\resources\\Bieszczady.txt", "A", "src\\test\\resources\\whitespaceErrorWishlist");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWishlistWrongFormatting() {
        DataReader dataReader = new DataReader("src\\test\\resources\\Bieszczady.txt", "A", "src\\test\\resources\\wishlistWrongFormatting");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongFirstLineFormatTimesDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongFirstLineFormatTimesDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongFirstLineWishlist() {
        DataReader dataReader = new DataReader("src\\test\\resources\\Bieszczady.txt", "A", "src\\test\\resources\\wrongFirstLineWishlist");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongIDFormatDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongIDFormatDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongLineFormatDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongLineFormatDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongLineFormatTimesDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongLineFormatTimesDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongNumberOrderDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongNumberOrderDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongSecondLineFormatTimesDataFile() {
        DataReader dataReader = new DataReader("src\\test\\resources\\wrongSecondLineFormatTimesDataFile", "A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkExceptionForWrongSecondLineWishlist() {
        DataReader dataReader = new DataReader("src\\test\\resources\\Bieszczady.txt", "A", "src\\test\\resources\\wrongSecondLineWishlist");
    }
}