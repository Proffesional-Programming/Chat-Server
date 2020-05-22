package eiu.edu.vn.DataStore;
import eiu.edu.vn.Models.*;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore uniqInstance;
    public ArrayList<User> lstUser;
    public ArrayList<PublicGroup> lstPubGroup;
    public ArrayList<PrivateGroup> lstPriGroup;

    public DataStore() {
        this.lstUser = new ArrayList<User>();
        this.lstPriGroup = new ArrayList<PrivateGroup>();
        this.lstPubGroup = new ArrayList<PublicGroup>();
    }

    public static DataStore getInstance() {
        if (uniqInstance == null)
            uniqInstance = new DataStore();
        return uniqInstance;
    }

    public ArrayList<User> getLstUser() {
        return this.lstUser;
    }

    public ArrayList<PublicGroup> getLstPubGroup() {
        return lstPubGroup;
    }

    public ArrayList<PrivateGroup> getLstPriGroup() {
        return lstPriGroup;
    }
}
