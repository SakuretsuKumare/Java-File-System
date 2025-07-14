package umbcs680.hw17.fs;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/*  This class represents a directory or folder. It is a subclass of the FSElement superclass
    and both the Directory and File classes should share a common interface. The size of a
    directory is always 0. It should handle methods to add a file or directory to the directory
    (appending child), it should get the children of the directory, count the children,
    get sub directories, get sub files, get the total size of the children, and check if a
    directory is a directory. Has an override accept method for accepting visitors.
 */

public class Directory extends FSElement {
    // Contains what elements are in the directory
    protected LinkedList<FSElement> children;

    // Public constructor to make a directory
    public Directory(Directory parent, String name, LocalDateTime creationTime) {
        super(parent, name, creationTime);
        children = new LinkedList<>();
    }

    // Getter method for getting the element children of the directory
    public LinkedList<FSElement> getChildren() {
        return children;
    }

    public LinkedList<FSElement> getChildren(Comparator<FSElement> comparator) {
        LinkedList<FSElement> listOfChildren = new LinkedList<>(children);
        Collections.sort(listOfChildren, comparator);
        return listOfChildren;
    }

    // Adds the file or directory to the directory
    public void appendChild(FSElement child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    // Counts the amount of elements in the directory
    public int countChildren() {
        return children.size();
    }

    // Only returns the directories in the directory
    public List<Directory> getSubDirectories() {
        List<Directory> subDirectories = new ArrayList<>();
        children.forEach(child -> {
            if (child instanceof Directory) {
                subDirectories.add((Directory) child);
            }
        });
        return subDirectories;
    }

    public LinkedList<Directory> getSubDirectories(Comparator<FSElement> comparator) {
        LinkedList<Directory> listOfSubDirectories = new LinkedList<>();
        children.forEach(child -> {
            if (child instanceof Directory) {
                listOfSubDirectories.add((Directory) child);
            }
        });
        listOfSubDirectories.sort(comparator);
        return listOfSubDirectories;
    }

    /*  Have a recursive function to return all of the children files in the directory. If it
        is a directory, then we will add all of the files within that directory too. */
    public List<File> getFiles() {
        // Holds all of the children files
        List<File> allChildrenFiles = new LinkedList<>();

        // Looping through each child element in the directory
        children.forEach(child -> {
            // If the child file is a file, then it will be added to the list of children files
            if (child instanceof File) {
                allChildrenFiles.add((File) child);
            }
            // If the child file is a directory, it will recursively call to get files in children
            else if (child instanceof Directory) {
                allChildrenFiles.addAll(((Directory) child).getFiles());
            }
        });

        return allChildrenFiles;
    }

    public LinkedList<File> getFiles(Comparator<FSElement> comparator) {
        LinkedList<File> listOfFiles = new LinkedList<>(getFiles());
        Collections.sort(listOfFiles, comparator);
        return listOfFiles;
    }

    public LinkedList<Link> getLinks() {
        LinkedList<Link> linksList = new LinkedList<>();
        children.forEach(child -> {
            if (child instanceof Link) {
                linksList.add((Link) child);
            }
        });
        return linksList;
    }

    public LinkedList<Link> getLinks(Comparator<FSElement> comparator) {
        LinkedList<Link> linksList = new LinkedList<>();

        children.forEach(child -> {
            if (child instanceof Link) {
                linksList.add((Link) child);
            }
        });
        linksList.sort(comparator);
        return linksList;
    }

    // Gets the size of all of the children in this directory.
    public int getTotalsize() {
        int totalSizeOfChildren = 0;
        for (FSElement child : children) {
            totalSizeOfChildren += child.getSize();
        }
        return totalSizeOfChildren;
    }

    // Bool check to see if the directory is a directory.
    public boolean isDirectory() {
        // All directories are directories, it will always be TRUE
        return true;
    }

    // Returns the parent of the directory
    public Directory getParent() {
        return this.parent;
    }

    // Size of a directory is 0
    @Override
    public int getSize() {
        return 0;
    }

    // Returns the creation time of the element
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    // Accept method for visitors to visit directories
    @Override
    public void accept(FSVisitor visitor) {
        // Visiting the directory
        visitor.visit(this);
        // Visiting all children elements in the directory
        children.forEach(child -> child.accept(visitor));
    }
}