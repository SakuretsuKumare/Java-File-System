package umbcs680.hw17.fs;

import java.time.LocalDateTime;

/*  This class represents a file. It is a subclass of the FSElement superclass and both
    the Directory and File classes should share a common interface. Unlike directories,
    files have a size that is not 0. Has an override accept method for accepting visitors
 */

public class File extends FSElement {
    protected int size;

    // Public constructor to make files
    public File(String name, LocalDateTime creationTime, int size, Directory parent) {
        super(parent, name, creationTime);
        // Size of the file cannot be negative. If it's below 0, then set the size to 0
        if (size < 0) {
            size = 0;
        }
        this.size = size;
    }

    // Bool check to see if the file is a directory.
    public boolean isDirectory() {
        // All files are NOT directories, and NEVER will be
        return false;
    }

    // Returns the name of the file
    public String getName() {
        return name;
    }

    // Returns the size of the file
    @Override
    public int getSize() {
        return size;
    }

    // Returns the creation time of the file
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    // Accept method for visitors to visit files
    @Override
    public void accept(FSVisitor visitor) {
        visitor.visit(this);
    }
}
