package umbcs680.hw17.cmds;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.FileSystem;
import umbcs680.hw17.fs.Link;

import java.time.LocalDateTime;

/*  Testing should include creating a file search command and executing it. After
    traversing through the file system, it should be able to search any file. It
    will return true if the found has been found, returns false if not found
 */

public class FileSearchCommandTest {
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

    // Can properly find every file within the file system through the command
    @Test
    public void testFileSearchCommand() {
        // Creating a file search command for the readme.md file
        FileSearchCommand readmeSearchCommand = new FileSearchCommand(rootDirectory, "readme.md");
        readmeSearchCommand.execute();
        assertTrue(readmeSearchCommand.fileHasBeenFound());

        // Creating a file search command for the A.java file
        FileSearchCommand aJavaSearchCommand = new FileSearchCommand(rootDirectory, "A.java");
        aJavaSearchCommand.execute();
        assertTrue(aJavaSearchCommand.fileHasBeenFound());

        // Creating a file search command for the B.java file
        FileSearchCommand bJavaSearchCommand = new FileSearchCommand(rootDirectory, "B.java");
        bJavaSearchCommand.execute();
        assertTrue(bJavaSearchCommand.fileHasBeenFound());

        // Creating a file search command for the ATest.java file
        FileSearchCommand aTestSearchCommand = new FileSearchCommand(rootDirectory, "ATest.java");
        aTestSearchCommand.execute();
        assertTrue(aTestSearchCommand.fileHasBeenFound());

        // Creating a file search command for the BTest.java file
        FileSearchCommand bTestSearchCommand = new FileSearchCommand(rootDirectory, "BTest.java");
        bTestSearchCommand.execute();
        assertTrue(bTestSearchCommand.fileHasBeenFound());
    }

    // Verifying searching for a file that cannot be found, returns false
    @Test
    public void testSearchNonexistantFiles() {
        // A visitor for a file that is not in the file system
        FileSearchCommand fakeFileSearchCommand = new FileSearchCommand(rootDirectory, "NotInFS.java");
        fakeFileSearchCommand.execute();
        // Should return false because the file is not in the file system
        assertFalse(fakeFileSearchCommand.fileHasBeenFound());
    }
}