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
    DataStore dataStore = DataStore.getInstance();
    public void generateUser() throws NoSuchAlgorithmException {
        userService.createUser("Nam", "123");
        userService.createUser("Bao", "123");
        userService.createUser("Bao1", "123");
    }

    @Test
    public void creatUsers() throws NoSuchAlgorithmException {
        this.userService.createUser("Bao", "123");
        boolean test = this.userService.createUser("Nam", "123");
        Assert.assertTrue(test);
    }

    @Test
    public void loginTests() throws NoSuchAlgorithmException {
        this.userService.createUser("Bao", "123");
        boolean test = this.userService.createUser("Nam", "123");
        Assert.assertTrue(test);
    }

    @Test
    public void createGroup() throws NoSuchAlgorithmException {
        generateUser();
        User user = userService.login("Nam","123");
        user.createGroup(user.getUserName(),"Hello",true);
        boolean test = dataStore.getLstPriGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().isEmpty();
        Assert.assertFalse(test);
    }

    @Test
    public void inviteFriend() throws NoSuchAlgorithmException {
        generateUser();
        boolean test = true;
        User user = userService.login("Nam", "123");
        user.createGroup(user.getUserName(), "Hello", false);
        PublicGroup publicGroup = dataStore.getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        test = user.inviteFriend("Bao",publicGroup.getId());
        Assert.assertTrue(test);
    }

    @Test
    public void joinGroup() throws NoSuchAlgorithmException {
        generateUser();
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");
        user1.createGroup(user1.getUserName(),"Hello",false);
        PublicGroup publicGroup = dataStore.getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        user2.joinGroup(publicGroup.getCode(), user2, publicGroup.getId());
        boolean test = publicGroup.getGroupMembers().contains(user2);
        Assert.assertTrue(test);
    }

    @Test
    public void receiveMessage() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = false;
        User user = userService.login("Bao","123");
        Box box = user.getBoxes().stream().filter(x ->x.getOwner().equals("Bao")).findAny().orElse(null);
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
        boolean test = false;
        User user = userService.login("Bao","123");
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
        User user1 = userService.login("Nam","123");
        user1.sendMessage("abc","Bao");
        User user2 = userService.login("Bao","123");
        ArrayList<Message>message=user2.getTopLastestMessageinM(1,"Nam");
        if(!message.get(message.size()-1).getMessage().equals("abc")){
             test =false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void removeMessageInUser() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = true;
        User user = userService.login("Nam","123");
        user.sendMessage("abc","Bao");
        user.removeMessageInUser("abc","Bao");
        ArrayList<Message> message = user.getTopLastestMessageinM(1,"Nam");
        if(message.size()!=0){
            test=false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void removeMessageInGroup() throws NoSuchAlgorithmException{
        generateUser();
        boolean test = true;
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");

        user1.createGroup(user1.getUserName(), "Hello", false);
        PublicGroup publicGroup = dataStore.getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
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

        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");
        User user3 = userService.login("Bao1", "123");

        user1.createGroup(user1.getUserName(), "Hello", false);
        PublicGroup publicGroup = dataStore.getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        user2.joinGroup(publicGroup.getCode(), user2, publicGroup.getId());
        user3.joinGroup(publicGroup.getCode(), user3, publicGroup.getId());
        user2.leaveGroup(publicGroup.getNameGroup(),false);
        Assert.assertEquals(1, publicGroup.getSize());
    }

    @Test
    public void getTopLastestMessageinK() throws NoSuchAlgorithmException{
        generateUser();
        boolean test =true;
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");

        user1.sendMessage("abc1","Nam");
        user1.sendMessage("abc2","Nam");
        user1.sendMessage("abc3","Nam");
        user1.sendMessage("abc4","Nam");
        user1.sendMessage("abc5","Nam");
        user1.sendMessage("abc6","Nam");
        user1.sendMessage("abc7","Nam");
        ArrayList<Message> message = user1.getTopLastestMessageinK(4,2,user2.getUserName());
        if(message.size()!=4){
            test =false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void getTopLastestMessageinM() throws NoSuchAlgorithmException{
        generateUser();

        boolean test =true;
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");

        user1.sendMessage("abc1","Nam");
        user1.sendMessage("abc2","Nam");
        user1.sendMessage("abc3","Nam");
        user1.sendMessage("abc4","Nam");
        user1.sendMessage("abc5","Nam");
        user1.sendMessage("abc6","Nam");
        user1.sendMessage("abc7","Nam");
        ArrayList<Message> message = user1.getTopLastestMessageinM(3,user2.getUserName());
        if(message.size()!=3){
            test =false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void getTopLastestMessageinGroup() throws NoSuchAlgorithmException{
        generateUser();

        boolean test =true;
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");

        user2.createGroup(user1.getUserName(),"Hello", false);
        PublicGroup publicGroup = dataStore.getLstPubGroup().stream().filter(x -> x.getNameGroup().equals("Hello")).findAny().orElse(null);
        user1.joinGroup(publicGroup.getCode(), user1, publicGroup.getId());
        user1.sendMessageinGroup("abc1",publicGroup);
        user1.sendMessageinGroup("abc2",publicGroup);
        user1.sendMessageinGroup("abc3",publicGroup);
        user1.sendMessageinGroup("abc4",publicGroup);
        user2.sendMessageinGroup("abc5",publicGroup);
        user2.sendMessageinGroup("abc6",publicGroup);
        user2.sendMessageinGroup("abc7",publicGroup);

        ArrayList<Message> message = user1.getTopLastestMessageinGroup(4,publicGroup.getId(),true);
        if(message.size()!=4){
            test =false;
        }
        Assert.assertTrue(test);
    }

    @Test
    public void putAlais() throws NoSuchAlgorithmException{
        generateUser();
        boolean test =true;
        User user1 = userService.login("Bao", "123");
        User user2 = userService.login("Nam", "123");

        user1.putAlais("abc",user2.getUserName());
        Box box =user1.getBoxes().stream().filter(x -> x.getOwner().equals("Nam")).findAny().orElse(null);
        if(box.getAlias() != "abc"){
            test=false;
        }
        Assert.assertTrue(test);
    }

}