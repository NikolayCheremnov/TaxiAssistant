package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent; ???
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setTitle("Application");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
