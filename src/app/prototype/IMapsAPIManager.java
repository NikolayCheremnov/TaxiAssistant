package app.prototype;

import org.json.JSONException;

import java.io.IOException;

//interface which realize operations with maps api for system
public interface IMapsAPIManager {
    //geo- Coder and Decoder for API interaction
    double[] geoCoder(String address) throws IOException;
    String geoDecoder(double longitude, double latitude) throws IOException;
}
