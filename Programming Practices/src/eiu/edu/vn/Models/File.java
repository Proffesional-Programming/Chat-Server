package eiu.edu.vn.Models;

import java.util.UUID;

public abstract class File {
    private UUID id;
    private String path;
    private String format;

    public File(UUID id, String path, String format) {
        this.id = id;
        this.path = path;
        this.format = format;
    }

    public UUID getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getFormat() {
        return format;
    }
}
