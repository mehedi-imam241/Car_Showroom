package Controller.Viewer;


import Client.SocketConnector;
import Utils.*;
import Utils.Car;
import Utils.Profile;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class SearchByRegController {
    @FXML
    public TextField regNoTxt;
    @FXML
    public Button searchBtn;
    @FXML
    public ImageView carImageView;
    @FXML
    public Button toBack;
    @FXML
    public TextArea carDetailTxt;
    private String reg;

    public void onPressedSearch(ActionEvent actionEvent) throws IOException {
        reg = regNoTxt.getText();
        if(reg.isEmpty())
        {
            new ShowAlert("ERROR","Enter registration number");
            return;
        }

        new Thread(() -> Platform.runLater(
                () -> {
                    try {
                        checkWithReg();
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        )).start();
    }

    private void checkWithReg () throws IOException, ClassNotFoundException, InterruptedException {
        SocketConnector client = new SocketConnector();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream.writeObject(new Profile("findCarWithReg", "viewer"));
        objectOutputStream.flush();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(client.getSocket().getOutputStream());
        objectOutputStream1.writeObject(reg);
        objectOutputStream1.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(client.getSocket().getInputStream());
        if(((String) objectInputStream.readObject()).equals("true"))
        {
            ObjectInputStream objectInputStream1 = new ObjectInputStream(client.getSocket().getInputStream());
            Car car =(Car) objectInputStream1.readObject();
            System.out.println(car.toString());
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(car.getImg()));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            carImageView.setImage(image);
            carDetailTxt.setText(car.toString());
        }
        else {
            regNoTxt.clear();
            carImageView.setImage(null);
            carDetailTxt.clear();
            new ShowAlert("ERROR","No car with this registration number");
        }
    }

    public void onPressedToBack(ActionEvent actionEvent) {
        new ShowWindow(FxmlLoc.getViewerMenu(),"Viewer Menu",actionEvent);
    }
}
