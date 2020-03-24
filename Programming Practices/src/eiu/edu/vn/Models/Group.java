package eiu.edu.vn.Models;


import eiu.edu.vn.DataStore.DataStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group extends Text {
    public DataStore dataStore;
    private String nameGroup;
    private ArrayList<User> groupMembers;
    private String ownerUser;
    private UUID id;
    private String path = "./eiu/edu/vn/DataStore/";

    public Group(UUID id, String nameGroup, String ownerUser, ArrayList<Message> lstMessage) {
        super(lstMessage);
        this.id = id;
        this.nameGroup = nameGroup;
        this.groupMembers = new ArrayList<User>();
        this.ownerUser = ownerUser;
        this.dataStore = new DataStore();
        this.path += id;
        crtFolder(this.path);
    }

    private boolean crtFolder(String path) {
        File file = new File(path);
        boolean check = file.mkdir();
        return check;
    }

    public String getPath() {
        return path;
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
