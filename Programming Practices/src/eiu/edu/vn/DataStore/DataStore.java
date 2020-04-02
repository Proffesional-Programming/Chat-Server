package eiu.edu.vn.DataStore;
import eiu.edu.vn.Models.*;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public ArrayList<User> lstUser;
    public ArrayList<PublicGroup> lstPubGroup;
    public ArrayList<PrivateGroup> lstPriGroup;

    public DataStore() {
        this.lstUser = new ArrayList<User>();
        this.lstPriGroup = new ArrayList<PrivateGroup>();
        this.lstPubGroup = new ArrayList<PublicGroup>();
    }

}
