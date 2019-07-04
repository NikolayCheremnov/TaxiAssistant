package app;

import app.MapsAPI.JsonReader;
import app.prototype.DataSystem;
import app.prototype.Driver;
import app.prototype.ISerializer;
import app.prototype.StadardJavaSerializer;
import com.google.common.collect.Maps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent; ???
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static app.MapsAPI.MapsAPIManager.encodeParams;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setTitle("Application");
        //authorization ui
        Pane authorizationField = FXMLLoader.load(getClass().getResource("authorizationWindow.fxml"));
        Stage authorizationStage = new Stage();
        authorizationStage.setScene(new Scene(authorizationField));
        authorizationStage.setTitle("Authorizaiton");
        authorizationStage.initModality(Modality.WINDOW_MODAL);
        authorizationStage.initOwner(primaryStage);
        authorizationStage.setResizable(false);
        authorizationStage.show();
    }


    public static void main(String[] args) throws IOException, JSONException {
        //ISerializer s = new StadardJavaSerializer();
        /*/testing driver initializaton
        Driver side = Driver.getDriverObject();
        //
        //
        Driver.loadDriverObj("test",  s);
        side = Driver.getDriverObject();
        Driver.initDriverObject("SaveTest", -1, -1);
        Driver.saveDriverObject(s);
        Driver.initDriverObject("other", 0, 0);
        side = Driver.getDriverObject();
        Driver.loadDriverObj("SaveTest", s);
        side = Driver.getDriverObject();
        /*/
        /*/testing data system initialization
        DataSystem pre = new DataSystem("test");
        pre.saveDriverObject(s);
        DataSystem side = new DataSystem();
        side = side.loadDriverObj("test", s);
        int a = 5;
        /*/
        /*/geocoding test
        final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        params.put("address", "fail test");// адрес, который нужно геокодировать
       params.put("key", "AIzaSyBvoNONXJEl_FbuA8vM4e8QR4akYuga6HQ");
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы

        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        final double lng = location.getDouble("lng");// долгота
        final double lat = location.getDouble("lat");// широта
        System.out.println(String.format("%f,%f", lat, lng));// итоговая широта и долгота
        /*/
        /*/geodecoding test

        final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("language", "ru");// язык данных, на котором мы хотим получить
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        // текстовое значение широты/долготы, для которого следует получить ближайший понятный человеку адрес, долгота и
        // широта разделяется запятой, берем из предыдущего примера
        params.put("latlng", "55.735893,37.527420");
        params.put("key", "AIzaSyBvoNONXJEl_FbuA8vM4e8QR4akYuga6HQ");
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило, наиболее подходящий ответ первый и данные об адресе можно получить по пути
        // //results[0]/formatted_address
        final JSONObject location = response.getJSONArray("results").getJSONObject(0);
        final String formattedAddress = location.getString("formatted_address");
        System.out.println(formattedAddress);// итоговый адрес
        /*/
        /*/direct test
        final String baseUrl = "https://maps.googleapis.com/maps/api/directions/json";// путь к Geocoding API по
        // HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        params.put("language", "ru");// язык данные на котором мы хотим получить
        params.put("mode", "driving");// способ перемещения, может быть driving, walking, bicycling
        params.put("origin", "Россия, Москва, улица Поклонная, 12");// адрес или текстовое значение широты и
        // отправного пункта маршрута
        params.put("destination", "Россия, Москва, станция метро Парк Победы");// адрес или текстовое значение широты и
        // долготы
        // долготы конечного пункта маршрута
        params.put("key", "AIzaSyBvoNONXJEl_FbuA8vM4e8QR4akYuga6HQ");
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url); // Можем проверить что вернет этот путь в браузере
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("routes").getJSONObject(0);
        location = location.getJSONArray("legs").getJSONObject(0);
        final String distance = location.getJSONObject("distance").getString("text");
        final String duration = location.getJSONObject("duration").getString("text");
        System.out.println(distance + "\n" + duration);
        /*/
        launch(args);
    }
}
