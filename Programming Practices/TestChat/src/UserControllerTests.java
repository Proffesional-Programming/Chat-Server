import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.Message;
import eiu.edu.vn.Models.PublicGroup;
import eiu.edu.vn.Models.User;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;


public class UserControllerTests {
    UserController userController = new UserController();
    DataStore dataStore = new DataStore();

    public UserControllerTests() throws NoSuchAlgorithmException {
    }

    @Test
    public void createGroup() throws NoSuchAlgorithmException {
//        ArrayList<Message> messages=new ArrayList<Message>();
//        this.userController.createGroup(new User(UUID.randomUUID(),"bao","admin",messages),"Group1","Bao",true);
//        boolean test = this.userController.createGroup(new User(UUID.randomUUID(),"bao","admin",),"Group1","Bao",true);
//        Assert.assertTrue(test);
    }
//
//    @Test
//    public void inviteFriend() throws NoSuchAlgorithmException {
//
//        this.userController.inviteFriend("bao", ArrayList<User>() = new Group(UUID.randomUUID(),"Group1","bao"));
//        boolean test = this.userController.inviteFriend("bao",new Group(UUID.randomUUID(),"Group1","bao"));
//        Assert.assertTrue(test);
//    }

    @Test void joinGroup() throws  NoSuchAlgorithmException {
        ArrayList<Message> messages = new ArrayList<Message>();
        this.userController.joinGroup("123", new User(UUID.randomUUID(), "bao", "admin", messages), new PublicGroup(UUID.randomUUID(), "Group1", "bao", messages, "123"));
        boolean test = this.userController.joinGroup("123", new User(UUID.randomUUID(), "bao", "admin", messages), new PublicGroup(UUID.randomUUID(), "Group1", "bao", messages, "123"));
        Assert.assertTrue(test);
    }

}