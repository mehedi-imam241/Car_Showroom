package Controller.Admin;

import Utils.FxmlLoc;
import Utils.ShowWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenuController {
    @FXML
    public Button toMainMenu ;
    @FXML
    public Button addUser;
    @FXML
    public Button seeAll;
    @FXML
    public Button deleteUser;
    @FXML
    public Button updateUser;

    public void onPressToBack(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAdminLogin(),"Admin Login",actionEvent);
    }

    public void onPressedAddUser(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAddUser(),"Add User",actionEvent);
    }

    public void onPressedUpdateUser(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getUpdateUser(),"Update User",actionEvent);
    }

    public void onPressedDeleteUser(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getDeleteUser(),"Delete User",actionEvent);
    }

    public void onPressedSeeAll(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAllUser(),"All Users",actionEvent);
    }
}
