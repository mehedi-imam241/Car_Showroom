package Utils;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class ShowAlert {
    Alert alert = new Alert(Alert.AlertType.NONE);

    public ShowAlert(String type, String txt) {
        if (type.equals("CONFIRMATION")) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
        } else if (type.equals("ERROR")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        else if (type.equals("INFORMATION")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setContentText(txt);
        alert.show();
    }
    public ShowAlert(String type, String txt, ImageView img) {
        if (type.equals("CONFIRMATION")) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
        } else if (type.equals("ERROR")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        else if (type.equals("INFORMATION")) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
        }
        alert.setContentText(txt);
        alert.setGraphic(img);
        alert.show();
    }
}
