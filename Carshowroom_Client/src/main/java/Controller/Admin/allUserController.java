package Controller.Admin;

import Client.SocketConnector;
import Utils.ShowAlert;
import Utils.table.Profile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class allUserController {
    @FXML
    public Button toBack;
    @FXML
    public Button loadAllUser;
    @FXML
    public TableView<Profile> tableView;
    @FXML
    public TableColumn<Profile,String> userNameColumn;
    @FXML
    public TableColumn<Profile,String> passwordColumn;

    ObservableList<Profile> data = FXCollections.observableArrayList();

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

    public void onPressedLoadAllUser(ActionEvent actionEvent){
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        Thread newThread = new Thread(
                () -> Platform.runLater(() -> {
                    try {
                        load();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                })
        );

        newThread.start();

    }

    public void load() throws IOException, ClassNotFoundException {

        SocketConnector admin = new SocketConnector();
        Utils.Profile p = new Utils.Profile("getUsers","admin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream.writeObject(p);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(admin.getSocket().getInputStream());

        List<Utils.Profile> list = (List<Utils.Profile>) objectInputStream.readObject();

        if(list.isEmpty())
        {
            new ShowAlert("ERROR","User database is empty. Please add user !");
        }
        data.clear();
        for(Utils.Profile profile:list)
        {
            data.add(new Utils.table.Profile(profile.getName(),profile.getPassword()));
        }
        tableView.setItems(data);
        loadAllUser.setText("Refresh");
    }
}
