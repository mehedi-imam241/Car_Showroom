package Utils;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ShowAlert {
    Alert alert = new Alert(Alert.AlertType.NONE);
    String path ;

    public ShowAlert(String type, String txt) {
        if (type.equals("CONFIRMATION")) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            path=System.getProperty("user.dir")+"\\src\\main\\resources\\notificationSounds\\Confirmation.mp3";
        } else if (type.equals("ERROR")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            path=System.getProperty("user.dir")+"\\src\\main\\resources\\notificationSounds\\error.mp3";
        }

        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        alert.setContentText(txt);
        alert.show();
    }
}
