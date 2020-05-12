package eiu.edu.vn.Models;

import java.util.ArrayList;

public class Box {
    private String owner;

    private ArrayList<Message> messages = new ArrayList<Message>();

    public Box(String owner, ArrayList<Message> messages) {
        this.owner = owner;
        this.messages = messages;
    }

    public Box(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public Box() {

    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
