package eiu.edu.vn.Models;

import java.util.ArrayList;
import java.util.UUID;

public class PublicGroup extends Group {
    private String code;

    public PublicGroup(UUID id, String nameGroup, String ownerUser, Box box, String code) {
        super(id, nameGroup, ownerUser, box);
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
