package eiu.edu.vn.Models;

import java.util.UUID;

public class PublicGroup extends Group {
    private String code;

    public PublicGroup(UUID id, String nameGroup, String ownerUser, String code) {
        super(id, nameGroup, ownerUser);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
