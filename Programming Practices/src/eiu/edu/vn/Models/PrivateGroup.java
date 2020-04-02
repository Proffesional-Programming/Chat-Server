package eiu.edu.vn.Models;

import java.util.ArrayList;
import java.util.UUID;

public class PrivateGroup extends Group {

    public PrivateGroup(UUID id, String nameGroup, String ownerUser, Box box) {
        super(id, nameGroup, ownerUser, box);
    }
}
