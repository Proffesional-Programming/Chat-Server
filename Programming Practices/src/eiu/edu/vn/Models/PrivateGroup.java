package eiu.edu.vn.Models;

import java.util.ArrayList;
import java.util.UUID;

public class PrivateGroup extends Group {

    public PrivateGroup(UUID id, String nameGroup, String ownerUser, ArrayList<Message> messages) {
        super(id, nameGroup, ownerUser, messages);
    }

}
