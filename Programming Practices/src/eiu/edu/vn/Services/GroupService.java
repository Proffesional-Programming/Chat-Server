package eiu.edu.vn.Services;

import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.Group;

import java.util.ArrayList;


public class GroupService {
    public DataStore dataStore;

    public GroupService() {
        this.dataStore = new DataStore();
    }

    public void addGroup(Group group) {
        dataStore.lstGroup.add(group);
    }

    public ArrayList lstGroup() {
        return dataStore.lstGroup;
    }
}
