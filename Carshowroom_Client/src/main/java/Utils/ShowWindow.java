package Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ShowWindow {
    public ShowWindow(String fxmlLoc, String title, ActionEvent actionEvent)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLoc));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle(title);
            WindowPositioning.setX(((Node)(actionEvent.getSource())).getScene().getWindow().getX());
            WindowPositioning.setY(((Node)(actionEvent.getSource())).getScene().getWindow().getY());
            stage.setX(WindowPositioning.getX());
            stage.setY(WindowPositioning.getY());
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
