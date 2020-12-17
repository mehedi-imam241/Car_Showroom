package App;

import Connection.Admin.Login;
import Connection.Admin.AdminTask;
import Connection.Manufacturer.ManufacturerLogin;
import Connection.Manufacturer.ManufacturerTask;
import Entity.CarEntity;
import Entity.ProfileEntity;
import Utils.Car;
import Utils.Profile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server extends Thread {

    private ServerSocket ss;

    public Server() throws IOException {
        ss = new ServerSocket(7777);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("ServerSocket awaiting connections...");
                Socket socket = ss.accept();
                System.out.println("Connection from " + socket + "!");


                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Profile profile = (Profile) objectInputStream.readObject();

                if (profile.getRole().equals("admin")) {
                    if (profile.getTask().equals("login")) {
                        Login login = new Login();
                        if (!login.isSuccessful(profile.getName(), profile.getPassword())) {
                            profile.setIsSuccess("false");
                        }
                        objectOutputStream.writeObject(profile);
                        objectOutputStream.flush();
                    } else if (profile.getTask().equals("getUsers")) {
                        AdminTask t = new AdminTask();
                        List<ProfileEntity> list = t.getAllUser();

                        List<Profile> plist = new ArrayList<>();

                        for (ProfileEntity p : list) {
                            plist.add(new Profile(p));
                        }

                        objectOutputStream.writeObject(plist);
                        objectOutputStream.flush();
                    } else if (profile.getTask().equals("addUser")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        Profile p = (Profile) objectInputStream1.readObject();
                        AdminTask t = new AdminTask();
                        if (!t.addUser(p.getName(), p.getPassword())) {
                            p.setIsSuccess("false");
                        }
                        objectOutputStream.writeObject(p);
                        objectOutputStream.flush();
                    } else if (profile.getTask().equals("deleteUser")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String s = (String) objectInputStream1.readObject();
                        AdminTask t = new AdminTask();
                        if (t.deleteUser(s)) {
                            s = "done";
                        }
                        objectOutputStream.writeObject(s);
                        objectOutputStream.flush();
                    } else if (profile.getTask().equals("updateUser")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String[] arrOfStr = ((String) objectInputStream1.readObject()).split(",");

                        String curName = arrOfStr[0];
                        String newName = arrOfStr[1];
                        String newPass = arrOfStr[2];

                        String s;
                        AdminTask t = new AdminTask();
                        if (t.findUser(newName)) {
                            s = "exists";
                        }
                        else if (t.updateUser(curName, newName, newPass)) {
                            s = "done";
                        } else {
                            s = "not found";
                        }
                        objectOutputStream.writeObject(s);
                        objectOutputStream.flush();
                    }
                } else if (profile.getRole().equals("viewer")) {
                    if (profile.getTask().equals("searchWithMake&Model")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        ArrayList<String> arr = (ArrayList<String>) objectInputStream1.readObject();

                        String make = arr.get(0);
                        String model = arr.get(1);

                        ManufacturerTask t = new ManufacturerTask();
                        List<CarEntity> list = t.findCarWithMakeAndModel(make, model);
                        List<Car> nList = new ArrayList<>();

                        for (CarEntity car : list) {
                            nList.add(new Car(car.getReg(), car.getMake(), car.getModel(), car.getYearmade(), car.getColor(), car.getPrice(), car.getImageloc()));
                        }

                        objectOutputStream.writeObject(nList);
                        objectOutputStream.flush();
                    }
                    else if (profile.getTask().equals("findCarWithReg")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String reg = (String) objectInputStream1.readObject();
                        ManufacturerTask t = new ManufacturerTask();
                        CarEntity car = t.findCarWithReg(reg);
                        if (car == null) {
                            objectOutputStream.writeObject("false");
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject("true");
                            objectOutputStream.flush();
                            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream1.writeObject(new Car(car.getReg(), car.getMake(), car.getModel(), car.getYearmade(), car.getColor(), car.getPrice(), car.getImageloc()));
                            objectOutputStream1.flush();
                        }
                    }
                    else if (profile.getTask().equals("view")) {
                        ManufacturerTask t = new ManufacturerTask();
                        List<CarEntity> list = t.getAllCar();
                        List<Car> nList = new ArrayList<>();
                        for (CarEntity car : list) {
                            nList.add(new Car(car));
                        }
                        objectOutputStream.writeObject(nList);
                        objectOutputStream.flush();
                    } else if (profile.getTask().equals("buy")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String reg = (String) objectInputStream1.readObject();
                        ManufacturerTask t = new ManufacturerTask();
                        CarEntity car = t.findCarWithReg(reg);

                        if (car == null) {
                            objectOutputStream.writeObject("false");
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject("true");
                            objectOutputStream.flush();
                            t.deleteCar(reg);
                            File file = new File(car.getImageloc());
                            file.delete();
                        }
                        t.deleteCar(reg);
                    }
                } else {
                    if (profile.getTask().equals("login")) {
                        ManufacturerLogin login = new ManufacturerLogin();
                        if (!login.isSuccessful(profile.getName(), profile.getPassword())) {
                            profile.setIsSuccess("false");
                        }
                        objectOutputStream.writeObject(profile);
                        objectOutputStream.flush();
                    }
                    else if (profile.getTask().equals("addCar")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        Car car = (Car) objectInputStream1.readObject();
                        ManufacturerTask t = new ManufacturerTask();
                        String res = "not done";
                        if (t.addCar(car.getReg(), car.getMake(), car.getModel(), car.getYearmade(), car.getColor(), car.getPrice(), System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + car.getReg() + ".jpg")) {
                            File outputImage = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + car.getReg() + ".jpg");
                            BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(car.getImg()));
                            ImageIO.write(bImage, "jpg", outputImage);
                            res = "done";
                        }
                        objectOutputStream.writeObject(res);
                        objectOutputStream.flush();
                    }
                    else if (profile.getTask().equals("delete")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String reg = (String) objectInputStream1.readObject();
                        ManufacturerTask t = new ManufacturerTask();
                        CarEntity car = t.findCarWithReg(reg);

                        if (car == null) {
                            objectOutputStream.writeObject("false");
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject("true");
                            objectOutputStream.flush();
                            t.deleteCar(reg);
                            File file = new File(car.getImageloc());
                            file.delete();
                        }
                        t.deleteCar(reg);
                    }
                    else if (profile.getTask().equals("findCarWithReg")) {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String reg = (String) objectInputStream1.readObject();
                        ManufacturerTask t = new ManufacturerTask();
                        CarEntity car = t.findCarWithReg(reg);
                        if (car == null) {
                            objectOutputStream.writeObject("false");
                            objectOutputStream.flush();
                        } else {
                            objectOutputStream.writeObject("true");
                            objectOutputStream.flush();
                            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream1.writeObject(new Car(car.getReg(), car.getMake(), car.getModel(), car.getYearmade(), car.getColor(), car.getPrice(), car.getImageloc()));
                            objectOutputStream1.flush();
                        }
                    }
                    else if(profile.getTask().equals("edit"))
                    {
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        String reg = (String) objectInputStream1.readObject();

                        ObjectInputStream objectInputStream2 = new ObjectInputStream(socket.getInputStream());
                        Car car = (Car) objectInputStream2.readObject();
                        ManufacturerTask t = new ManufacturerTask();

                        CarEntity cNewReg = t.findCarWithReg(car.getReg());
                        CarEntity c= t.findCarWithReg(reg);
                        if (cNewReg != null) {
                            objectOutputStream.writeObject("duplicate");
                        }
                        else if (t.updateCar(reg,car.getReg(), car.getMake(), car.getModel(), car.getYearmade(), car.getColor(), car.getPrice(), System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + car.getReg() + ".jpg")) {
                            File prvImage = new File(c.getImageloc());
                            prvImage.delete();

                            File outputImage = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\images\\" + car.getReg() + ".jpg");
                            BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(car.getImg()));
                            ImageIO.write(bImage, "jpg", outputImage);
                            objectOutputStream.writeObject("done");
                        }
                        else {
                            objectOutputStream.writeObject("noCar");
                        }
                        objectOutputStream.flush();
                    }
                }

                //System.out.println(profile.getPassword()+profile.getPassword());


                // Do necessary work here ---->>>>
                //Add functionality
                //objectOutputStream.writeObject();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Thread Sthread = new Server();
        Sthread.start();
    }
}