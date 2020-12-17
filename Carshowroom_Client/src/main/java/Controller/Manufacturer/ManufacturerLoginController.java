package Controller.Manufacturer;


import Client.SocketConnector;
import Utils.Profile;
import Utils.ShowAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Manufacturer/manufacturerMenu.fxml"));
                    Parent root1 = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.setTitle("Manufacturer Menu");
                    stage.show();
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Main Menu");
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
