package app.prototype;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//performs unified serialization and deserialization to the project directory
public class StadardJavaSerializer implements ISerializer{
    //saves the state of an object to disk
    //input: object, name
    //output: null or exception message
    public <T> String Serialization(T object, String name) {
        try (ObjectOutputStream stream = new ObjectOutputStream((new FileOutputStream(name + "_" + object.getClass().getSimpleName() + ".dat")))) {
            stream.writeObject(object);       //write object
        }
        catch (Exception writeEx) {
            return writeEx.getMessage();    //if we have any problem then return message about it
        }
        return null;    //if object was write successful
    }
    //load the state of an object from disk
    //input: simple class name of read class, name
    //output: read object or null (if unsuccessful)
    public <T> T Deserialization(String simpleClassName, String name, T object) {
        try (ObjectInputStream stream = new ObjectInputStream((new FileInputStream(name + "_" + simpleClassName + ".dat")))) {
           object = (T)stream.readObject();       //read object in object which contain readable class
        }
        catch (Exception writeEx) {
            return null;    //if we have any problem then return null
        }
        return object;    //if object was read successful
    }
}
