package umbcs680.hw17.util;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.FileSystem;
import umbcs680.hw17.fs.Link;

import java.time.LocalDateTime;

/*  Test cases should verify that the CountingVisitor class correctly counts the amount of
    directories, files, and links within the file system. Accept the root directory and then
    traverse the file system from the root.
 */

public class CountingVisitorTest {
    // Using implicit setup
    private static FileSystem fileSystem;
    private static Directory rootDirectory;
    private static File readMeFile;
    private static Directory srcDirectory;
    private static Directory mainDirectory;
    private static Directory testDirectory;
    private static CountingVisitor countingVisitor;

    // Setting up fixtures
    @BeforeAll
    public static void setUp() {
        // Creating the file system and making the structure of the file system
        fileSystem = FileSystem.getFileSystem();

        // The parent directory has a src directory and a readme.md file
        rootDirectory = new Directory(null, "repo", LocalDateTime.now()); // Root directory
        srcDirectory = new Directory(rootDirectory, "src", LocalDateTime.now());
        rootDirectory.appendChild(srcDirectory);
        readMeFile = new File("readme.md", LocalDateTime.now(), 1, rootDirectory);
        rootDirectory.appendChild(readMeFile);

        // The src directory has 2 directories; a main and a test directory
        mainDirectory = new Directory(srcDirectory, "main", LocalDateTime.now());
        testDirectory = new Directory(srcDirectory, "test", LocalDateTime.now());
        srcDirectory.appendChild(mainDirectory);
        srcDirectory.appendChild(testDirectory);

        // The main directory has 2 files, A.java and B.java
        File aJavaFile = new File("A.java", LocalDateTime.now(), 133, mainDirectory);
        File bJavaFile = new File("B.java", LocalDateTime.now(), 147, mainDirectory);
        mainDirectory.appendChild(aJavaFile);
        mainDirectory.appendChild(bJavaFile);

        // The test directory has 2 files and a link, ATest.java and BTest.java, link to readme.md
        File aTestJavaFile = new File("ATest.java", LocalDateTime.now(), 129, testDirectory);
        File bTestJavaFile = new File("BTest.java", LocalDateTime.now(), 138, testDirectory);
        Link linkToFile = new Link(readMeFile, testDirectory, "rm.md", LocalDateTime.now());
        testDirectory.appendChild(aTestJavaFile);
        testDirectory.appendChild(bTestJavaFile);
        testDirectory.appendChild(linkToFile);

        // The root directory will be set to the file system
        fileSystem.appendRootDir(rootDirectory);

        // Create CountingVisitor instance. Root directory accepts visitor for starting location
        countingVisitor = new CountingVisitor();
        rootDirectory.accept(countingVisitor);
    }

    // Verifies that it counts all the right number of directories in the file system.
    @Test
    public void testCountingDirectories() {
        // Should have 4 directories -> root repo, src, main, and test directories
        assertNotEquals(0, countingVisitor.getDirectoryNumber());
        assertEquals(4, countingVisitor.getDirectoryNumber());
    }

    // Verifies that it counts all the right number of files in the file system.
    @Test
    public void testCountingFiles() {
        // Should have 5 files -> A, B, ATest, BTest, and readme
        assertNotEquals(0, countingVisitor.getFileNumber());
        assertEquals(5, countingVisitor.getFileNumber());
    }

    // Verifies that it counts all the right number of links in the file system.
    @Test
    public void testCountingLinks() {
        // Should have 1 link -> readme link
        assertNotEquals(0, countingVisitor.getLinkNumber());
        assertEquals(1, countingVisitor.getLinkNumber());
    }
}