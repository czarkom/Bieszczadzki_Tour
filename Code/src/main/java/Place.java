import java.util.ArrayList;

public class Place {
    private String name;
    private String id;
    private String typeOfPlace;
    private int numericId;
    private ArrayList<Place> listOfConnections;

    public void setName(String name){
        this.name = name;
    }

    public void setNumericId(int numericId) {
        this.numericId = numericId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTypeOfPlace(String typeOfPlace){
        this.typeOfPlace = typeOfPlace;
    }

    public void addPathTo(Place place){
        listOfConnections.add(place);
    }

    public ArrayList<Place> getListOfConnections(){
        return listOfConnections;
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
}
