package eiu.edu.vn.Controller;

import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class UserController {

    DataStore data = new DataStore();

    public User findFriend(String userName) {
        User user = data.lstUser.stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null);
        return user;
    }

    public String getCode() {
        char data[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9'};
        char index[] = new char[7];
        Random r = new Random();
        int i = 0;
        for (i = 0; i < (index.length); i++) {
            int ran = r.nextInt(data.length);
            index[i] = data[ran];
        }
        return new String(index);
    }

    public void createGroup(User owner, String nGroup, String userName, boolean isPrivate) {
        ArrayList<Message> lstMessage = new ArrayList<Message>();
        if (isPrivate == false) {
            PublicGroup pubGroup = new PublicGroup(UUID.randomUUID(), nGroup, userName, lstMessage, getCode());
            owner.addGroup(pubGroup);
        } else {
            PrivateGroup priGroup = new PrivateGroup(UUID.randomUUID(), nGroup, userName, lstMessage);
            owner.addGroup(priGroup);
        }
    }

    public void inviteFriend(String user, ArrayList<User> lstFriend, Group group) {
        User u = lstFriend.stream().filter(x -> x.getUserName().equals(user)).findFirst().orElse(null);
        if (u != null) {
            group.addMember(u);
        }
    }

    public boolean joinGroup(String code, User user, PublicGroup pubGroup) {
        if (pubGroup.getCode().equals(code)) {
            pubGroup.addMember(user);
            return true;
        }
        return false;
    }

    public void sendMessageToUser(Message message, User user) throws IOException {
        File file = new File(message.getText());
        if (file.isFile()) {
            Path result = Files.move(Paths.get(message.getText()), Paths.get(user.getPath()));
            if (result != null) {
                System.out.println("Ok");
            }
        }
        user.receiveMessage(message);
    }

    public void sendMessageToGroup(Message message, Group group) throws IOException {
        File file = new File(message.getText());
        if (file.isFile()) {
            Path result = Files.move(Paths.get(message.getText()), Paths.get(group.getPath()));
            if (result != null) {
                System.out.println("Ok");
            }
        }
        group.receiveMessage(message);
    }

    public void delMessageinUser(Message message, User user) throws IOException {
        File file = new File(message.getText());
        if (file.isFile()) {
            boolean rs = Files.deleteIfExists(Paths.get(message.getText()));
            if (rs == true) {
                System.out.println("Ok");
            }
        }
        user.removeMessage(message);
    }

    public void delMessageinGroup(Message message, Group group) throws IOException {
        File file = new File(message.getText());
        if (file.isFile()) {
            boolean rs = Files.deleteIfExists(Paths.get(message.getText()));
            if (rs == true) {
                System.out.println("Ok");
            }
        } else {
            group.removeMessage(message);
        }
    }


}
