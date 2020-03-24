package eiu.edu.vn.Models;

import java.util.UUID;

public class Image extends File {

    private int width;
    private int height;

    public Image(UUID id, String path, String format) {
        super(id, path, format);
    }
}
