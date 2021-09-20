import java.nio.file.Path;
import java.sql.Timestamp;

public class FileInfo {
    String name;
    String version;
    Timestamp dateOfMod;
    long size;
    String absolutePath;

    FileInfo(String name, String version, Timestamp dateOfCreation, long size, String absolutePath) {
        this.name = name;
        this.version = version;
        this.absolutePath = absolutePath;
        this.dateOfMod = dateOfCreation;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Timestamp getDateOfMod() {
        return dateOfMod;
    }

    public void setDateOfMode(Timestamp dateOfMod) {
        this.dateOfMod = dateOfMod;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString() {
        return "[name: \"" + name + "\", version:(" + version + "), modify:(" + dateOfMod + "), size:(" + size + "), path:(" + absolutePath + ")]";
    }
}
