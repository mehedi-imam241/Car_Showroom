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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddCarController {
    @FXML
    public TextField regTxt;
    @FXML
    public TextField carMakeTxt;
    @FXML
    public TextField PriceTxt;
    @FXML
    public TextField CarModelTxt;
    @FXML
    public TextField YearMadeTxt;
    @FXML
    public TextField ColorTxt;
    @FXML
    public Button addCarBtn;
    @FXML
    public Button toBack;
    @FXML
    public Button selectImgBtn;
    @FXML
    public ImageView carView;

    private BufferedImage bImage = null;
    private String make;
    private int price;
    private String model;
    private String color;
    private int year;
    private String reg;


    public void onPressedSelectImage(ActionEvent actionEvent) throws IOException {
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
                return;
            }
            carView.setImage(SwingFXUtils.toFXImage(bImage, null));
        }
    }


    public void onPressedAddCar(ActionEvent actionEvent)  {

        if (bImage == null) {
            new ShowAlert("ERROR", "Please Select an Image!");
            return;
        }

        if (regTxt.getText().isEmpty() || carMakeTxt.getText().isEmpty() || YearMadeTxt.getText().isEmpty() || CarModelTxt.getText().isEmpty() || ColorTxt.getText().isEmpty() || PriceTxt.getText().isEmpty()) {
            new ShowAlert("ERROR", "Empty field exists. Please fill up! ");
            return;
        }

        if (!isInt(PriceTxt.getText())) {
            PriceTxt.clear();
            new ShowAlert("ERROR", "Price should be number");
            return;
        }
        if (!isInt(YearMadeTxt.getText())) {
            YearMadeTxt.clear();
            new ShowAlert("ERROR", "Year Made should be number");
            return;
        }

        if (PriceTxt.getText().length()>9) {
            PriceTxt.clear();
            new ShowAlert("ERROR", "Price is too long");
            return;
        }
        if (YearMadeTxt.getText().length()>4) {
            YearMadeTxt.clear();
            new ShowAlert("ERROR", "Year is too long");
            return;
        }

        reg = regTxt.getText();
        make = carMakeTxt.getText();
        price = Integer.parseInt(PriceTxt.getText());
        model = CarModelTxt.getText();
        color = ColorTxt.getText();
        year = Integer.parseInt(YearMadeTxt.getText());


        new Thread(() -> Platform.runLater(
                () -> {
                    try {
                        addCar();
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                })).start();
    }



        private void addCar () throws IOException, ClassNotFoundException {

            SocketConnector client = new SocketConnector();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
            objectOutputStream.writeObject(new Profile("addCar", "manufacturer"));
            objectOutputStream.flush();

            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(client.getSocket().getOutputStream());
            objectOutputStream1.writeObject(new Car(reg, make, model, year, color, price, bImage));
            objectOutputStream1.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
            String res = (String) objectInputStream.readObject();


            Car c = new Car(reg, make, model, year, color, price, bImage);
            System.out.println(c.toString());

            if (res.equals("done")) {
                bImage = null;
                carView.setImage(null);

                carMakeTxt.clear();
                CarModelTxt.clear();
                PriceTxt.clear();
                YearMadeTxt.clear();
                ColorTxt.clear();

                new ShowAlert("CONFIRMATION", "Car has been successfully added to database");

            }
            else {
                new ShowAlert("ERROR", "Car with same registration number already exists!");
            }
            regTxt.clear();
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
