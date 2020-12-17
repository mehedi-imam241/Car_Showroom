package Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CarEntity {
    @Id
    private String reg;
    private String make;
    private String model;
    private int yearmade;
    private String color;
    private int price;
    private String imageloc;

    public CarEntity()
    {
        reg = "null";
        reg = "null";
        make = "null";
        model = "null";
        yearmade = 0;
        color = "null";
        price = 0;
    }

    public CarEntity(String reg, String make, String model, int yearmade, String color, int price, String imageloc) {
        this.reg = reg;
        this.make = make;
        this.model = model;
        this.yearmade = yearmade;
        this.color = color;
        this.price = price;
        this.imageloc = imageloc;
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

    public String getImageloc() {
        return imageloc;
    }

    public void setImageloc(String imageloc) {
        this.imageloc = imageloc;
    }

    public  String toString()
    {
        return "Registration No : "+getReg()+"\nCar Make : "+getMake()+"\nCar Model : "+getModel()+"\nYear Made : "+getYearmade()+"\nColor : "+getColor()+"\nPrice : "+getPrice();
    }
}