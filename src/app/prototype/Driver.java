package app.prototype;

//Driver - object what drives the car
//Driver able to create new notes and requests about routes and places
//
public class Driver {
    private String Name;    //Driver`s name
    private int ID;         //ID
    private int Exp;        //the scale og the experience
    //
    //basic constructor
    public Driver(String name, int id, int exp) {
        Name = name;
        ID = id;
        Exp = exp;
    }
}
