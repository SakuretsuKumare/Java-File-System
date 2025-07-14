package umbcs680.hw17.fs;

import java.time.LocalDateTime;

/*  This class represents a shortcut link to a directory or file like a proxy. The
    size of these link elements are always 0. I pass in the target as a parameter
    to the public constructor to reference the target. Directories and files are
    linkable, and can also point to other links. Has an override accept method
    for accepting visitors.
 */

public class Link extends FSElement {
    // The target directory or file to have the shortcut to
    private FSElement target;

    // Public constructor to make a link
    public Link(FSElement target, Directory parent, String name, LocalDateTime creationTime) {
        // If the target is a link, then boolean linkable is false
        super(parent, name, creationTime);
        // Links cannot point to nothing
        if (target == null) {
            throw new IllegalArgumentException("The target cannot be null");
        }
        this.target = target;
    }

    // Getter method to check the target element
    public FSElement getTargetElement() {
        return target;
    }

    // The size of the shortcut is always 0, just like directories
    @Override
    public int getSize() {
        return 0;
    }

    // Returns the creation time of the link
    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    // Accept method for visitors to visit links
    @Override
    public void accept(FSVisitor visitor) {
        visitor.visit(this);
    }
}