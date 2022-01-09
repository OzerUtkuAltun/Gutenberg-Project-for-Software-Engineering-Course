package tr.edu.mcbu.gutenberg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Entity
public class File extends BaseEntity {

    String name;

    @JsonIgnore
    String location;

    String contentType;

    @Transient
    @JsonIgnore
    Long size;

    @Transient
    @JsonIgnore
    byte[] content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        String extension = name.substring(name.lastIndexOf("."));
        this.location = location + UUID.randomUUID().toString() + extension.replaceAll("HEIC", "JPG").replaceAll("HEIF", "JPG");
        this.size = Paths.get(this.location).toFile().length();
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }


}
