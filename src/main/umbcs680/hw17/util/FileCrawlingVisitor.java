package umbcs680.hw17.util;

import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.Link;
import umbcs680.hw17.fs.FSVisitor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*  This FileCrawlingVisitor class is responsible for returning a list of all
    the files in the file system. If it's a directory or link, do nothing.
    I have a set of files for files that have been visited, so no duplicate files
 */

public class FileCrawlingVisitor implements FSVisitor {
    // Keeps track of all the files that this has visited in a list
    private LinkedList<File> files = new LinkedList<>();
    private Set<File> visitedFiles = new HashSet<>();

    // Don't do anything when visiting directories
    @Override
    public void visit(Directory directory) {
        // Not checking for directories
    }

    // When visiting a file, add the file to the list of files
    @Override
    public void visit(File file) {
        // Adds the file if it has not been visited before
        if (!visitedFiles.contains(file)) {
            files.add(file);
            visitedFiles.add(file); // Mark the file as visited
            System.out.println("Has visited file: " + file.getName());
        }
    }

    // Don't do anything when visiting links
    @Override
    public void visit(Link link) {
        // Not checking for links
    }

    // Gets all of the files in the directory
    public LinkedList<File> getFiles() {
        return files;
    }
}