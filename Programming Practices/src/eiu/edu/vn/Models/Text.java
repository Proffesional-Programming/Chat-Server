package eiu.edu.vn.Models;

import java.util.ArrayList;

abstract class Text {
    private ArrayList<Message> lstMessage;

    public Text(ArrayList<Message> lstMessage) {
        this.lstMessage = new ArrayList<Message>();
    }

    public void receiveMessage(Message message) {
        lstMessage.add(message);
    }

    public void removeMessage(Message message) {
        lstMessage.remove(message);
    }

    public ArrayList<Message> getLstMessage() {
        return lstMessage;
    }
}
