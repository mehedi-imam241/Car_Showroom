package Utils;

import Entity.ProfileEntity;

import java.io.Serializable;


public class Profile implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String password;
    private String isSuccess;
    private String task;
    private String role;

    public Profile() {

    }

    public Profile( String task, String role) {
        this.name = "dummy";
        this.password = "dummy";
        this.isSuccess = "true";
        this.task = task;
        this.role = role;
    }
    public Profile(ProfileEntity p)
    {
        this.name = p.getName();
        this.password = p.getPassword();
        this.isSuccess = "true";
        this.task = "dummy";
        this.role = "dummy";
    }
    public Profile(String name, String password, String isSuccess, String task, String role) {
        this.name = name;
        this.password = password;
        this.isSuccess = isSuccess;
        this.task = task;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
