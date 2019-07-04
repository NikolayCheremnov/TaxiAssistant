package app.prototype;

import java.io.Serializable;
import java.util.ArrayList;

public class Place implements Serializable {
    private double longitude;             //longitude
    private double latitude;             //latitude
    private String Address;         //symbolic address
    private ArrayList<Note> Notes;  //notes about place
    //
    //basic constructors
    public Place(String adr, double lng, double lat){
        Address = adr;
        longitude = lng;
        latitude =  lat;
        Notes = new ArrayList<Note>();
    }
    //
    //add note
    //input: comments
    //output:
    public void addNote(String comments) {
        Notes.add(new Note(comments));
    }
    //remove note
    //input: note index
    //output: null if successful or error message
    public String removeNote(int index){
        try {
            Notes.remove(index);
            return null;
        }
        catch (Exception ex) {
            return "Incorrect note index";
        }
    }
    //notes string
    //input:
    //output: notes string
    public String notesToString() {
        String res = new String();
        for (int i = 0; i < Notes.size(); i++)
            res += String.valueOf(i) + ". " + (Notes.get(i)).toString();
        return res;
    }
    //override toString method
    @Override
    public String toString(){
        return Address + "; (lng,lat):(" + String.valueOf(longitude) + "," + String.valueOf(latitude) + "); have " + String.valueOf(Notes.size()) + " notes\n";
    }
}
