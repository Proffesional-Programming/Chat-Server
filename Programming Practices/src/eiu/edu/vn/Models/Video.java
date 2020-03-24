package eiu.edu.vn.Models;

import java.util.UUID;

public class Video extends File {
    private int duration;

    public Video(UUID id, String path, String format) {
        super(id, path, format);
    }
}
