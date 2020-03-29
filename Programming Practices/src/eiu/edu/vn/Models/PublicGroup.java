package eiu.edu.vn.Models;

import java.util.ArrayList;
import java.util.UUID;

public class PublicGroup extends Group {
    private String code;

    public PublicGroup(UUID id, String nameGroup, String ownerUser, ArrayList<Message> messages, String code) {
        super(id, nameGroup, ownerUser, messages);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
