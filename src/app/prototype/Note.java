package app.prototype;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private Date NoteDate;              //then note was created
    private String Comments;            //comments about theme of note
    //
    //basic constructor
    public Note(String comments) {
        NoteDate = new Date();  //initialization current date
        Comments = comments;
    }
    //toString method
    @Override
    public String toString(){
        return NoteDate.toString() + ": " + Comments + "\n";
    }
}
