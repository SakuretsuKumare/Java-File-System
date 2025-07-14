package umbcs680.hw17.fs;

import java.time.LocalDateTime;

/*  This class represents any file element such as a file or directory. It should have
    base properties that all elements should have. It should also tell you how big
    the file has, 0 size for a directory. Has an override accept method for accepting visitors
 */
public abstract class FSElement {
    // Holds information about the file element
    protected String name;
    //protected int size; // Can be overwritten by subclasses later
    protected LocalDateTime creationTime;
    protected Directory parent;

    // All file system elements will have these attributes
    public FSElement(Directory parent, String name, LocalDateTime creationTime) {
        this.name = name;
        //this.size = size;
        this.creationTime = creationTime;
        this.parent = parent;
    }

    // Gets the name of the element
    public String getName() {
        return name;
    }

    // Tells you the size of the element
    public abstract int getSize();

    // Gets the creation time of the element
    public abstract LocalDateTime getCreationTime();


    // Accept method for visitors
    public abstract void accept(FSVisitor visitor);
}