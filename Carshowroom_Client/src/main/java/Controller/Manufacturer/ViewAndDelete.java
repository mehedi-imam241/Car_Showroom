package Controller.Manufacturer;


import Client.SocketConnector;
import Utils.FxmlLoc;
import Utils.Profile;
import Utils.ShowAlert;
import Utils.ShowWindow;
import Utils.table.Car;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewAndDelete {
    @FXML
    public Button loadCar;
    @FXML
    public TableColumn<Car, String> carRegColumn;
    @FXML
    public TableColumn<Car, String> carMakeColumn;
    @FXML
    public TableColumn<Car, String> CarModelColumn;
    @FXML
    public TableColumn<Car, String> yearMadeColumn;
    @FXML
    public TableColumn<Car, String> colorCol;
    @FXML
    public TableColumn<Car, String> priceColumn;
    @FXML
    public Button toBack;
    @FXML
    public ImageView carView;
    @FXML
    public TableView<Car> carTable;
    @FXML
    public TableColumn<Car, String> viewCol;
    @FXML
    public TableColumn<Car, String> deleteCol;

    public void onPressedLoadCar(ActionEvent actionEvent) {
        new Thread(
                () -> Platform.runLater(
                        () -> {
                            try {
                                viewAndDelete();
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                )
        ).start();
    }


    private void viewAndDelete() throws IOException, ClassNotFoundException {
        SocketConnector client = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("view", "viewer"));
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
        List<Utils.Car> list = (List<Utils.Car>) objectInputStream.readObject();

        if (list.isEmpty()) {
            new ShowAlert("CONFIRMATION", "No Car found");
        } else {
            List<Car> nList = new ArrayList<>();
            for (Utils.Car c : list) {
                nList.add(new Car(c));
            }

            ObservableList<Car> data = FXCollections.observableArrayList(nList);
            carTable.setItems(data);

            carRegColumn.setCellValueFactory(new PropertyValueFactory<>("reg"));
            carMakeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
            CarModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
            yearMadeColumn.setCellValueFactory(new PropertyValueFactory<>("yearmade"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            viewCol.setCellValueFactory(new PropertyValueFactory<>("Dummy1"));
            deleteCol.setCellValueFactory(new PropertyValueFactory<>("Dummy2"));

            Callback<TableColumn<Car, String>, TableCell<Car, String>> viewCellFactory
                    = new Callback<TableColumn<Car, String>, TableCell<Car, String>>() {
                @Override
                public TableCell call(final TableColumn<Utils.table.Car, String> param) {
                    return new TableCell<Utils.table.Car, String>() {

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
                                        carView.setImage(image);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            };
            viewCol.setCellFactory(viewCellFactory);

            Callback<TableColumn<Utils.table.Car, String>, TableCell<Utils.table.Car, String>> deleteCellFactory
                    = new Callback<TableColumn<Utils.table.Car, String>, TableCell<Utils.table.Car, String>>() {
                @Override
                public TableCell call(final TableColumn<Utils.table.Car, String> param) {
                    return new TableCell<Utils.table.Car, String>() {

                        final Button btn = new Button("Delete");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    Utils.table.Car car = getTableView().getItems().get(getIndex());
                                    try {
                                        delete(car);
                                        data.remove(car);
                                    } catch (IOException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            };
            deleteCol.setCellFactory(deleteCellFactory);
        }
        loadCar.setText("Refresh");
    }

    private void delete(Car car) throws IOException, ClassNotFoundException{
        SocketConnector nClient = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(nClient.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("delete", "manufacturer"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(nClient.getSocket().getOutputStream());
        objectOutputStream1.writeObject(car.getReg());
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(nClient.getSocket().getInputStream());
        String res = (String) objectInputStream.readObject();

        if (res.equals("true")) {
            new ShowAlert("CONFIRMATION", "You deleted the car :\n" + car.toString());
        } else {
            new ShowAlert("ERROR", "Sorry! The car with\n" + car.toString() + "\nis no longer available.\nPlease refresh to stay updated !");
        }
        carView.setImage(null);
    }

    public void onPressedToBack(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getManufacturerMenu(),"Manufacturer Menu",actionEvent);
    }
}
