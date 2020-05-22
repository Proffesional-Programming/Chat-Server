package eiu.edu.vn.Models;

import eiu.edu.vn.DataStore.DataStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class User extends Notification {
    private UUID id;
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



    public User findFriend(String userName) {
        User user = DataStore.getInstance().lstUser.stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null);
        return user;
    }

    public String getCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String fullalphabet = alphabet + alphabet.toLowerCase() + "123456789";
        Random random = new Random();
        char code = fullalphabet.charAt(random.nextInt(7));
        return Character.toString(code);
    }

    public void createGroup(String owner, String nGroup, boolean isPrivate) {
        if (isPrivate == false) {
            PublicGroup pubGroup = new PublicGroup(UUID.randomUUID(), nGroup, owner, new Box(owner, new ArrayList<Message>()), getCode());
            DataStore.getInstance().lstPubGroup.add(pubGroup);
        } else {
            PrivateGroup priGroup = new PrivateGroup(UUID.randomUUID(), nGroup, owner, new Box(owner, new ArrayList<Message>()));
            DataStore.getInstance().lstPriGroup.add(priGroup);
        }
    }

    public boolean inviteFriend(String user, UUID groupid) {
        User u = DataStore.getInstance().lstUser.stream().filter(x -> x.getUserName().equals(user)).findFirst().orElse(null);
        PublicGroup pubGroup = DataStore.getInstance().lstPubGroup.stream().filter(x -> x.getId().equals(groupid)).findAny().orElse(null);
        PrivateGroup priGroup = DataStore.getInstance().lstPriGroup.stream().filter(x -> x.getId().equals(groupid)).findAny().orElse(null);
        if (u != null) {
            if (pubGroup.getId() == groupid) {
                pubGroup.addMember(u);
            } else {
                priGroup.addMember(u);
            }
            return true;
        }
        return false;
    }

    public boolean joinGroup(String code, User user, UUID groupid) {
        PublicGroup pubGroup = DataStore.getInstance().lstPubGroup.stream().filter(x -> x.getId().equals(groupid)).findFirst().orElse(null);
        if (pubGroup.getCode().equals(code)) {
            DataStore.getInstance().lstPubGroup.remove(pubGroup);
            pubGroup.addMember(user);
            DataStore.getInstance().lstPubGroup.add(pubGroup);
            return true;
        } else {
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

    public boolean receiveMessage(String message, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        boolean check = false;
        if (box != null) {
            box.getMessages().add(new Message(owner, message));
            getBoxes().remove(box);
            getBoxes().add(box);
            return check=false;
        } else {
            Box newBox = new Box(owner, new ArrayList<Message>());
            newBox.getMessages().add(new Message(owner, message));
            getBoxes().add(newBox);
            return check=true;
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

    public void sendMessage(String message, String receiver) {
        User user = DataStore.getInstance().lstUser.stream().filter(x -> x.getUserName().equals(receiver)).findAny().orElse(null);
        if (user != null) {
            user.receiveMessage(message, getUserName());

        }
    }

    public void leaveGroup(String nameGroup, boolean isPrivate) {
        if (isPrivate == true) {
            PrivateGroup pri = DataStore.getInstance().lstPriGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pri.getGroupMembers().remove(pri.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            DataStore.getInstance().lstPriGroup.remove(pri);
            DataStore.getInstance().lstPriGroup.add(pri);
        } else {
            PublicGroup pub = DataStore.getInstance().lstPubGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pub.getGroupMembers().remove(pub.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            DataStore.getInstance().lstPubGroup.remove(pub);
            DataStore.getInstance().lstPubGroup.add(pub);
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

    public void removeMessageInUser(String message, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        if (box != null) {
            Message message1 = box.getMessages().stream().filter(x -> x.getMessage().equals(message)).findAny().orElse(null);
            if (message1 != null) {
                box.getMessages().remove(message1);
            }

            File file = new File(path + message);
            if (file.isFile()) {
                file.delete();
            }
            getBoxes().remove(box);
            getBoxes().add(box);
        }

        User user = DataStore.getInstance().lstUser.stream().filter(x -> x.getUserName().equals(owner)).findAny().orElse(null);
        user.removeMessageInUser(message, getUserName());
    }

    public void removeMessageInGroup(String message, String nameGroup, boolean isPrivate) {
        if (isPrivate == true) {
            PrivateGroup pri = DataStore.getInstance().lstPriGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            if (pri != null) {
                pri.delMessage(message, getUserName());
            }
        } else {
            PublicGroup pub = DataStore.getInstance().lstPubGroup.stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            if (pub != null) {
                pub.delMessage(message, getUserName());
            }
        }
    }

    public ArrayList<Message> getTopLastestMessageinGroup(int k, String owner, boolean checkGroup) {
        if (checkGroup == true) { //true == public
            ArrayList<Message> lstMessages = new ArrayList<Message>();
            Group group = DataStore.getInstance().lstPubGroup.stream().filter(x -> x.getNameGroup().equals(owner)).findAny().orElse(null);
            if (group != null) {
                lstMessages = group.getTopLastestMessage(k);
            }
            return lstMessages;
        } else {
            ArrayList<Message> lstMessages = new ArrayList<Message>();
            Group group = DataStore.getInstance().lstPriGroup.stream().filter(x -> x.getNameGroup().equals(owner)).findAny().orElse(null);
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
