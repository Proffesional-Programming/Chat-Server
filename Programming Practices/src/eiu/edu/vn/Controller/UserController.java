package eiu.edu.vn.Controller;

import eiu.edu.vn.DataStore.DataStore;
import eiu.edu.vn.Models.Group;
import eiu.edu.vn.Models.PrivateGroup;
import eiu.edu.vn.Models.PublicGroup;
import eiu.edu.vn.Models.User;

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
        if (isPrivate == false) {
            PublicGroup pubGroup = new PublicGroup(UUID.randomUUID(), nGroup, userName, getCode());
            owner.addGroup(pubGroup);
        } else {
            PrivateGroup priGroup = new PrivateGroup(UUID.randomUUID(), nGroup, userName);
            owner.addGroup(priGroup);
        }
    }

    public void inviteFriend(String user, ArrayList<User> lstFriend, Group group) {
        User u = lstFriend.stream().filter(x -> x.getUserName().equals(user)).findFirst().orElse(null);
        if (u != null) {
            group.addMember(u);
        }
    }

    public void joinGroup(String code, User user, PublicGroup pubGroup) {
        if (pubGroup.getCode().equals(code)) {
            pubGroup.addMember(user);
        }
    }
}
