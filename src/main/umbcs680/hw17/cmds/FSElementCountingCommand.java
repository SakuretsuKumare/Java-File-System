package umbcs680.hw17.cmds;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.FSCommand;
import umbcs680.hw17.util.CountingVisitor;

/*  This class represents the command class for counting file system elements. It
    overrides the execute method from the FSCommand interface, creates a new
    CountingVisitor and starts at the root directory.
 */

public class FSElementCountingCommand implements FSCommand {
    private final Directory rootDirectory;
    private int numberOfDirectories;
    private int numberOfFiles;
    private int numberOfLinks;

    public FSElementCountingCommand(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    // Execute for client code
    @Override
    public void execute() {
        // Creating a new counting visitor to count elements
        CountingVisitor countingVisitor = new CountingVisitor();
        rootDirectory.accept(countingVisitor); // Start counting at root directory

        // Getting the number of elements
        this.numberOfDirectories = countingVisitor.getDirectoryNumber();
        this.numberOfFiles = countingVisitor.getFileNumber();
        this.numberOfLinks = countingVisitor.getLinkNumber();
    }

    // Gets the number of directories
    public int getNumberOfDirectories() {
        return numberOfDirectories;
    }

    // Gets the number of files
    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    // Gets the number of links
    public int getNumberOfLinks() {
        return numberOfLinks;
    }
}