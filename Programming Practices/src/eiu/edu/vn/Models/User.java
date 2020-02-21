package eiu.edu.vn.Models;

import eiu.edu.vn.Controller.UserController;
import eiu.edu.vn.Services.UserService;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private UUID id;
    private String userName;
    private String password;
    private String lastName;
    private String firstName;
    private String hashPassword;
    private String fullName;
    public ArrayList<Group> groups = new ArrayList<Group>();
    public UserController userController;

    public User(UUID id, String userName, String hashPassword) {
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        userController = new UserController();
    }

    public User(UUID id, String userName, String password, String hashPassword, String fullName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.hashPassword = hashPassword;
        this.fullName = fullName;
        userController = new UserController();
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
