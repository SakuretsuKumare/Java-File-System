package umbcs680.hw17.util;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.Link;
import umbcs680.hw17.fs.FSVisitor;

/*  This class is responsible for handling the ability to search a file
    in the file system by the name of the file. When visiting the file system,
    if it's a directory, then it will search through the children. If it's a
    file, it should compare the name of the file to check if it has found the
    right file, the boolean will return true. Do nothing with links.
 */

public class FileSearchVisitor implements FSVisitor {
    private String searchFileName;
    private boolean hasFoundTheFile;

    // Making a visit with the file name that you want to search as the parameter
    public FileSearchVisitor(String searchFileName) {
        this.searchFileName = searchFileName;
        this. hasFoundTheFile = false;
    }

    // When visiting a directory, check the files within the directory
    @Override
    public void visit(Directory directory) {
        // Check for every file in the directory
        directory.getFiles().forEach(file -> {
            if (file.getName().equals(searchFileName)) {
                // Returns true if/when found
                hasFoundTheFile = true;
            }
        });
    }

    // Check files with the same name
    @Override
    public void visit(File file) {
        // If the files have the same name
        if (file.getName().equals(searchFileName)) {
            // Returns true if/when found
            hasFoundTheFile = true;
        }
    }

    // If it's a link, don't compare it with files
    @Override
    public void visit(Link link) {
        // Not checking links
    }

    // After the file has been found, this returns the name of the file
    public String getFoundFile() {
        // If we have found the file with the same name
        if (hasFoundTheFile) {
            return searchFileName;
        }
        // We have not found the file
        else {
            return null;
        }
    }
}