package Controller.Manufacturer;


import Client.SocketConnector;
import Utils.FxmlLoc;
import Utils.Profile;
import Utils.ShowAlert;
import Utils.ShowWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManufacturerLoginController {
    @FXML
    public Button ManufacturerLogin;
    @FXML
    public TextField userName;
    @FXML
    public PasswordField password;
    @FXML
    public Button toMainMenu;

    public void onPressedManufacturerLogin(ActionEvent actionEvent) {
        String name = userName.getText();
        String pswd = password.getText();
        if (name.isEmpty() || pswd.isEmpty()) {
            new ShowAlert("ERROR", "Input field is empty. Please fill up");
            return;
        }
        Profile p = new Profile(name, pswd, "true", "login", "manufacturer");

        new Thread(() -> Platform.runLater(() -> {
            try {
                checkUser(p,actionEvent);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        })).start();

    }

    private void checkUser(Profile p,ActionEvent actionEvent) throws IOException, ClassNotFoundException {

            SocketConnector client = new SocketConnector();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
            objectOutputStream.writeObject(p);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());

            Profile profile = (Profile) objectInputStream.readObject();
            System.out.println(profile.toString());


            if (profile.getIsSuccess().equals("true")) {
                new ShowWindow(FxmlLoc.getManufacturerMenu(),"Manufacturer Menu",actionEvent);
            } else {
                userName.clear();
                password.clear();
                new ShowAlert("ERROR", "Login Failed. Try again");
            }


    }


    public void onGetUserName(ActionEvent actionEvent) {
    }

    public void onGetPassword(ActionEvent actionEvent) {
    }

    public void onPressedToMainMenu(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getMainMenu(),"Main Menu",actionEvent);
    }

}
