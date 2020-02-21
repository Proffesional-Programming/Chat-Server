package eiu.edu.vn.Controller;

import eiu.edu.vn.Models.Group;
import eiu.edu.vn.Models.PrivateGroup;
import eiu.edu.vn.Models.PublicGroup;
import eiu.edu.vn.Models.User;
import eiu.edu.vn.Services.GroupService;

public class UserController {
    public GroupService groupService = new GroupService();

    public boolean createGroup(boolean isPrivate, String nameGroup, User user) {
        if (isPrivate == true) {
            PrivateGroup priGroup = new PrivateGroup(nameGroup, user.getUserName(), user.getId().toString());
            groupService.addGroup(priGroup);
        } else {
            PublicGroup pubGroup = new PublicGroup();
            groupService.addGroup(pubGroup);
        }

        return true;
    }

    public void inviteFriend() {

    }

    public void sendMessage() {

    }

    public boolean delGroup(Group group) {
        if (groupService.lstGroup().contains(group)) {
            groupService.lstGroup().remove(group);
            return true;
        }
        return false;
    }
}
