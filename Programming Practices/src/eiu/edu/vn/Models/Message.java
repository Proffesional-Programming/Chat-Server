package eiu.edu.vn.Models;

import java.awt.*;

public class Message {
    private String text;
    private String owner;

    public Message(String text, String owner, File file) {
        this.text = text;
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public String getOwner() {
        return owner;
    }
}
