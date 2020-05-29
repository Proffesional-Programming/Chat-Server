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

    ArrayList box = new ArrayList<Box>();

    public void generateUser() throws NoSuchAlgorithmException {
        userService.createUser("nam", "123");
        userService.createUser("bao", "123");
        userService.createUser("bao1", "123");
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
        boolean test = DataStore.getInstance().getLstPriGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().isEmpty();
        Assert.assertFalse(test);
    }

    @Test
    public void inviteFriend() throws NoSuchAlgorithmException {
        generateUser();
        boolean test = false;
        User user = userService.Login("nam", "123");
        user.createGroup(user.getUserName(), "Hello", true);
        test = user.inviteFriend("nam", UUID.fromString(""));
        Assert.assertTrue(test);
    }

    @Test
    public void joinGroup() throws NoSuchAlgorithmException {
        generateUser();
        User user1 = userService.Login("bao", "123");
        User user2 = userService.Login("nam", "123");
        user1.createGroup(user1.getUserName(), "Hello", false);
        PublicGroup publicGroup = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        user2.joinGroup(publicGroup.getCode(), user2, publicGroup.getId());
        boolean test = publicGroup.getGroupMembers().contains(user2);
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

    @Test
    public void removeMessageInGroup() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = true;
        User user1 = userService.Login("bao", "123");
        User user2 = userService.Login("nam", "123");

        user1.createGroup(user1.getUserName(), "Hello", false);
        PublicGroup publicGroup = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);

        user2.joinGroup(publicGroup.getCode(), user2, publicGroup.getId());
        user2.sendMessageinGroup("abc",publicGroup);
        user2.removeMessageInGroup("abc",publicGroup.getNameGroup(),false);
        ArrayList<Message> message = user2.getTopLastestMessageinM(1,user2.getUserName());
        if(message.size()!=0){
            test=false;
        }
        Assert.assertTrue(test);
    }


    @Test
    public void leaveGroup() throws NoSuchAlgorithmException{
        generateUser();
//        boolean test = true;
        User user1 = userService.Login("bao", "123");
        User user2 = userService.Login("nam", "123");
        User user3 = userService.Login("bao1", "123");

        user1.createGroup(user1.getUserName(), "Hello", false);
        PublicGroup publicGroup = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);

        user2.joinGroup(publicGroup.getCode(), user2, publicGroup.getId());
        user3.joinGroup(publicGroup.getCode(), user3, publicGroup.getId());
        user2.leaveGroup(publicGroup.getNameGroup(),false);
        Assert.assertEquals(1, publicGroup.getSize());
    }









}