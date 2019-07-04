package app.prototype;

//
public interface ISerializer {
    //saves the state of an object to disk
    //input: object, name
    //output: null or exception message
    <T> String Serialization(T object, String name);
    //input: simple class name of read class, name
    //output: read object or null (if unsuccessful)
    <T> T Deserialization(String simpleClassName, String name, T object);
}
