package umbcs680.hw17.fs;

import java.util.LinkedList;

/*  This singleton class represents the file system. It can have multiple drives or
    tree structures (like C, D, E drives in Windows). It should be able to get the root
    directories in a LinkedList. It Should also be able to append the root directory
 */

public class FileSystem {
    private static FileSystem instance;
    private LinkedList<Directory> rootDirectory;

    // Define a private constructor
    private FileSystem() {
        rootDirectory = new LinkedList<>();
    }

    // Singleton making sure it only ever returns one instance of the file system
    public static FileSystem getFileSystem() {
        if (instance == null)
            instance = new FileSystem();
        return instance;
    }

    // Returns the root directories in a linked list
    public LinkedList<Directory> getRootDirectory() {
        return rootDirectory;
    }

    // Puts the root directory into the file system
    public void appendRootDir(Directory root) {
        rootDirectory.add(root);
    }
}