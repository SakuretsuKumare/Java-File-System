package umbcs680.hw17.cmds;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.FileSystem;
import umbcs680.hw17.fs.Link;

import java.time.LocalDateTime;

/*  Testing should include creating an element counting command and executing it. After
    visiting through the file system, it should be able to count all 4 directories, 5
    files, and 1 link.
 */

public class FSElementCountingCommandTest {
    // Using implicit setup
    private static FileSystem fileSystem;
    private static Directory rootDirectory;
    private static File readMeFile;
    private static Directory srcDirectory;
    private static Directory mainDirectory;
    private static Directory testDirectory;
    private static File aJavaFile;
    private static File bJavaFile;
    private static File aTestJavaFile;
    private static File bTestJavaFile;

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
        aJavaFile = new File("A.java", LocalDateTime.now(), 133, mainDirectory);
        bJavaFile = new File("B.java", LocalDateTime.now(), 147, mainDirectory);
        mainDirectory.appendChild(aJavaFile);
        mainDirectory.appendChild(bJavaFile);

        // The test directory has 2 files and a link, ATest.java and BTest.java, link to readme.md
        aTestJavaFile = new File("ATest.java", LocalDateTime.now(), 129, testDirectory);
        bTestJavaFile = new File("BTest.java", LocalDateTime.now(), 138, testDirectory);
        Link linkToFile = new Link(readMeFile, testDirectory, "rm.md", LocalDateTime.now());
        testDirectory.appendChild(aTestJavaFile);
        testDirectory.appendChild(bTestJavaFile);
        testDirectory.appendChild(linkToFile);

        // The root directory will be set to the file system
        fileSystem.appendRootDir(rootDirectory);
    }

    // Testing the Element Counting Execute command. It should have counted 4 directories, 5 files, and 1 link
    @Test
    public void testElementCountingCommand() {
        // Creating an element counting command
        FSElementCountingCommand fsElementCountingCommand = new FSElementCountingCommand(rootDirectory);
        // Executing the command
        fsElementCountingCommand.execute();

        // Trying to verify the correct number of elements counted
        int numberOfDirectories = fsElementCountingCommand.getNumberOfDirectories();
        int numberOfFiles = fsElementCountingCommand.getNumberOfFiles();
        int numberOfLinks = fsElementCountingCommand.getNumberOfLinks();

        // Should have 4 directories -> root repo, src, main, and test directories
        assertNotEquals(0, numberOfDirectories);
        assertEquals(4, numberOfDirectories);

        // Should have 5 files -> A, B, ATest, BTest, and readme
        assertNotEquals(0, numberOfFiles);
        assertEquals(5, numberOfFiles);

        // Should have 1 link -> readme link
        assertEquals(1, numberOfLinks);
    }
}