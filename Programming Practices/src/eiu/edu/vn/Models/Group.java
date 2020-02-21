package eiu.edu.vn.Models;


import eiu.edu.vn.DataStore.DataStore;

import java.util.ArrayList;
import java.util.List;

public abstract class Group {
    public DataStore dataStore;
    private String nameGroup;
    private ArrayList<User> groupMembers;
    private ArrayList<Message> messages;
    private String ownerUser;

    public Group() {
        this.nameGroup = nameGroup;
        this.messages = new ArrayList<Message>();
        this.groupMembers = new ArrayList<User>();
        this.ownerUser = ownerUser;
        this.dataStore = new DataStore();
    }

    public Group(String nameGroup, String ownerUser) {
        this.nameGroup = nameGroup;
        this.groupMembers = new ArrayList<User>();
        this.messages = new ArrayList<Message>();
        this.ownerUser = ownerUser;
        this.dataStore = new DataStore();
    }

    public boolean checkUserExist(User user) {
        if (dataStore.lstUser.contains(user)) {
            return true;
        }
        return false;
    }

    public int getSize() {
        return groupMembers.size();
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(String ownerUser) {
        this.ownerUser = ownerUser;
    }

}
