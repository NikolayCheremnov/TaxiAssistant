package app.prototype;

import java.util.Date;

public class Note {
    //valid values for the condition variable
    public enum ConditionTypes {                                             //!!! public or private ??? !!!!
        GOOD,
        MIDDLE,
        BAD,
        UNKNOWN;    //if condition is unknown
    }
    private ConditionTypes Condition;   //note condition
    private Date NoteDate;              //then note was created
    private String Comments;            //comments about theme of note
    //
    //basic constructor
    public Note(String comments, ConditionTypes condition ) {
        NoteDate = new Date();  //initialization current date
        Comments = comments;
        Condition = condition;
    }
    //
}
