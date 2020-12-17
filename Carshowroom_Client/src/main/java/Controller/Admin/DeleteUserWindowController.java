package Controller.Admin;


import Client.SocketConnector;
import Utils.Profile;
import Utils.ShowAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteUserWindowController {
    public TextField userName;
    public Button delete;
    public Button toBack;

    public void onPressedDelete(ActionEvent actionEvent) {

        String name = userName.getText();
        if(name.isEmpty())
        {
            new ShowAlert("ERROR","Enter User Name !");
            return;
        }

         new Thread(() -> Platform.runLater(() -> {
            try {
                deleteUser(name);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        })).start();
    }

    private void deleteUser(String name) throws IOException, ClassNotFoundException {

        SocketConnector admin = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("deleteUser", "admin"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream1.writeObject(name);
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(admin.getSocket().getInputStream());
        String s = (String) objectInputStream.readObject();

        if(s.equals("done"))
        {
            new ShowAlert("CONFIRMATION","Profile with user name : "+name+ " Deleted!");
        }
        else{
            new ShowAlert("ERROR","Profile not found!");
        }
        userName.clear();
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
