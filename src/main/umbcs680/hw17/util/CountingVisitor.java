package umbcs680.hw17.util;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.Link;
import umbcs680.hw17.fs.FSVisitor;

/* This CountingVisitor class should represent a visitor that counts the number of
   directories, files, and links in a file system. It should have a counter for
   each file type. The visitor should start at the root directory and counts everything.
   Using method overload to visit directories, files, & links. Counters cannot be negative.
 */

public class CountingVisitor implements FSVisitor {
    private int numberOfDirectories = 0;
    private int numberOfFiles = 0;
    private int numberOfLinks = 0;

    // Increments the directory counter
    private void incrementDirectoryNumber() {
        numberOfDirectories++;
    }

    // Increments the file counter
    private void incrementFileNumber() {
        numberOfFiles++;
    }

    // Increments the link counter
    private void incrementLinkNumber() {
        numberOfLinks++;
    }

    // Calls to the interface to visit directories
    @Override
    public void visit(Directory directory) {
        incrementDirectoryNumber();
        // Counter cannot be less than 0
        if (numberOfDirectories < 0) {
            numberOfDirectories = 0;
        }
    }

    // Calls to the interface to visit files
    @Override
    public void visit(File file) {
        incrementFileNumber();
        // Counter cannot be less than 0
        if (numberOfFiles < 0) {
            numberOfFiles = 0;
        }
    }

    // Calls to the interface to visit links
    @Override
    public void visit(Link link) {
        incrementLinkNumber();
        // Counter cannot be less than 0
        if (numberOfLinks < 0) {
            numberOfLinks = 0;
        }
    }

    // Returns the number of directories in the file system
    public int getDirectoryNumber() {
        return numberOfDirectories;
    }

    // Returns the number of files in the file system
    public int getFileNumber() {
        return numberOfFiles;
    }

    // Returns the number of links in the file system
    public int getLinkNumber() {
        return numberOfLinks;
    }
}