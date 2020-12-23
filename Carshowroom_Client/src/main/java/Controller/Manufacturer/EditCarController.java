package Controller.Manufacturer;

import Client.SocketConnector;
import Utils.*;
import Utils.Car;
import Utils.Profile;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class EditCarController {
    @FXML
    public TextField newRegTxt;
    @FXML
    public TextField newCarMakeTxt;
    @FXML
    public TextField newPriceTxt;
    @FXML
    public TextField newCarModelTxt;
    @FXML
    public TextField newYearMadeTxt;
    @FXML
    public TextField newColorTxt;
    @FXML
    public Button EditCarBtn;
    @FXML
    public Button ToBack;
    @FXML
    public TextField searchRegTxt;
    @FXML
    public Button selectImgBtn;
    @FXML
    public Button searchBtn;
    @FXML
    public ImageView carView;


    private BufferedImage bImage = null;
    private String reg;

    public void onPressedSearch(ActionEvent actionEvent)  {
        reg = searchRegTxt.getText();

        if (reg.isEmpty()) {
            new ShowAlert("Error", "Registration field is Empty. Please fill it");
            return;
        }

        new Thread(() -> Platform.runLater(
                () -> {
                    try {
                        Search();
                    } catch (IOException | ClassNotFoundException  e) {
                        e.printStackTrace();
                    }
                }
        )).start();
    }


    private void Search() throws IOException, ClassNotFoundException {
        SocketConnector client = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("findCarWithReg", "manufacturer"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream1.writeObject(reg);
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
        String res = (String) objectInputStream.readObject();

        if (res.equals("true")) {
            ObjectInputStream objectInputStream1 = new ObjectInputStream(client.getSocket().getInputStream());
            Car car = (Car) objectInputStream1.readObject();
            System.out.println(car.toString());
            newRegTxt.setText(car.getReg());
            newCarMakeTxt.setText(car.getMake());
            newCarModelTxt.setText(car.getModel());
            newPriceTxt.setText(String.valueOf(car.getPrice()));
            newYearMadeTxt.setText(String.valueOf(car.getYearmade()));
            newColorTxt.setText(car.getColor());

            bImage= ImageIO.read(new ByteArrayInputStream(car.getImg()));
            Image image = SwingFXUtils.toFXImage(bImage, null);
            carView.setImage(image);
        } else {
            bImage = null;
            carView.setImage(null);
            searchRegTxt.clear();
            newRegTxt.clear();
            newCarMakeTxt.clear();
            newCarModelTxt.clear();
            newPriceTxt.clear();
            newYearMadeTxt.clear();
            newColorTxt.clear();
            new ShowAlert("ERROR", "No car found");
        }
    }


    public void onPressedEditCar(ActionEvent actionEvent) throws IOException {

        if (bImage == null) {
            new ShowAlert("ERROR", "Please Select an Image!");
            return;
        }

        if (searchRegTxt.getText().isEmpty() || newRegTxt.getText().isEmpty() || newCarMakeTxt.getText().isEmpty() || newCarModelTxt.getText().isEmpty() || newYearMadeTxt.getText().isEmpty() || newColorTxt.getText().isEmpty() || newPriceTxt.getText().isEmpty()) {
            new ShowAlert("ERROR", "Empty field exists. Please fill up! ");
            return;
        }

        if (!isInt(newYearMadeTxt.getText())) {
            newYearMadeTxt.clear();
            new ShowAlert("ERROR", "Year Made should be number");
            return;
        }
        if (!isInt(newPriceTxt.getText())) {
            newPriceTxt.clear();
            new ShowAlert("ERROR", "Price should be number");
            return;
        }

        if (newPriceTxt.getText().length()>9) {
            newPriceTxt.clear();
            new ShowAlert("ERROR", "Price provided is too long");
            return;
        }

        if (newYearMadeTxt.getText().length()>4) {
            newYearMadeTxt.clear();
            new ShowAlert("ERROR", "Year [rovided is too long");
            return;
        }

        reg = searchRegTxt.getText();
        String newReg = newRegTxt.getText();
        String make = newCarMakeTxt.getText();
        String model = newCarModelTxt.getText();
        String color = newColorTxt.getText();
        int year = Integer.parseInt(newYearMadeTxt.getText());
        int price = Integer.parseInt(newPriceTxt.getText());
        Car car = new Car(newReg, make, model, year, color, price, bImage);


        new Thread(
                () -> Platform.runLater(
                        () -> {
                            try {
                                editCar(car);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                )
        ).start();
    }

    private void editCar(Car car) throws IOException, ClassNotFoundException {

        SocketConnector client = new SocketConnector();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("edit", "manufacturer"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream1.writeObject(reg);
        objectOutputStream1.flush();

        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream2.writeObject(car);
        objectOutputStream2.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
        String res = (String) objectInputStream.readObject();


        if (res.equals("done")) {
                bImage = null;
                carView.setImage(null);
                searchRegTxt.clear();
                newRegTxt.clear();
                newCarMakeTxt.clear();
                newCarModelTxt.clear();
                newPriceTxt.clear();
                newYearMadeTxt.clear();
                newColorTxt.clear();

                new ShowAlert("CONFIRMATION", "Car has been successfully updated to database.");

            }
            else if (res.equals("noCar")) {
                searchRegTxt.clear();
                new ShowAlert("ERROR", "No Car found with such registration number.");
            } else {
                newRegTxt.clear();
                new ShowAlert("ERROR", "Car with same registration number already exists. Try another new registration number.");
            }

        }

        public void onPressedSelectImg (ActionEvent actionEvent) throws IOException {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            File file = fileChooser.showOpenDialog(((Node) (actionEvent.getSource())).getScene().getWindow());

            if (file != null) {
                bImage = ImageIO.read(file);
                if (bImage == null) {
                    new ShowAlert("ERROR", "This is not an Image");
                }
                Image image = SwingFXUtils.toFXImage(bImage, null);
                carView.setImage(image);
            }
        }

        public void OnPressedToBack (ActionEvent actionEvent){
            new ShowWindow(FxmlLoc.getManufacturerMenu(),"Manufacturer Menu",actionEvent);
        }

        private boolean isInt (String str){
            try {
                Long.parseLong(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
