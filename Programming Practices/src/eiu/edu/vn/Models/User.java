package eiu.edu.vn.Models;

import eiu.edu.vn.DataStore.DataStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class User extends Notification {
    private UUID id;
    DataStore data = new DataStore();
    private String userName;
    private String password;
    private String lastName;
    private String firstName;
    private String hashPassword;
    private String fullName;
    private String path = "./eiu/edu/vn/DataStore/";

    public User(UUID id, String userName, String hashPassword, ArrayList<Box> boxes) {
        super(boxes);
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.path += id;
        crtFolder(this.path);
    }

    public User(UUID id, String userName, String password, String hashPassword, String fullName, ArrayList<Box> boxes) {
        super(boxes);
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.hashPassword = hashPassword;
        this.fullName = fullName;
        this.path += id;
        crtFolder(this.path);
    }

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

    public void createGroup(String owner, String nGroup, boolean isPrivate) {
        if (isPrivate == false) {
            PublicGroup pubGroup = new PublicGroup(UUID.randomUUID(), nGroup, owner, new Box(owner, new ArrayList<Message>()), getCode());
            data.lstPubGroup.add(pubGroup);
        } else {
            PrivateGroup priGroup = new PrivateGroup(UUID.randomUUID(), nGroup, owner, new Box(owner, new ArrayList<Message>()));
            data.lstPriGroup.add(priGroup);
        }
    }

    public boolean inviteFriend(String user, ArrayList<User> lstFriend, Group group) {
        User u = lstFriend.stream().filter(x -> x.getUserName().equals(user)).findFirst().orElse(null);
        if (u != null) {

            group.addMember(u);
            return true;
        }
        return false;
    }

    public boolean joinGroup(String code, User user, PublicGroup pubGroup) {
        if (pubGroup.getCode().equals(code)) {
            data.lstPubGroup.remove(pubGroup);
            pubGroup.addMember(user);
            data.lstPubGroup.add(pubGroup);
            return true;
        }else {
            return false;
        }
    }

    public String getPath() {
        return path;
    }

    private boolean crtFolder(String path) {
        File file = new File(path);
        boolean check = file.mkdir();
        return check;
    }

    public void receiveMessage(String message, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        if (box != null) {
            box.getMessages().add(new Message(owner, message));
            getBoxes().remove(box);
            getBoxes().add(box);
        } else {
            Box newBox = new Box(owner, new ArrayList<Message>());
            newBox.getMessages().add(new Message(owner, message));
            getBoxes().add(newBox);
        }
    }

    public ArrayList<Message> seeConversation(String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        if (box != null) {
            return box.getMessages();
        }
        return new ArrayList<Message>();
    }

    public void sendMessageinGroup(String message, Group group) {
        group.receiveMessage(message, getUserName());
    }

    public void sendMessage(String message, String owner) {
        User user = data.lstUser.stream().filter(x -> x.getUserName().equals(owner)).findAny().orElse(null);
        if (user != null) {
            user.receiveMessage(message, getUserName());
        }
    }

    public void leaveGroup(String nameGroup, boolean isPrivate) {
        if (isPrivate == true) {
            PrivateGroup pri = data.lstPriGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pri.getGroupMembers().remove(pri.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            data.lstPriGroup.remove(pri);
            data.lstPriGroup.add(pri);
        } else {
            PublicGroup pub = data.lstPubGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pub.getGroupMembers().remove(pub.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            data.lstPubGroup.remove(pub);
            data.lstPubGroup.add(pub);
        }
    }

    public ArrayList<Message> getTopLastestMessageinK(int k, int m, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        ArrayList<Message> messagesinM = getTopLastestMessageinM(m, owner);
        ArrayList<Message> all = box.getMessages();
        for (Message i : messagesinM) {
            all.remove(i);
        }
        ArrayList<Message> lstMessages = new ArrayList<Message>();
        for (int i = all.size() - 1 - k; i < all.size(); i++) {
            lstMessages.add(box.getMessages().get(i));
        }
        return lstMessages;
    }

    public ArrayList<Message> getTopLastestMessageinM(int m, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        ArrayList<Message> lstMessages = new ArrayList<Message>();
        if (box != null) {
            for (int i = box.getMessages().size() - 1 - m; i < box.getMessages().size(); i++) {
                lstMessages.add(box.getMessages().get(i));
            }
        }
        return lstMessages;
    }

    public ArrayList<Message> getTopLastestMessageinGroup(int k, String owner,boolean checkGroup) {
        if(checkGroup==true){ //true == public
            ArrayList<Message> lstMessages = new ArrayList<Message>();
            Group group = data.lstPubGroup.stream().filter(x -> x.getNameGroup().equals(owner)).findAny().orElse(null);
            if (group != null) {
                lstMessages = group.getTopLastestMessage(k);
            }
            return lstMessages;
        }else {
            ArrayList<Message> lstMessages = new ArrayList<Message>();
            Group group = data.lstPriGroup.stream().filter(x -> x.getNameGroup().equals(owner)).findAny().orElse(null);
            if (group != null) {
                lstMessages = group.getTopLastestMessage(k);
            }
            return lstMessages;
        }

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getFullName() {
        return fullName = lastName + " " + firstName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
