package eiu.edu.vn.Models;

import java.util.ArrayList;

public class Box {
    private String owner;
    private String alias;
    private ArrayList<Message> messages = new ArrayList<Message>();

    public Box(String owner, String alias, ArrayList<Message> messages) {
        this.owner = owner;
        this.alias = alias;
        this.messages = messages;
    }

    public Box(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public Box() {

    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

}
