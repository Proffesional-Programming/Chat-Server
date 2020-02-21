package eiu.edu.vn.DataStore;
import eiu.edu.vn.Models.Group;
import eiu.edu.vn.Models.Message;
import eiu.edu.vn.Models.User;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public List<User> lstUser;
    public ArrayList<Group> lstGroup;

    public DataStore() {
        this.lstUser = new ArrayList<User>();
        this.lstGroup = new ArrayList<Group>();
    }
}
