import java.util.ArrayList;

public class Place {
    private String name;
    private String id;
    private String typeOfPlace;
    private int numericId;
    private boolean isVisited = false;

    public void setName(String name){
        this.name = name;
    }

    public void setVisited() {this.isVisited = true; }

    public void setNumericId(int numericId) {
        this.numericId = numericId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTypeOfPlace(String typeOfPlace){
        this.typeOfPlace = typeOfPlace;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumericId() {
        return numericId;
    }

    public boolean isVisited() { return isVisited; }
}
