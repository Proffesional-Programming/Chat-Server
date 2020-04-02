import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.*;
import eiu.edu.vn.Services.UserService;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserServiceTests {
    UserService userService = new UserService();
    DataStore dataStore = new DataStore();
    ArrayList box = new ArrayList<Box>();

    public void generateUser() throws NoSuchAlgorithmException {
        userService.createUser("nam", "123");
        userService.createUser("bao", "123");
    }

    public UserServiceTests() throws NoSuchAlgorithmException {
    }

    @Test
    public void CreatUsers() throws NoSuchAlgorithmException {
        this.userService.createUser("bao", "123");
        boolean test = this.userService.createUser("nam", "123");
        Assert.assertTrue(test);
    }

    @Test
    public void LoginTests() throws NoSuchAlgorithmException {
        this.userService.createUser("bao", "123");
        boolean test = this.userService.createUser("nam", "123");
        Assert.assertTrue(test);
    }

    @Test
    public void CreateGroup() throws NoSuchAlgorithmException {
        generateUser();
        User user=userService.Login("nam","123");
        user.createGroup(user.getUserName(),"Hello",true);
        user.createGroup(user.getUserName(),"Hero",false);
        boolean test=user.getPriGroups().stream().filter(x->x.getNameGroup().equals("Hello")).findAny().isEmpty();
        Assert.assertFalse(test);
    }


    ArrayList<User>lst = new ArrayList<>();


    @Test
    public void inviteFriend() throws NoSuchAlgorithmException {
        generateUser();
        User user=userService.Login("nam","123");
        boolean test = user.inviteFriend("nam",dataStore.lstUser,user.getPubGroups().stream().filter(x->x.getNameGroup().equals("Hello")).findAny()
                .orElse(null));
        Assert.assertTrue(test);

    }

    @Test
    public void joinGroup() throws NoSuchAlgorithmException {
        generateUser();
        PublicGroup publicGroup = dataStore.lstPubGroup.stream().filter(x->x.getNameGroup().equals("Hello")).findAny().orElse(null);
        User user = userService.Login("bao","123");
        user.joinGroup("abc",user,publicGroup);
        boolean test = user.joinGroup("abc",user,user.getPubGroups().stream().filter(x->x.getNameGroup().equals("bao")).findAny().orElse(null));
        Assert.assertTrue(test);


    }




}