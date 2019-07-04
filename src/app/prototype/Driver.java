package app.prototype;

import java.io.*;
import java.util.Date;

//Driver - object what drives the car
//Driver able to create new notes and requests about routes and places
//driver is serializable
public class Driver implements Serializable {
    //Singleton object
    private static Driver DriverObj;    //directly driver
    //1.
    //basic private constructors
    private Driver() {} //basic void constructor
    private Driver(String name, int exp) {  //constructor via parameters
        Name = name;
        Exp = exp;
        regDate = new Date();   //get current date
        Id = regDate.hashCode();    //get unique hash
    }
    //2.
    //load object from disk
    //input: personal name, object serializer
    //output: driver object or null
    public static Driver loadDriverObj(String name, ISerializer Serializer) {
        DriverObj = Serializer.<Driver>Deserialization(Driver.class.getSimpleName(), name, DriverObj); //try read driver
        if (DriverObj != null)  //if was read successful
            return DriverObj;
        else
            return null;    //if have any problem
    }
    //3.
    //initialization driver object: updating all fields via parameters
    //input: Name, Id, exp
    //output: void
    public static void initDriverObject(String name, int exp) {
        DriverObj = new Driver(name, exp);  //create new object (old object was lost)
    }
    //4.
    //save object to disk
    //input: object serializer
    //output: null if successful or problem string
    public static String saveDriverObject(ISerializer Serializer) {
        if (DriverObj == null)  //check the presence of the object
            return "object is empty";
        else    //if object is exist then try serialize it
            return Serializer.<Driver>Serialization(DriverObj, DriverObj.Name); //null if successful

    }
    //5. getters
    public static Driver getDriverObject(){return DriverObj;}   // main object getter
    public int getDriverId(){return Id;}// id getter
    //
    //6. toString display method
    @Override
    public String toString(){
        return "Name: " + Name + "\n" + "ID: " + String.valueOf(Id) + "\n" + "Exp: " + String.valueOf(Exp) + "\n" + "Date of registration: " + regDate.toString();
     }
    private String Name;    //Driver`s name
    private int Id;         //Id
    private int Exp;        //the scale og the experience
    private Date regDate;   //date of driver registration
    //
}
