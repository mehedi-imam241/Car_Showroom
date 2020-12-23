package Controller.Viewer;

import Utils.FxmlLoc;
import Utils.ShowWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ViewerMenuController {
    @FXML
    public Button viewAndBuyCar;
    @FXML
    public Button searchByReg;
    @FXML
    public Button searchByMake;
    @FXML
    public Button toMainMenu;

    public void onPressedViewAndBuyCar(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getViewBuy(),"View & Buy",actionEvent);
    }

    public void onPressedSearchByReg(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getSearchByReg(),"Search",actionEvent);
    }

    public void onPressedSearchByMake(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getSearchByMakeModel(),"Search",actionEvent);
    }


    public void onPressedToMainMenu(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getMainMenu(),"Main Menu",actionEvent);
    }
}
