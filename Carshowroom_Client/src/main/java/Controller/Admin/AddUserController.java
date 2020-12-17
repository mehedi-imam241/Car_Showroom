package Controller.Admin;


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

public class AddUserController {
    @FXML
    public TextField nameTxt;
    @FXML
    public PasswordField passwordTxt;
    @FXML
    public Button register;
    @FXML
    public Button toBack;

    public void onPressedRegister(ActionEvent actionEvent) {
        String name = nameTxt.getText();
        String password = passwordTxt.getText();
        if (name.isEmpty() && password.isEmpty()) {
            new ShowAlert("ERROR", "Enter Username and Password");
            return;
        } else if (name.isEmpty()) {
            new ShowAlert("ERROR", "Enter Username !");
            return;
        } else if (password.isEmpty()) {
            new ShowAlert("ERROR", "Enter Password !");
            return;
        }

        Profile regProfile = new Profile(name, password, "true", "dummy", "dummy");

        new Thread(
                () -> Platform.runLater(
                        () -> {
                            try {
                                registration(regProfile);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                )
        ).start();
    }

    private void registration(Profile regProfile) throws IOException, ClassNotFoundException {

        SocketConnector admin = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("addUser", "admin"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream1.writeObject(regProfile);
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(admin.getSocket().getInputStream());
        Profile profile = (Profile) objectInputStream.readObject();

        if (profile.getIsSuccess().equals("true")) {
            nameTxt.clear();
            passwordTxt.clear();
            new ShowAlert("CONFIRMATION", regProfile.toString() + "\nProfile has been Added!");
        } else {
            nameTxt.clear();
            new ShowAlert("ERROR", "Duplicate User Name! Try another.");
        }
    }


    public void onPressedToBack(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Admin/AdminMenu.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Admin Menu");
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
