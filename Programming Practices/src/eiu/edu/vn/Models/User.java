package eiu.edu.vn.Models;

import eiu.edu.vn.Controller.UserController;
import eiu.edu.vn.Services.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class User extends Text {
    private UUID id;
    private String userName;
    private String password;
    private String lastName;
    private String firstName;
    private String hashPassword;
    private ArrayList<Message> lstMessage = new ArrayList<Message>();
    private String fullName;
    private ArrayList<Group> groups = new ArrayList<Group>();
    private UserController userController;
    private String path = "./eiu/edu/vn/DataStore/";

    public User(UUID id, String userName, String hashPassword, ArrayList<Message> lstMessage) {
        super(lstMessage);
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.userController = new UserController();
        this.path += id;
        crtFolder(this.path);
    }

    public User(UUID id, String userName, String password, String hashPassword, String fullName, ArrayList<Message> lstMessage) {
        super(lstMessage);
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.hashPassword = hashPassword;
        this.fullName = fullName;
        this.userController = new UserController();
        this.path += id;
        crtFolder(this.path);
    }

    public String getPath() {
        return path;
    }

    private boolean crtFolder(String path) {
        File file = new File(path);
        boolean check = file.mkdir();
        return check;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getFullName() {
        return fullName = lastName + " " + firstName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
