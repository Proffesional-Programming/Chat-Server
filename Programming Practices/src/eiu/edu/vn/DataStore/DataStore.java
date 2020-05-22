package eiu.edu.vn.DataStore;
import eiu.edu.vn.Models.*;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore uniqInstance;
    public static ArrayList<User> lstUser;
    public static ArrayList<PublicGroup> lstPubGroup;
    public static ArrayList<PrivateGroup> lstPriGroup;

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

    public static ArrayList<User> getLstUser() {
        return lstUser;
    }

    public static ArrayList<PublicGroup> getLstPubGroup() {
        return lstPubGroup;
    }

    public static ArrayList<PrivateGroup> getLstPriGroup() {
        return lstPriGroup;
    }
}
