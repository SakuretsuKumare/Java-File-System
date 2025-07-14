package umbcs680.hw17.sorting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import umbcs680.hw17.fs.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;

/*  This class is supposed to test that the comparator class is able to
    correctly sort the elements by increasing creation time order.
 */

public class CreationTimeSortingPolicyTest {
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
    private static Link linkToFile;
    private static CreationTimeSortingPolicy creationTimeSortingPolicy;

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
        aTestJavaFile = new File("ATest.java", LocalDateTime.now(), 138, testDirectory);
        bTestJavaFile = new File("BTest.java", LocalDateTime.now(), 129, testDirectory);
        linkToFile = new Link(readMeFile, testDirectory, "rm.md", LocalDateTime.now());
        testDirectory.appendChild(aTestJavaFile);
        testDirectory.appendChild(bTestJavaFile);
        testDirectory.appendChild(linkToFile);

        // The root directory will be set to the file system
        fileSystem.appendRootDir(rootDirectory);
        creationTimeSortingPolicy = new CreationTimeSortingPolicy();
    }

    // Test that the directories, files, and links are all sorted correctly by creation time using comparator
    @Test
    public void testCreationTimeSortingPolicy() {
        LinkedList<Directory> sortedDirectories = srcDirectory.getSubDirectories(Comparator.comparing(FSElement::getCreationTime));
        LinkedList<File> sortedFiles = srcDirectory.getFiles(Comparator.comparing(FSElement::getCreationTime));
        LinkedList<Link> sortedLinks = testDirectory.getLinks(Comparator.comparing(FSElement::getCreationTime));

        // Main was made before test directory
        LinkedList<Directory> expectedDirectories = new LinkedList<>();
        expectedDirectories.add(mainDirectory);
        expectedDirectories.add(testDirectory);
        assertEquals(expectedDirectories, sortedDirectories);

        // The regular java files were made before the test java files
        LinkedList<File> expectedFiles = new LinkedList<>();
        expectedFiles.add(aJavaFile);
        expectedFiles.add(bJavaFile);
        expectedFiles.add(aTestJavaFile);
        expectedFiles.add(bTestJavaFile);
        assertEquals(expectedFiles, sortedFiles);

        LinkedList<Link> expectedLinks = new LinkedList<>();
        expectedLinks.add(linkToFile);
        assertEquals(expectedLinks, sortedLinks);
    }
}