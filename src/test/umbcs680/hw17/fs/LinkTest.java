package umbcs680.hw17.fs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*  This class is to test the Link class. It should test that the link is able to target the
    correct element (directory or file). It cannot target other links. In the professor's example,
    I am testing that the link in the test directory is correctly pointing to the readme file.
    Verify that the size of the link is 0. Also links cannot point to null targets.
 */

public class LinkTest {
    // Using implicit setup
    private static FileSystem fileSystem;
    private static Directory rootDirectory;
    private static File readMeFile;
    private static Directory srcDirectory;
    private static Directory mainDirectory;
    private static Directory testDirectory;

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
    }

    // Verifying that the link created in the test directory is actually referencing the readme file in the repo
    @Test
    public void testLinkToCorrectFile() {
        // Reference the rm.md link to the in the test directory
        Link linkToReadMeFile = (Link) testDirectory.getChildren().get(2);

        // The link should point to the readme.md file, and it should be the same element
        assertEquals(3, testDirectory.countChildren());
        assertSame(readMeFile, linkToReadMeFile.getTargetElement());
    }

    // Links are able to target directories as well as files
    @Test
    public void testLinkToDirectory() {
        // Get the link to the main directory from the src directory
        Link repoShortcutLink = new Link(mainDirectory, srcDirectory, "repo shortcut link", LocalDateTime.now());
        // Should be able to point to a directory
        assertSame(mainDirectory, repoShortcutLink.getTargetElement());
    }

    // Links can also point to other links
    @Test
    public void testLinkToLink() {
        // Create a new link to the link in the test directory
        Link linkToLink = new Link(testDirectory.getChildren().get(2), srcDirectory, "Link to Link", LocalDateTime.now());

        // Assert that the link does not point to another link
        assertSame(testDirectory.getChildren().get(2), linkToLink.getTargetElement());
    }

    // Links cannot have null target as parameters
    @Test
    public void testLinkNullTarget() {
        // Try to create a link with null target
        assertThrows(IllegalArgumentException.class, () -> {
            new Link(null, testDirectory, "Null Target Link", LocalDateTime.now());
        });
    }

    // The size of any link will always be 0
    @Test
    public void testLinkSizeIs0() {
        // Create a link
        Link exampleLink = new Link(readMeFile, testDirectory, "Example Link", LocalDateTime.now());

        // Assert that the size of the link is 0
        assertEquals(0, exampleLink.getSize());
    }
}