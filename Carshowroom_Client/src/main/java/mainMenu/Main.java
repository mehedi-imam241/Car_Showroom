package mainMenu;

import Utils.WindowPositioning;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        primaryStage.setTitle("Car Showroom");
        primaryStage.setScene(new Scene(root, 1000.0, 650.0));
        primaryStage.setX(WindowPositioning.getX());
        primaryStage.setY(WindowPositioning.getY());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
