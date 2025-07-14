package umbcs680.hw17.sorting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import umbcs680.hw17.fs.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;

/*  This class is supposed to test that the comparator class is able to
    correctly sort the elements by increasing alphabetical order.
 */

public class AlphabeticalSortingPolicyTest {
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
    }

    // Test that the directories, files, and links are all sorted correctly by alphabet using comparator
    @Test
    public void testAlphabeticalSorting() {
        // Gets all of the different elements in different lists in alphabetical order
        LinkedList<Directory> sortedDirectories = srcDirectory.getSubDirectories(Comparator.comparing(FSElement::getName));
        LinkedList<File> sortedFiles = srcDirectory.getFiles(Comparator.comparing(FSElement::getName));
        LinkedList<Link> sortedLinks = testDirectory.getLinks(Comparator.comparing(FSElement::getName));

        // The directories under src should have only the main and test directories
        LinkedList<Directory> expectedDirectories = new LinkedList<>();
        expectedDirectories.add(mainDirectory);
        expectedDirectories.add(testDirectory);
        assertEquals(expectedDirectories, sortedDirectories);

        // There are 4 files total under the main & test directories. Increasing alphabetical order
        LinkedList<File> expectedFiles = new LinkedList<>();
        expectedFiles.add(aJavaFile);
        expectedFiles.add(aTestJavaFile);
        expectedFiles.add(bJavaFile);
        expectedFiles.add(bTestJavaFile);
        assertEquals(expectedFiles, sortedFiles);

        // Under the test directory, there exists only 1 link
        LinkedList<Link> expectedLinks = new LinkedList<>();
        expectedLinks.add(linkToFile);
        assertEquals(expectedLinks, sortedLinks);
    }
}