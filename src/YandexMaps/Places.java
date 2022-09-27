package YandexMaps;

public class Places {
    private  String namePlace;
    private  String coordinates;
    public Places(String namePlace, String coordinates){
        this.namePlace = namePlace;
        this.coordinates = coordinates;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}


