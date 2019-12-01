import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataReader {
    public static final int INFINITE_DISTANCE = 999999;
    private String dataFile;
    private String startPlace;
    private String wishlist;
    private String currentLine;
    private int placesCounter = 0;
    private int lineNumber = 0;
    private BufferedReader reader1;
    private BufferedReader reader2;

    private HashMap<String, Place> placesMap = new HashMap<>();
    private HashMap<Integer, Place> placesMapIntegersKeys = new HashMap<>();


    private int[][] timeMatrix;
    private int[][] priceMatrix;

    private ArrayList<String> wishArrayList = new ArrayList<>();

    public DataReader(String file1, String startPlace, String file2) {
        this.dataFile = file1;
        this.startPlace = startPlace;
        this.wishlist = file2;
        getConfig();
    }

    public DataReader(String file1, String startPlace) {
        this.dataFile = file1;
        this.startPlace = startPlace;
        getConfigForNoWishlistCase();
    }

    private void getWishlistForNoWishlistArgument() {
        for (int i = 0; i < timeMatrix.length; i++) {
            wishArrayList.add(placesMapIntegersKeys.get(i).getId());
        }
    }

    private void getConfigForNoWishlistCase() {
        try {
            getData();
            getWishlistForNoWishlistArgument();
        } catch (NullPointerException e) {
            System.out.println("Sprawdź czy formatowanie plików jest zgodne ze specyfikacją funkcjonalną!");
        }
    }

    private void initiateDataReader() {
        try {
            this.reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initiateWishListReader() {
        try {
            this.reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(wishlist), "UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void advance(BufferedReader reader) {
        try {
            this.currentLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        initiateDataReader();
        advance(reader1);
        if (!currentLine.trim().equals("### Miejsca podróży"))
            throw new IllegalArgumentException("Pierwsza linia powinna zawierać poprawnie sformatowany tytuł ,,### Miejsca podróży''.");
        advance(reader1);
        if (!currentLine.trim().equals("Lp. | ID_miejsca | Nazwa miejsca | Opis miejsca |"))
            throw new IllegalArgumentException("Druga linia powinna zawierać poprawnie sformatowane tytuły sekcji w części z danymi.");
        advance(reader1);
        if (currentLine.equals(""))
            throw new IllegalArgumentException("Jedyną linijką przerwy powinna być linia pomiędzy danymi miejsc, a czasami przejść.");

        while (!currentLine.equals("")) {
            parseLineInDataFilePlaces();
            advance(reader1);
        }

        if (placesMap.get(startPlace) == null)
            throw new IllegalArgumentException("ID miejsca startowego jest nieprawidłowe!");

        advance(reader1);
        timeMatrix = new int[placesCounter][placesCounter];
        priceMatrix = new int[placesCounter][placesCounter];
        lineNumber = 1;
        getTimes();
        for (int i = 0; i < timeMatrix.length; i++) {
            for (int j = 0; j < timeMatrix.length; j++) {
                if (timeMatrix[i][j] == 0) timeMatrix[i][j] = INFINITE_DISTANCE;
            }
        }
    }


    private void getTimes() {
        if (!currentLine.trim().equals("### Czas przejścia"))
            throw new IllegalArgumentException("Pierwsza linia sekcji czasów powinna zawierać poprawnie" +
                    " sformatowany tytuł ,,### Czas przejścia'', pamiętaj o JEDNEJ lini przerwy pomiędzy sekcjami.");
        advance(reader1);
        if (!currentLine.trim().equals("Lp. | ID_miejsca_początkowego (S) | ID_miejsca_końcowego (E) | Czas S -> E | Czas E -> S | Jednorazowa opłata za przejście trasą (zł) |"))
            throw new IllegalArgumentException("Druga linia sekcji czasów powinna zawierać poprawnie" +
                    " sformatowane nazwy poszczególnych kolumn.");
        advance(reader1);
        while (this.currentLine != null) {
            parseLineInDataFileTimes();
            advance(reader1);
        }
    }

    private void getWishList() {
        boolean isAlreadyonWishList = false;
        initiateWishListReader();
        lineNumber = 1;
        advance(reader2);
        if (!currentLine.trim().equals("### Wybrane miejsca podróży"))
            throw new IllegalArgumentException("Pierwsza linia pliku z listą wybranych miejsc jest błędnie sformatowana.");
        advance(reader2);
        if (!currentLine.trim().equals("Lp. | ID_miejsca |"))
            throw new IllegalArgumentException("Druga linia pliku z listą wybranych miejsc jest błędnie sformatowana.");
        advance(reader2);
        while (this.currentLine != null) {
            if (currentLine.equals("")) {
                advance(reader2);
                lineNumber++;
                continue;
            }
            parseLineInWishListFile();
            advance(reader2);
        }
        for (int i = 0; i < wishArrayList.size(); i++) {
            if (startPlace.equals(wishArrayList.get(i))) isAlreadyonWishList = true;
        }
        if (!isAlreadyonWishList) wishArrayList.add(startPlace);
    }

    private void getConfig() {
        try {
            getData();
            getWishList();
        } catch (NullPointerException e) {
            System.out.println("Sprawdź czy formatowanie plików jest zgodne ze specyfikacją funkcjonalną!");
        }
    }

    private void parseLineInDataFilePlaces() {
        if (!currentLine.trim().matches("^[0-9]+\\. \\| [A-z0-9]+ \\| [^|]+ \\| [^|]* \\|$"))
            throw new IllegalArgumentException("Źle sformatowana linia nr " + (lineNumber + 1) + " w części z nazwami miejsc.\n" +
                    "Pamiętaj o JEDNEJ linijce przerwy po zakończeniu sekcji danych miejsc podróży.");
        Place place = new Place();
        String number;
        String[] firstSplit = currentLine.split("\\|");
        String firstPart = firstSplit[0];
        String[] numerousPart = firstPart.split("\\.");
        number = numerousPart[0].trim();
        int placeNumericId = Integer.parseInt(number) - 1;
        if (placesCounter != placeNumericId)
            throw new IllegalArgumentException("Pamiętaj o poprawnej numeracji wierszy! ( linia nr " + (placesCounter + 1) + ")");
        place.setNumericId(placeNumericId);
        String id = firstSplit[1].trim();


        place.setId(id);
        String name = firstSplit[2].trim();

        place.setName(name);
        String description = firstSplit[3].trim();
        place.setTypeOfPlace(description);
        if (placesMap.get(id) != null)
            throw new IllegalArgumentException("Nie może wystąpić dwa razy to samo id! Sprawdź linię nr " + (lineNumber + 1));
        placesMap.put(id, place);
        placesMapIntegersKeys.put(placeNumericId, place);
        placesCounter++;
        lineNumber++;

    }

    private void parseLineInDataFileTimes() {
        if (!currentLine.trim().matches("^[0-9]+\\. \\| [A-z0-9]+ \\| [A-z0-9]+ \\| \\d+:\\d{2} \\| \\d+:\\d{2} \\| (\\d+|--) \\|$"))
            throw new IllegalArgumentException("Żle sformatowana linia nr " + lineNumber + " w części z czasami przejść.");
        String[] firstSplit = currentLine.split("\\|");
        String a = firstSplit[1].trim();
        String b = firstSplit[2].trim();
        String timeFromAToB = firstSplit[3].trim();
        String timeFromBToA = firstSplit[4].trim();
        String price = firstSplit[5].trim();

        if (placesMap.get(a) == null || placesMap.get(b) == null)
            throw new IllegalArgumentException("Niepoprawne ID miejsca w lini nr " + lineNumber + " w części z czasami przejść");

        int aNumericId = placesMap.get(a).getNumericId();
        int bNumericId = placesMap.get(b).getNumericId();

        String[] hoursAndMinutesFromAtoB = timeFromAToB.split(":");
        int hoursAToB = Integer.parseInt(hoursAndMinutesFromAtoB[0].trim());
        int minutesAToB = Integer.parseInt(hoursAndMinutesFromAtoB[1].trim());

        String[] hoursAndMinutesFromBtoA = timeFromBToA.split(":");
        int hoursBToA = Integer.parseInt(hoursAndMinutesFromBtoA[0].trim());
        int minutesBToA = Integer.parseInt(hoursAndMinutesFromBtoA[1].trim());

        int timeFromAToBInMinutes = hoursAToB * 60 + minutesAToB;
        int timeFromBToAInMinutes = hoursBToA * 60 + minutesBToA;

        if (timeMatrix[aNumericId][bNumericId] != 0)
            throw new IllegalArgumentException("Błąd w linii nr " + lineNumber
                    + ". Czas przejścia dla wybranej pary punktów powinien być określony jednokrotnie.");

        timeMatrix[aNumericId][bNumericId] = timeFromAToBInMinutes;
        timeMatrix[bNumericId][aNumericId] = timeFromBToAInMinutes;

        if (price.equals("--")) price = "0";
        priceMatrix[aNumericId][bNumericId] = Integer.parseInt(price);
        priceMatrix[bNumericId][aNumericId] = Integer.parseInt(price);
        lineNumber++;

    }

    private void parseLineInWishListFile() {
        if (!currentLine.trim().matches("^[0-9]+\\. \\| [A-z0-9]+ \\|$"))
            throw new IllegalArgumentException("Błąd w pliku z wybranymi miejscami w lini nr " + lineNumber +
                    ". Pamiętaj m. in. o pojedynczych spacjach.");
        String[] firstSplit = currentLine.split("\\|");
        boolean isAlreadyonWishList = false;
        String point = firstSplit[1].trim();
        if (placesMap.get(point) == null)
            throw new IllegalArgumentException("Miejsce o wybranym ID nie istnieje w pliku z danymi (linia nr " + lineNumber + ")");
        for (int i = 0; i < wishArrayList.size(); i++) {
            if (point.equals(wishArrayList.get(i))) isAlreadyonWishList = true;
        }
        if (!isAlreadyonWishList) wishArrayList.add(point);
        lineNumber++;
    }

    public int[][] getTimeMatrix() {
        return timeMatrix;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public int[][] getPriceMatrix() {
        return priceMatrix;
    }

    public HashMap<Integer, Place> getPlacesMapIntegersKeys() {
        return placesMapIntegersKeys;
    }

    public ArrayList<String> getWishArrayList() {
        return wishArrayList;
    }
}
