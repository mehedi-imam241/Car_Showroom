package Controller.Viewer;


import Client.SocketConnector;
import Utils.Profile;
import Utils.ShowAlert;
import Utils.table.Car;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchByCarMakeAndModel {
    @FXML
    public TableColumn<Car, String> carRegColumn;
    @FXML
    public TableColumn<Car, String> carMakeColumn;
    @FXML
    public TableColumn<Car, String> CarModelColumn;
    @FXML
    public TableColumn<Car, String> yearMadeColumn;
    @FXML
    public TableColumn<Car, String> priceColumn;
    @FXML
    public Button toBack;
    @FXML
    public TableColumn<Car, String> colorCol;
    @FXML
    public Button searchBtn;
    @FXML
    public TextField carMakeTxt;
    @FXML
    public TextField carModelTxt;
    @FXML
    public ImageView viewCar;
    @FXML
    public TableColumn<Car, String> actionCol;
    @FXML
    public TableView<Car> carTable;
    private String make, model;
    private List<Car> nList = new ArrayList<>();

    public void onPressedSearchBtn(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        make = carMakeTxt.getText();
        model = carModelTxt.getText();

        if (make.isEmpty() || model.isEmpty()) {
            new ShowAlert("ERROR", "Empty field exists");
        }

        new Thread(() -> Platform.runLater(
                () -> {
                    try {
                        search();
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        )).start();
    }


    private void search() throws IOException, ClassNotFoundException, InterruptedException {

        SocketConnector admin = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("searchWithMake&Model", "viewer"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(admin.getSocket().getOutputStream());
        objectOutputStream1.writeObject(new ArrayList<String>(Arrays.asList(make, model)));
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(admin.getSocket().getInputStream());
        List<Utils.Car> list = (List<Utils.Car>) objectInputStream.readObject();
        System.out.println(list);

        nList = new ArrayList<>();
        for (Utils.Car car : list) {
            nList.add(new Car(car));
        }
        if (nList.isEmpty()) {
            carMakeTxt.clear();
            carModelTxt.clear();

            new ShowAlert("ERROR", "No Car found");
            return;
        }


        ObservableList<Car> data = FXCollections.observableArrayList(nList);
        carTable.setItems(data);

        carRegColumn.setCellValueFactory(new PropertyValueFactory<>("reg"));
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        CarModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearMadeColumn.setCellValueFactory(new PropertyValueFactory<>("yearmade"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("Dummy"));


        Callback<TableColumn<Car, String>, TableCell<Car, String>> cellFactory
                = new Callback<TableColumn<Car, String>, TableCell<Car, String>>() {
            @Override
            public TableCell call(final TableColumn<Car, String> param) {
                final TableCell<Car, String> cell = new TableCell<Car, String>() {

                    final Button btn = new Button("View");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Car car = getTableView().getItems().get(getIndex());
                                try {
                                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(car.getImg()));
                                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                    viewCar.setImage(image);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        searchBtn.setText("Refresh");
    }

    public void onPressedToBack(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Viewer/viewerMenu.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Viewer Menu");
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



