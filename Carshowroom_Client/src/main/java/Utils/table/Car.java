package Utils.table;

import javafx.beans.property.SimpleStringProperty;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Car {

    private  SimpleStringProperty reg;
    private  SimpleStringProperty make;
    private  SimpleStringProperty model;
    private  SimpleStringProperty yearmade;
    private  SimpleStringProperty color;
    private  SimpleStringProperty price;
    private byte[] img;
    private byte[] size;

    public Car(String reg, String make, String model,int yearmade,String Color, String imgloc) throws IOException {
        this.reg = new SimpleStringProperty(reg);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.yearmade = new SimpleStringProperty(String.valueOf(yearmade));
        this.color = new SimpleStringProperty(Color);
        this.price = new SimpleStringProperty(String.valueOf(price));

        BufferedImage image = ImageIO.read(new File(imgloc));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        this.size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        this.img = byteArrayOutputStream.toByteArray();
    }


    public Car(Utils.Car car) throws IOException {
        this.reg = new SimpleStringProperty(car.getReg());
        this.make = new SimpleStringProperty(car.getMake());
        this.model = new SimpleStringProperty(car.getModel());
        this.yearmade = new SimpleStringProperty(String.valueOf(car.getYearmade()));
        this.color = new SimpleStringProperty(car.getColor());
        this.price = new SimpleStringProperty(String.valueOf(car.getPrice()));
        this.img = car.getImg();
    }

    public String getReg() {
        return reg.get();
    }

    public SimpleStringProperty regProperty() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg.set(reg);
    }

    public String getMake() {
        return make.get();
    }

    public SimpleStringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getYearmade() {
        return yearmade.get();
    }

    public SimpleStringProperty yearmadeProperty() {
        return yearmade;
    }

    public void setYearmade(String yearmade) {
        this.yearmade.set(yearmade);
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public  String toString()
    {
        return "Registration No : "+getReg()+"\nCar Make : "+getMake()+"\nCar Model : "+getModel()+"\nYear Made : "+getYearmade()+"\nColor : "+getColor()+"\nPrice : "+getPrice();
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public byte[] getSize() {
        return size;
    }

    public void setSize(byte[] size) {
        this.size = size;
    }
}