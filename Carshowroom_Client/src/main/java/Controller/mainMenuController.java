package Controller;

import Utils.FxmlLoc;
import Utils.ShowWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainMenuController {
    @FXML
    public Button manufacturerButton;
    @FXML
    public Button viewerButton;
    @FXML
    public Button admin;

    public void onPressedViewer(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getViewerMenu(),"Viewer Menu",actionEvent);
    }

    public void onPressedManufacturer(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getManufacturerLogin(),"Manufacturer Login",actionEvent);
    }

    public void onPressAdmin(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAdminLogin(),"Admin Login",actionEvent);
    }
}
