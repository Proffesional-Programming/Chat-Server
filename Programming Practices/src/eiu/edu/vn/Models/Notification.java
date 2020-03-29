package eiu.edu.vn.Models;

import java.util.ArrayList;

public abstract class Notification {
    private ArrayList<Box> boxes = new ArrayList<Box>();
    Box box = new Box();

    public Notification(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    public Notification(Box box) {
        this.box = box;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public Box getBox() {
        return box;
    }
}
