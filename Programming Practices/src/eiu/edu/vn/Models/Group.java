package eiu.edu.vn.Models;


import eiu.edu.vn.DataStore.DataStore;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
    public DataStore dataStore;
    private String nameGroup;
    private ArrayList<User> groupMembers;
    private ArrayList<Message> messages;
    private String ownerUser;
    private UUID id;

    public Group() {
        this.nameGroup = nameGroup;
        this.messages = new ArrayList<Message>();
        this.groupMembers = new ArrayList<User>();
        this.ownerUser = ownerUser;
        this.dataStore = new DataStore();
    }

    public Group(UUID id, String nameGroup, String ownerUser) {
        this.id = id;
        this.nameGroup = nameGroup;
        this.groupMembers = new ArrayList<User>();
        this.messages = new ArrayList<Message>();
        this.ownerUser = ownerUser;
        this.dataStore = new DataStore();
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

    public void addMember(User user) {
        groupMembers.add(user);
    }
}
