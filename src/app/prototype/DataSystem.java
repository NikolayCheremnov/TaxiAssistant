package app.prototype;

import java.io.Serializable;
import java.util.ArrayList;

public class DataSystem implements Serializable {
    //1. basic constructors
    public DataSystem(){} //basic void
    public DataSystem(String ownerName, int ownerIdentificator) {   //basic via parameters
        Name = ownerName;
        OwnerId = ownerIdentificator;
        Routes = new ArrayList<Route>();
        Places = new ArrayList<Place>();
    }
    //2.
    //save object to disk
    //input: object serializer
    //output: null if successful or problem string
    public String saveDataSystemObject(ISerializer Serializer) {
        return Serializer.<DataSystem>Serialization(this, Name); //null if successful or not null
    }
    //3.
    //load object from disk: method with returned result
    //input: personal name, object serializer
    //output: dataSystem object or null
    public DataSystem loadDataSystemObj(String name, ISerializer Serializer) {
        DataSystem result = new DataSystem();
        result = Serializer.<DataSystem>Deserialization(DataSystem.class.getSimpleName(), name, result); //try read driver
        if (result != null)  //if was read successful
            return result;
        else
            return null;    //if have any problem
    }
    //4. 5.
    //add Place object in data System via address
    //input: symbolic address, api manager
    //output: null if successful else error message string
    public String addPlace(String address, IMapsAPIManager mapsAPIManager) {
        try {
            double[] coords = mapsAPIManager.geoCoder(address); //get coords
            Places.add(new Place(address, coords[0], coords[1]));
            return null;
        }
        catch (Exception ex) {
            return "Error in api request, try again or debug json doc.";
        }
    }
    //add Place object in data System via address
    //input: coords, api manager
    //output: null if successful else error message string
    public String addPlace(double lng, double lat, IMapsAPIManager mapsAPIManager) {
        try {
            String address = mapsAPIManager.geoDecoder(lng, lat); //get coords
            Places.add(new Place(address, lng, lat));
            return null;
        }
        catch (Exception ex) {
            return "Error in api request, try again or debug json doc.";
        }
    }
    //
    //6. Remove place[index]
    //input: index
    //output: null if successful or error message string
    public String removePlace(int index) {
        try {
            Places.remove(index);
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //7. add note to place
    //input: index of place, comments
    //output: null if successful else error message string
    public String addNoteToPlace(int index, String comments) {
        try {
            (Places.get(index)).addNote(comments);
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //8. remove note from place
    //input: index of place, index of note
    //output: null if successful else error message string
    public String removeNoteFromPlace(int placeIndex, int noteIndex) {
        try {
            String res = (Places.get(placeIndex)).removeNote(noteIndex);
            if (res != null)  //if incorrect res
                throw new Exception();
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //
    //9.
    //add route
    //input: start places, finish places
    //output: void
    public void addRoute(Place startPlace, Place finishPlace) {
        Routes.add(new Route(startPlace, finishPlace));
    }
    //10. Remove route[index]
    //input: index
    //output: null if successful or error message string
    public String removeRoute(int index) {
        try {
            Routes.remove(index);
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //11. add note to route
    //input: index of route, comments
    //output: null if successful else error message string
    public String addNoteToRoute(int index, String comments) {
        try {
            (Routes.get(index)).addNote(comments);
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //12. remove note from route
    //input: index of place, index of note
    //output: null if successful else error message string
    public String removeNoteFromRoute(int routeIndex, int noteIndex) {
        try {
            String res = (Routes.get(routeIndex)).removeNote(noteIndex);
            if (res != null)  //if incorrect res
                throw new Exception();
            return null;
        }
        catch(Exception ex) {
            return "Incorrect index";
        }
    }
    //getters:
    public int getPlacesNumb(){return Places.size();}   //places numb getter
    public Place getPlace(int i) {  //place[i] getter, if index out of range then return null
        try{
            return Places.get(i);
        }
        catch (Exception ex) {
            return null;
        }
    }
    public Route getRoute(int i) {  //route[i] getter, if index out of range then return null
        try{
            return Routes.get(i);
        }
        catch (Exception ex) {
            return null;
        }
    }
    //toString all places
    public String placesToString(){
        String res = new String();
        for (int i = 0; i < Places.size(); i++)
            res += String.valueOf(i) + ". " + (Places.get(i)).toString();
        return res;
    }
    //toString all routes
    public String routesToString(){
        String res = new String();
        for (int i = 0; i < Routes.size(); i++)
            res += String.valueOf(i) + ". " + (Routes.get(i)).toString();
        return res;
    }
    //toString display method
    @Override
    public String toString(){
        return "Name: " + Name + "\nOwner ID: " + String.valueOf(OwnerId) +  "\nRoutes number: " + String.valueOf(Routes.size()) + "\nPlaces number: " + String.valueOf(Places.size());
    }
    //
    private String Name;               //name of user who created this base
    private int OwnerId;                //id of user who created this base
    private ArrayList<Route> Routes;    //routes collection
    private ArrayList<Place> Places;    //places collection
}
