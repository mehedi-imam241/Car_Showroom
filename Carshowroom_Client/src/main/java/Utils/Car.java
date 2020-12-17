package Utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Car implements Serializable {

    private static final long serialVersionUID = 6529685098267757693L;
    private String reg;
    private String make;
    private String model;
    private int yearmade;
    private String color;
    private int price;
    private byte[] img;

    public Car(String reg, String make, String model, int yearmade, String color, int price, String imageloc) throws IOException {
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.yearmade = yearmade;
        this.color = color;
        this.price = price;

        File file = new File(imageloc);
        FileInputStream fileInputStream = new FileInputStream(file.getPath());
        setSize((int)file.length());
        fileInputStream.read(img,0,img.length);
        fileInputStream.close();
    }

    public Car(String reg, String make, String model, int yearmade, String color, int price, BufferedImage bImage) throws IOException {
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.yearmade = yearmade;
        this.color = color;
        this.price = price;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", byteArrayOutputStream);
        this.img = byteArrayOutputStream.toByteArray();
    }

    private void setSize(int size)
    {
        img = new byte[size];
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearmade() {
        return yearmade;
    }

    public void setYearmade(int yearmade) {
        this.yearmade = yearmade;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public  String toString()
    {
        return "Registration No : "+getReg()+"\nCar Make : "+getMake()+"\nCar Model : "+getModel()+"\nYear Made : "+getYearmade()+"\nColor : "+getColor()+"\nPrice : "+getPrice()+"\n";
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

}