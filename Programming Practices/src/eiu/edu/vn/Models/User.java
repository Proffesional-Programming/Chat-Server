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
        User user = DataStore.getInstance().getLstUser().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null);
        return user;
    }

    public String generateCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String fullalphabet = alphabet + alphabet.toLowerCase() + "123456789";
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 7; i++) {
            code += fullalphabet.charAt(random.nextInt(fullalphabet.length()));
        }
        return code;
    }

    public void createGroup(String owner, String nGroup, boolean isPrivate) {
        if (isPrivate == false) {
            PublicGroup pubGroup = new PublicGroup(UUID.randomUUID(), nGroup, owner, new Box(new ArrayList<Message>()), generateCode());
            DataStore.getInstance().getLstPubGroup().add(pubGroup);
        } else {
            PrivateGroup priGroup = new PrivateGroup(UUID.randomUUID(), nGroup, owner, new Box(new ArrayList<Message>()));
            DataStore.getInstance().getLstPriGroup().add(priGroup);
        }
    }

    public boolean inviteFriend(String user, UUID groupid) {
        User u = DataStore.getInstance().getLstUser().stream().filter(x -> x.getUserName().equals(user)).findFirst().orElse(null);
        PublicGroup pubGroup = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getId().equals(groupid)).findAny().orElse(null);
        PrivateGroup priGroup = DataStore.getInstance().getLstPriGroup().stream().filter(x -> x.getId().equals(groupid)).findAny().orElse(null);
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
        PublicGroup pubGroup = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getId().equals(groupid)).findFirst().orElse(null);
        if (pubGroup.getCode().equals(code)) {
            DataStore.getInstance().getLstPubGroup().remove(pubGroup);
            pubGroup.addMember(user);
            DataStore.getInstance().getLstPubGroup().add(pubGroup);
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

    public void putAlais(String alais, String username) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(username)).findAny().orElse(null);
        if (box != null) {
            box.setAlias(alais);
            getBoxes().remove(box);
            getBoxes().add(box);
        } else {
            Box newBox = new Box(username, alais, new ArrayList<Message>());
            getBoxes().add(newBox);
        }
    }

    public boolean receiveMessage(String message, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        if (box != null) {
            box.getMessages().add(new Message(owner, message));
            getBoxes().remove(box);
            getBoxes().add(box);
            return false;
        } else {
            Box newBox = new Box(owner, "", new ArrayList<Message>());
            newBox.getMessages().add(new Message(owner, message));
            getBoxes().add(newBox);
            return true;
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
        User user = DataStore.getInstance().getLstUser().stream().filter(x -> x.getUserName().equals(receiver)).findAny().orElse(null);
        if (user != null) {
            user.receiveMessage(message, getUserName());
            receiveMessage(message,user.getUserName());
        }
    }

    public void leaveGroup(String nameGroup, boolean isPrivate) {
        if (isPrivate == true) {
            PrivateGroup pri = DataStore.getInstance().getLstPriGroup().stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pri.getGroupMembers().remove(pri.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            DataStore.getInstance().getLstPriGroup().remove(pri);
            DataStore.getInstance().getLstPriGroup().add(pri);
        } else {
            PublicGroup pub = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            pub.getGroupMembers().remove(pub.getGroupMembers().stream().filter(x -> x.getUserName().equals(userName)).findAny().orElse(null));
            DataStore.getInstance().getLstPubGroup().remove(pub);
            DataStore.getInstance().getLstPubGroup().add(pub);
        }
    }

    public ArrayList<Message> getTopLastestMessageinK(int k, int m, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        ArrayList<Message> messagesinM = getTopLastestMessageinM(m, owner);
        ArrayList<Message> all = box.getMessages();
        for (Message i : messagesinM) {
            all.remove(i);
        }

        return getTopLastestMessageinM(k,owner);
    }

    public ArrayList<Message> getTopLastestMessageinM(int m, String owner) {
        Box box = getBoxes().stream().filter(x -> x.getOwner().equals(owner)).findAny().orElse(null);
        ArrayList<Message> lstMessages = new ArrayList<Message>();
        if (box != null) {
            for (int i = box.getMessages().size() - 1; i >= 0; i--) {
                lstMessages.add(box.getMessages().get(i));
                m--;
                if(m==0) break;
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


    }

    public void removeMessageInGroup(String message, String nameGroup, boolean isPrivate) {
        if (isPrivate == true) {
            PrivateGroup pri = DataStore.getInstance().getLstPriGroup().stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            if (pri != null) {
                pri.delMessage(message, getUserName());
            }
        } else {
            PublicGroup pub = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getNameGroup().equals(nameGroup)).findAny().orElse(null);
            if (pub != null) {
                pub.delMessage(message, getUserName());
            }
        }
    }

    public ArrayList<Message> getTopLastestMessageinGroup(int k,UUID id ,boolean checkGroup) {
        ArrayList<Message> lstMessages = new ArrayList<Message>();
        if (checkGroup == true) { //true == public
            PublicGroup group = DataStore.getInstance().getLstPubGroup().stream().filter(x -> x.getId().equals(id)).findAny().orElse(null);
            if (group != null) {
                User user=group.getGroupMembers().stream().filter(x->x.getUserName().equals(getUserName())).findAny().orElse(null);
                if(user!=null){
                    lstMessages = group.getTopLastestMessage(k);
                }
            }
            return lstMessages;
        } else {
            PrivateGroup group = DataStore.getInstance().getLstPriGroup().stream().filter(x -> x.getId().equals(id)).findAny().orElse(null);
            if (group != null) {
                User user=group.getGroupMembers().stream().filter(x->x.getUserName().equals(getUserName())).findAny().orElse(null);
                if(user!=null){
                    lstMessages = group.getTopLastestMessage(k);
                }
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
