package Controller.Admin;

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

public class UpdateUserWindowController {

    @FXML
    public TextField searchUserTxt;
    @FXML
    public TextField newNameTxt;
    @FXML
    public PasswordField newPasswordTxt;
    @FXML
    public Button update;
    @FXML
    public Button toBack;


    public void onPressedUpdate(ActionEvent actionEvent) {
        String curName = searchUserTxt.getText();
        String newName = newNameTxt.getText();
        String newPass = newPasswordTxt.getText();
        if (curName.isEmpty() || newName.isEmpty() || newPass.isEmpty()) {
            new ShowAlert("ERROR", "Empty field Exists. Please fill them!");
            return;
        }

        new Thread(() -> Platform.runLater(
                        () -> {
                            try {
                                updateUser(curName, newName, newPass);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                )
        ).start();
    }

    private void updateUser(String curName, String newName, String newPass) throws IOException, ClassNotFoundException {
        SocketConnector admin = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("updateUser", "admin"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream1.writeObject(curName + "," + newName + "," + newPass);
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(admin.getSocket().getInputStream());
        String s = (String) objectInputStream.readObject();
        System.out.println(s);

        if (s.equals("done")) {
            Profile p = new Profile();
            p.setName(newName);
            p.setPassword(newPass);
            searchUserTxt.clear();
            newNameTxt.clear();
            newPasswordTxt.clear();
            new ShowAlert("CONFIRMATION", "Profile updated to :\n" + p);

        } else if (s.equals("exists")) {
            newNameTxt.clear();
            new ShowAlert("ERROR", "Profile with same name already exists! Enter another new User Name!");
        } else {
            searchUserTxt.clear();
            new ShowAlert("ERROR", "Profile was not found");
        }
    }

    public void onPressedToBack(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getAdminMenu(),"Admin Menu",actionEvent);
    }
}
