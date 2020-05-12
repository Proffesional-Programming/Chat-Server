import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.*;
import eiu.edu.vn.Services.UserService;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

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
        boolean test=user.getData().lstPriGroup.stream().filter(x->x.getNameGroup().equals("Hello")).findAny().isEmpty();
        Assert.assertFalse(test);
    }


    ArrayList<User>lst = new ArrayList<>();


    @Test
    public void inviteFriend() throws NoSuchAlgorithmException {
        generateUser();
        boolean test = false;
        User user = userService.Login("nam", "123");
        PublicGroup pubGroup = user.getData().lstPubGroup.stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        PrivateGroup priGroup = user.getData().lstPriGroup.stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        if (pubGroup != null) {
            test = user.inviteFriend("nam", dataStore.lstUser, pubGroup);
        } else {
            test = user.inviteFriend("nam", dataStore.lstUser, priGroup);
        }
        Assert.assertTrue(test);

    }

    @Test
    public void joinGroup() throws NoSuchAlgorithmException {
        generateUser();
        PublicGroup publicGroup = dataStore.lstPubGroup.stream().filter(x->x.getNameGroup().equals("Hello")).findAny().orElse(null);
        User user = userService.Login("bao","123");
        user.joinGroup("abc",user,publicGroup);
        boolean test = user.joinGroup("abc",user,user.getData().lstPubGroup.stream().filter(x->x.getNameGroup().equals("bao")).findAny().orElse(null));
        Assert.assertTrue(test);
    }

    @Test
    public void crtFolder() throws NoSuchAlgorithmException{
        generateUser();
        User user = userService.Login("bao","123");
        String test = user.getPath();
        assertEquals(true,test);
    }


    @Test
    public void receiveMessage() throws NoSuchAlgorithmException{
        generateUser();
        boolean test =false;
        User user = userService.Login("bao","123");
        Box box = user.getBoxes().stream().filter(x ->x.getOwner().equals("bao")).findAny().orElse(null);
        if (box !=null){
            test = true;
        }else {
            test=false;
        }
        Assert.assertFalse(test);
    }

    @Test
    public void seeConversation() throws NoSuchAlgorithmException{
        generateUser();
        boolean test =false;
        User user = userService.Login("bao","123");
        Box box = user.getBoxes().stream().filter(x ->x.getMessages().equals("123")).findAny().orElse(null);
        if (box !=null){
            test = true;
        }else {
            test=false;
        }
        Assert.assertFalse(test);
    }

    @Test
    public void sendMessage() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = true;
        User user = userService.Login("nam","123");
        user.sendMessage("abc","bao");
        User user1 = userService.Login("bao","123");
        ArrayList<Message>message=user1.getTopLastestMessageinM(1,"nam");
        if(!message.get(message.size()-1).getMessage().equals("abc")){
             test =false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void removeMessageInUser() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = true;
        User user = userService.Login("nam","123");
        user.sendMessage("abc","bao");
        user.removeMessageInUser("abc","bao");
        ArrayList<Message> message = user.getTopLastestMessageinM(1,"nam");
        if(message != null){
            test = false;
        }
        Assert.assertTrue(test);

    }






}