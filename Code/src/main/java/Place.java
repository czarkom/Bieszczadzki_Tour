public class Place {
    private String name;
    private String id;
    private String typeOfPlace;
    private int numericId;

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumericId() {
        return numericId;
    }

    public void setNumericId(int numericId) {
        this.numericId = numericId;
    }

}
