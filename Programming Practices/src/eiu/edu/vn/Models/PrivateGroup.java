package eiu.edu.vn.Models;

import java.util.ArrayList;
import java.util.UUID;

public class PrivateGroup extends Group {
    public PrivateGroup(UUID id, String nameGroup, String ownerUser, ArrayList<Message> lstMessage) {
        super(id, nameGroup, ownerUser, lstMessage);
    }

}
