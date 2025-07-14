package umbcs680.hw17.cmds;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.FSCommand;
import umbcs680.hw17.util.FileSearchVisitor;

/*  This class represents the command class for file searching in the file system.
    It overrides the execute method from the FSCommand interface, creates a new
    FileSearchVisitor and starts at the root directory. Can check if the file
    has been found with getFoundFile(), if it returns null then it doesn't exist.
 */

public class FileSearchCommand implements FSCommand {
    private final Directory rootDirectory;
    private final String searchFileName;
    private boolean fileHasBeenFound;

    public FileSearchCommand(Directory rootDirectory, String searchFileName) {
        this.rootDirectory = rootDirectory;
        this.searchFileName = searchFileName;
        this.fileHasBeenFound = false;
    }

    // Execute for client code
    @Override
    public void execute() {
        // Creating a new file search visitor to search for the name of the file
        FileSearchVisitor fileSearchVisitor = new FileSearchVisitor(searchFileName);
        rootDirectory.accept(fileSearchVisitor); // Start search at root directory
        // Update the boolean. Returns true if file has been found, false if not found. Null is file doesn't exist
        fileHasBeenFound = (fileSearchVisitor.getFoundFile() != null);
    }

    // Checks if the file has been found, true if it has been found. Else, returns false
    public boolean fileHasBeenFound() {
        return fileHasBeenFound;
    }
}