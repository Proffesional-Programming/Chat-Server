package eiu.edu.vn.Models;

public class PrivateGroup extends Group {
    private String id;

    public PrivateGroup(String nameGroup, String ownerUser, String id) {
        super(nameGroup, ownerUser);
        this.id = id;
    }
}
