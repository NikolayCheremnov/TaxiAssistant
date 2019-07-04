package app;

import app.MapsAPI.MapsAPIManager;
import app.prototype.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    //subject area
    private ISerializer serializer = new StadardJavaSerializer();     //serializer for driver and database
    private IMapsAPIManager mapsAPIManager = new MapsAPIManager();    //maps api manager for data system
    private DataSystem dataSystem;
    //GUI area
    @FXML
    private Pane root;  //main window root
    @FXML
    private MenuBar menu;
    //1. exitButton click handler
    public void exitClick(javafx.event.ActionEvent actionEvent) {
        Optional<ButtonType> result = ConfirmationAlertDialog("Would You really close this?");
        if (result.get() == ButtonType.OK) {
            Platform.exit();        //the completion of the program
        }
    }

    @FXML
    private TextField authorizationName; //name input field
    @FXML
    private Label expLabel; //label about needed experience data
    @FXML
    private TextField expTextField; //experience input field
    //authorization handler
    //2. load driver object or initialize it
    public void authorization( javafx.event.ActionEvent actionEvent){
        String userName = authorizationName.getText(); //entered user name
        if (expLabel.isVisible() == true) {    // if handle adding new driver
            if (expTextField.getText().isEmpty())
                return; //if exp not entered
            int userExp;
            try {
                userExp = Integer.valueOf(expTextField.getText());   //give exp value
            }
            catch (NumberFormatException ex) {
                InformationAlertDialog("Meet invalid argument", "Attention:", "Experience must be an integer");
                expTextField.setText("0");
                return;
            }
            Driver.initDriverObject(userName, userExp);    //initialize new driver
            Driver.saveDriverObject(serializer);    //save it
            expLabel.setVisible(false);
            expTextField.setText("0");
            expTextField.setVisible(false);
            ((Stage) authorizationField.getScene().getWindow()).close();//close this stage
        }
        else {
            if (userName.isEmpty())    //if user has not entered text
                return;
            //else try to authorize user with entered name
            if (Driver.loadDriverObj(userName, serializer) == null) { //if driver object with entered name was not found
                //then ask user to register new driver
                Optional<ButtonType> result = QuestionAlertDialog("Would You like to register new driver?");
                if (result.get().getText() == "Yes") {    // if user would like register new driver
                    expLabel.setVisible(true);
                    expTextField.setVisible(true);
                    expTextField.setText("0");
                }
            }
            else {
                //else driver was initialized and authorization windows was hide
                ((Stage) authorizationField.getScene().getWindow()).close();//close this stage
            }
        }

    }

    //3. authorization question handler (button ?)
    public void aboutAuthorization(ActionEvent actionEvent) {
        InformationAlertDialog("?", "You should be authorized.", "Please input your user name or register new user.");
    }
    //4. authorization field fields and methods
    @FXML
    private AnchorPane authorizationField;
    public void authorizationFieldExecute(){
        Pane authorizationField = null;
        try {
            authorizationField = FXMLLoader.load(getClass().getResource("authorizationWindow.fxml"));
        } catch (IOException e) {
            // e.printStackTrace();
            return;
        }
        Stage authorizationStage = new Stage();
        authorizationStage.setScene(new Scene(authorizationField));
        authorizationStage.setTitle("Authorizaiton");
        authorizationStage.initModality(Modality.WINDOW_MODAL);
        authorizationStage.initOwner(root.getScene().getWindow());
        authorizationStage.setResizable(false);
        authorizationStage.show();
    }
    //5. loading dataSystem handler
    @FXML
    public Tab placesTab;    //for unlocking after initialize data System
    @FXML
    public Tab routesTab;
    public void dataSystemLoading(ActionEvent actionEvent) {
        inputFieldExecute("input name of dataSystem");    //input String was entered
        dataSystem = new DataSystem();
        dataSystem = dataSystem.loadDataSystemObj(inputString, serializer);
        if (dataSystem == null) {   //if data system was not read
            InformationAlertDialog("Attention", "Object was not found", "Object with entered name was not found");
            return;
        }
        placesTab.setDisable(false);
        routesTab.setDisable(false);
    }
    //6. creator and saver dataSystem handler
    public void dataSystemCreator(ActionEvent actionEvent) {
        inputFieldExecute("input name of dataSystem");
        dataSystem= new DataSystem(inputString, Driver.getDriverObject().getDriverId());
        placesTab.setDisable(false);
        routesTab.setDisable(false);
    }
    public void dataSystemSaver(ActionEvent actionEvent) {
        dataSystem.saveDataSystemObject(serializer);
    }
    //helpful methods
    //1.  information (OK) alert dialog display
    private void InformationAlertDialog(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
    //2. input field fields and methods - from inputField.fxml
    @FXML
    private AnchorPane inputField;
    @FXML
    private TextField inputTextField;
    private static String inputString; //entered string from input field
    //launch input field window
    public void inputFieldExecute(String title) {
        AnchorPane inputField = null;
        try {
            inputField = FXMLLoader.load(getClass().getResource("inputWindow.fxml"));
        } catch (IOException e) {
            // e.printStackTrace();
            return;
        }
        Stage inputStage = new Stage();
        inputStage.setScene(new Scene(inputField));
        inputStage.setTitle(title);
        inputStage.initModality(Modality.WINDOW_MODAL);
        inputStage.initOwner(root.getScene().getWindow());
        inputStage.setResizable(false);
        inputStage.showAndWait();
    }
    //input field enter handler
    public void inputFieldEnter(ActionEvent actionEvent) {
        inputString = inputTextField.getText();
        inputFieldClose(new ActionEvent());
    }
    //input field closer
    public void inputFieldClose(ActionEvent actionEvent) {
        ((Stage)inputField.getScene().getWindow()).close();
    }
    //3. confirmation (OK/CANCEL) alert dialog display with returned answer
    //input: content text string
    //output: pressed button type
    public Optional<ButtonType> ConfirmationAlertDialog(String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Attention");
        alert.setContentText("Would You really close this?");
        return alert.showAndWait();
    }
    //4. question (YES/NO) alert dialog display with returned answer
    public Optional<ButtonType> QuestionAlertDialog(String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ask Dialog");
        alert.setHeaderText("Attention");
        alert.setContentText(contentText);
        ButtonType YesButton = new ButtonType("Yes");
        ButtonType NoButton = new ButtonType("No");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(YesButton, NoButton);
        return alert.showAndWait();
    }
    //
    //information tab selection
    @FXML
    private TextArea driverInfo;    //information about driver
    @FXML
    private TextArea dataSystemInfo;    //information about dataSystem
    public void infoUpdateHandler(ActionEvent actionEvent) {
        driverInfo.setText(Driver.getDriverObject().toString());
        if (dataSystem == null) //if data system was not loaded
            dataSystemInfo.setText("Not initialized");
        else
            dataSystemInfo.setText(dataSystem.toString());
    }
    //
    //placesTab selection:
    @FXML
    private TextArea commentsTextArea;    //special big text area in places tab
    @FXML
    private TextArea informationTextArea;   //text area for information about place/note
    @FXML
    private TextField placeIndexTextField;   //index of place
    @FXML
    private TextField noteIndexTextField;   //index of note
    @FXML
    private TextField addressTextField; //text of address
    @FXML
    private TextField longitudeTextField;
    @FXML
    private TextField latitudeTextField;
    //handler show all places button - show all information about places in comment text area
    public void showAllPlacesHandler(ActionEvent actionEvent) {
        String res = dataSystem.placesToString();
        if (res.isEmpty())
            commentsTextArea.setText("Places array is empty\n");
        else
            commentsTextArea.setText(res);
    }
    //handler add new place into
    public void addPlaceHandler(ActionEvent actionEvent) {
        try {
            String res;
            String address = addressTextField.getText();    //try to get address
            if (!address.isEmpty()) {    //if was input symbolic address
                res = dataSystem.addPlace(address, mapsAPIManager);
            } else {
                double longitude = Double.valueOf(longitudeTextField.getText());    //try to get geo coords
                double latitude = Double.valueOf(latitudeTextField.getText());
                //if we meet error then work exception
                res = dataSystem.addPlace(longitude, latitude, mapsAPIManager);
            }
            if (res == null)
                InformationAlertDialog("Successful", "Place was added", "");
            else
                InformationAlertDialog("Unsuccessful", "Place was not added", res);
            return;
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your arguments");
            return;
        }

    }
    //clear button handler - clear all text field and areas in places tab
    public void placesFieldsClear(ActionEvent actionEvent) {
        commentsTextArea.clear();
        informationTextArea.clear();
        placeIndexTextField.clear();

        addressTextField.clear();
        longitudeTextField.clear();
        latitudeTextField.clear();
    }
    //handler which remove place
    public void removePlaceHandler(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(placeIndexTextField.getText());  //if meet problem then exception is worked
            String res = dataSystem.removePlace(index);
            if (res == null)
                InformationAlertDialog("Successful", "Place was removed", "");
            else
                InformationAlertDialog("Unsuccessful", "Place was not removed", res);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //handler which add new note to place
    //note add to place with index from indexTextField
    public void addNoteHandler(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(placeIndexTextField.getText());  //if meet problem then exception is worked
            String comments = commentsTextArea.getText();
            if (comments.isEmpty()) {   //if comments was not entered
                InformationAlertDialog("Attention", "Could not add note", "Empty comments are illegal");
                return;
            }
            String res = dataSystem.addNoteToPlace(index, comments);
            if (res == null)
                InformationAlertDialog("Successful", "Note was added", "");
            else
                InformationAlertDialog("Unsuccessful", "Note was not added", res);
    }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //handler which remove note from place
    public void removeNoteHandler(ActionEvent actionEvent) {
        try {
            int placeIndex = Integer.valueOf(placeIndexTextField.getText());  //if meet problem then exception is worked
            int noteIndex = Integer.valueOf(noteIndexTextField.getText());  //if meet problem then exception is worked
            String res = dataSystem.removeNoteFromPlace(placeIndex, noteIndex);
            if (res == null)
                InformationAlertDialog("Successful", "Note was removed", "");
            else
                InformationAlertDialog("Unsuccessful", "Note was not removed", res);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index arguments");
        }
    }

    public void requestPlaceHandler(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(placeIndexTextField.getText());  //if meet problem then exception is worked
            String placeInfoRes = (dataSystem.getPlace(index)).toString();
            String notesInfoRes = (dataSystem.getPlace(index)).notesToString();
            if (notesInfoRes.isEmpty())
                notesInfoRes = "There is no notes";
            informationTextArea.setText(placeInfoRes);
            commentsTextArea.setText(notesInfoRes);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //
    //placesTab selection:
    //fields:
    @FXML
    private TextField routeIndexTextField;
    @FXML
    private TextField noteIndexTextField1;
    @FXML
    private TextArea informationTextArea1;
    @FXML
    private TextArea commentsTextArea1;
    //routes clear button clear - clear all text in tab
    public void routesFieldsClear(ActionEvent actionEvent) {
        routeIndexTextField.clear();
        noteIndexTextField1.clear();
        informationTextArea1.clear();
        commentsTextArea1.clear();
    }
    //show all routes in database
    public void showAllRoutesHandler(ActionEvent actionEvent) {
        String res = dataSystem.routesToString();
      if (res.isEmpty())
        commentsTextArea1.setText("Routes array is empty\n");
      else
         commentsTextArea1.setText(res);
    }
    //add route to database handler
    public void addRouteHandler(ActionEvent actionEvent) {
        try {
            //1.
            int startPlaceIndex = -1, finishPlaceIndex = -1;    //index of place from places list or -1
            Optional<ButtonType> userAnswer = QuestionAlertDialog("Would you like add start place from places list?");
            if (userAnswer.get().getText() == "Yes") {    //if add start place from places list
                inputFieldExecute("Input index of start place: ");
                startPlaceIndex = Integer.valueOf(inputString);
            }
            userAnswer = QuestionAlertDialog("Would you like add finish place from places list?");
            if (userAnswer.get().getText() == "Yes") {    //if add finish place from places list
                inputFieldExecute("Input index of finish place: ");
                finishPlaceIndex = Integer.valueOf(inputString);
            }
            //start place preparation
            Place startPlace;
            if (startPlaceIndex != -1) {    //if start places from places list
                startPlace = dataSystem.getPlace(startPlaceIndex);
                if (startPlace == null)
                    throw new Exception();
            }
            else {  //if required add new place
                inputFieldExecute("Input start place address: ");
                String res = dataSystem.addPlace(inputString, mapsAPIManager);  //try to add new place to the end
                if (res != null) //if unsuccessful
                    throw new Exception();
                startPlace = dataSystem.getPlace(dataSystem.getPlacesNumb() - 1);
            }
            //finish place preparation
            Place finishPlace;
            if (finishPlaceIndex != -1) {    //if start places from places list
                finishPlace = dataSystem.getPlace(finishPlaceIndex);
                if (finishPlace == null)
                    throw new Exception();
            }
            else {  //if required add new place
                inputFieldExecute("Input finish place address: ");
                String res = dataSystem.addPlace(inputString, mapsAPIManager);  //try to add new place to the end
                if (res != null) //if unsuccessful
                    throw new Exception();
                finishPlace = dataSystem.getPlace(dataSystem.getPlacesNumb() - 1);
            }
            //add new route
            dataSystem.addRoute(startPlace, finishPlace);
            InformationAlertDialog("Successful", "Route was added", "");
        }
        catch (Exception ex) {  //catch convertion, nullpointer and other possible exceptions
            InformationAlertDialog("Attention", "Route was not added", "Verify your input parameters");
            return;
        }
    }
    //remove route
    public void removeRouteHandler(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(routeIndexTextField.getText());  //if meet problem then exception is worked
            String res = dataSystem.removeRoute(index);
            if (res == null)
                InformationAlertDialog("Successful", "Route was removed", "");
            else
                InformationAlertDialog("Unsuccessful", "Route was not removed", res);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //add  route note handler
    public void addNoteHandler1(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(routeIndexTextField.getText());  //if meet problem then exception is worked
            String comments = commentsTextArea1.getText();
            if (comments.isEmpty()) {   //if comments was not entered
                InformationAlertDialog("Attention", "Could not add note", "Empty comments are illegal");
                return;
            }
            String res = dataSystem.addNoteToRoute(index, comments);
            if (res == null)
                InformationAlertDialog("Successful", "Note was added", "");
            else
                InformationAlertDialog("Unsuccessful", "Note was not added", res);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //handler which remove note from routes
    public void removeNoteHandler1(ActionEvent actionEvent) {
        try {
            int routeIndex = Integer.valueOf(routeIndexTextField.getText());  //if meet problem then exception is worked
            int noteIndex = Integer.valueOf(noteIndexTextField1.getText());  //if meet problem then exception is worked
            String res = dataSystem.removeNoteFromRoute(routeIndex, noteIndex);
            if (res == null)
                InformationAlertDialog("Successful", "Note was removed", "");
            else
                InformationAlertDialog("Unsuccessful", "Note was not removed", res);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index arguments");
        }
    }
    //route request
    public void requestRouteHandler(ActionEvent actionEvent) {
        try {
            int index = Integer.valueOf(routeIndexTextField.getText());  //if meet problem then exception is worked
            String routeInfoRes = (dataSystem.getRoute(index)).toString();
            String notesInfoRes = (dataSystem.getRoute(index)).notesToString();
            if (notesInfoRes.isEmpty())
                notesInfoRes = "There is no notes";
            informationTextArea1.setText(routeInfoRes);
            commentsTextArea1.setText(notesInfoRes);
        }
        catch (Exception ex) {
            InformationAlertDialog("Attention", "Invalid arguments", "Please, verify your Index argument");
        }
    }
    //
}
