package Controller.Manufacturer;

import Utils.FxmlLoc;
import Utils.ShowWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ManufacturerMenuController {
    @FXML
    public Button addCar;
    @FXML
    public Button editCar;
    @FXML
    public Button toBack;
    @FXML
    public Button viewAndDelete;

    public void onPressedViewAllCars(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getViewDelete(),"View and Delete",actionEvent);
    }

    public void onPressAddCar(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAddCar(),"Add Car",actionEvent);
    }

    public void onPressEditCar(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getEditCar(),"Edit Car",actionEvent);
    }


    public void onPressToBack(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getManufacturerLogin(),"Manufacturer Login",actionEvent);
    }
}
