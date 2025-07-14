package umbcs680.hw17.cmds;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.FSCommand;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.util.FileCrawlingVisitor;
import java.util.LinkedList;

/*  This class represents the command class for file crawling in the file system.
    It overrides the execute method from the FSCommand interface, creates a new
    FileCrawlingVisitor and starts at the root directory.
 */

public class FileCrawlingCommand implements FSCommand {
    private final Directory rootDirectory;
    private LinkedList<File> files;

    public FileCrawlingCommand(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    // Execute for client code
    @Override
    public void execute() {
        // Creating a new file crawling visitor to get all of the files in the file system
        FileCrawlingVisitor fileCrawlingVisitor = new FileCrawlingVisitor();
        rootDirectory.accept(fileCrawlingVisitor); // Start traversal at root directory

        // Retrieve the files visited by the visitor
        files = fileCrawlingVisitor.getFiles();
    }

    // Returns the list of files
    public LinkedList<File> getFiles() {
        return files;
    }
}