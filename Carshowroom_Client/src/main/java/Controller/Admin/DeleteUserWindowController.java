package Controller.Admin;


import Client.SocketConnector;
import Utils.FxmlLoc;
import Utils.Profile;
import Utils.ShowAlert;
import Utils.ShowWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        new ShowWindow(FxmlLoc.getAdminMenu(),"Admin Menu",actionEvent);
    }
}
