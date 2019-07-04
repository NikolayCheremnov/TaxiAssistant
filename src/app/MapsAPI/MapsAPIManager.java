package app.MapsAPI;

import app.prototype.IMapsAPIManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.json.JSONException;
import org.json.JSONObject;

//directly realization of interface
public class MapsAPIManager implements IMapsAPIManager {
    //parameters store in map: key1=value1&key2=value2...
    public static String encodeParams(Map<String, String> params) {
        String paramsUrl = Joiner.on('&').join(// получаем значение вида key1=value1&key2=value2...
                Iterables.transform(params.entrySet(), new Function<Entry<String, String>, String>() {

                    @Override
                    public String apply(Entry<String, String> input) {
                        try {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(input.getKey());// получаем значение вида key=value
                            buffer.append('=');
                            buffer.append(URLEncoder.encode(input.getValue(), "utf-8"));// кодируем строку в соответствии со стандартом HTML 4.01
                            return buffer.toString();
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
        return paramsUrl;
    }
    //geoCoder API realization:
    //input: address string
    //output: array double type: { longitude, latitude } or null if incorrect request
    public double[] geoCoder(String address) throws IOException, JSONException {
        String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        params.put("address", address);// адрес, который нужно геокодировать
        params.put("key", "AIzaSyBvoNONXJEl_FbuA8vM4e8QR4akYuga6HQ");
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        //url = the way to answer the query
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        try {
            JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
            // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
            // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
            JSONObject location = response.getJSONArray("results").getJSONObject(0);
            location = location.getJSONObject("geometry");
            location = location.getJSONObject("location");
            double lng = location.getDouble("lng");// долгота
            double lat = location.getDouble("lat");// широта
            //System.out.println(String.format("%f,%f", lat, lng));// итоговая широта и долгота
            double[] result = new double[2];
            result[0] = lng;    //longitude
            result[1] = lat;    //latitude
            return result;
        }
        catch (Exception ex) {
            return null;    //if we have problem then null
        }
    }
    //geoDecoder API realization
    //input: double type longitude and latitude
    //output: address string or null
    public String geoDecoder(double longitude, double latitude) throws IOException, JSONException {
        String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        Map<String, String> params = Maps.newHashMap();
        params.put("language", "ru");// язык данных, на котором мы хотим получить
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        // текстовое значение широты/долготы, для которого следует получить ближайший понятный человеку адрес, долгота и
        // широта разделяется запятой, берем из предыдущего примера
        params.put("latlng", String.valueOf(latitude) + "," + String.valueOf(longitude));
        params.put("key", "AIzaSyBvoNONXJEl_FbuA8vM4e8QR4akYuga6HQ");   //unique key
        String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        try {
            JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
            // как правило, наиболее подходящий ответ первый и данные об адресе можно получить по пути
            // //results[0]/formatted_address
            JSONObject location = response.getJSONArray("results").getJSONObject(0);
            String formattedAddress = location.getString("formatted_address");
            //System.out.println(formattedAddress);// итоговый адрес
            return formattedAddress;    //result
        }
        catch (Exception ex) {
            return null;    //if we have problem then null
        }
    }
}
