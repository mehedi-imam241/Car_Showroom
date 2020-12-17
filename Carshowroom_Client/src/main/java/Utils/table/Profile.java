package Utils.table;

import javafx.beans.property.SimpleStringProperty;



public class Profile  {

    private final SimpleStringProperty name;
    private final SimpleStringProperty password;


    public Profile(String name, String password) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String nm) {
        name.set(nm);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String psd) {
        password.set(psd);
    }
    public String toString(){
        return "Name: "+getName()+"\n"+"Password: "+getPassword()+"\n";
    }
}
