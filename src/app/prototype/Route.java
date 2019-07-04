package app.prototype;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    //start and finish points
    private Place StartPlace;
    private Place FinishPlace;
    private ArrayList<Note> Notes; //notes about routes
    //
    //basic constructor
    public Route(Place startPoint, Place finishPoint) {
        StartPlace = startPoint;
        FinishPlace = finishPoint;
        Notes = new ArrayList<Note>();
    }
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
    public String toString() {return "Start place: " + StartPlace.toString() + "FinishPlace: " + FinishPlace.toString() + "Route have " + String.valueOf(Notes.size()) + " notes\n";}
}
