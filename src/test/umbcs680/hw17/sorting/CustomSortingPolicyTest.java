package umbcs680.hw17.sorting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.FileSystem;
import umbcs680.hw17.fs.Link;
import umbcs680.hw17.fs.FSElement;
import java.time.LocalDateTime;
import java.util.LinkedList;

/*  This class is supposed to test that the comparator class is able to
    correctly sort the elements by different type order (Directories first,
    then files, then links at the end). If they are both the same type of
    element, then sort by alphabetical name.
 */

public class CustomSortingPolicyTest {
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

    // Test that the directories, files, and links are all sorted correctly by size using comparator
    @Test
    public void testCustomSortingPolicy() {
        // Gets all of the directories, files, and links
        LinkedList<FSElement> allElements = new LinkedList<>();
        allElements.addAll(srcDirectory.getSubDirectories());
        allElements.addAll(srcDirectory.getFiles());
        allElements.addAll(testDirectory.getLinks());

        // Sorts the elements with the custom sorting policy
        allElements.sort((element1, element2) -> {
            // Directories come before Files and Links
            // If element 1 is a directory and element 2 isn't
            if (element1 instanceof Directory && !(element2 instanceof Directory)) {
                // Means that element 1 is a directory, so it comes before element2
                return -1;
            }
            else if (!(element1 instanceof Directory) && element2 instanceof Directory) {
                // Means that element 2 is a directory and it is before element 1
                return 1;
            }
            else if (element1 instanceof Directory && element2 instanceof Directory) {
                // If they are both directories, then sort by alphabetical order
                return element1.getName().compareTo(element2.getName());
            }
            else if (element1 instanceof File && element2 instanceof Link) {
                // One is a file and one is a link, prioritize files before links
                return -1;
            }
            else if (element1 instanceof Link && element2 instanceof File) {
                // If it's a link compared to a file, the second one takes priority
                return 1;
            }
            else if (element1 instanceof File && element2 instanceof File) {
                // If they are both files, then compare them alphabetically
                return element1.getName().compareTo(element2.getName());
            }
            else if (element1 instanceof Link link1 && element2 instanceof Link link2) {
                // If they are both links, compare their targets' names
                return link1.getTargetElement().getName().compareTo(link2.getTargetElement().getName());
            }
            else {
                // If neither elements are files, then move down to links
                return element1.getName().compareTo(element2.getName());
            }
        });

        // Gets all of the directories
        LinkedList<FSElement> expectedElements = new LinkedList<>();
        expectedElements.add(mainDirectory);
        expectedElements.add(testDirectory);

        // Gets all of the files under the 2 directories
        expectedElements.add(aJavaFile);
        expectedElements.add(aTestJavaFile);
        expectedElements.add(bJavaFile);
        expectedElements.add(bTestJavaFile);

        // Gets the links
        expectedElements.add(linkToFile);

        // This should return all of the directories first, then all files, then links at the end
        assertEquals(expectedElements, allElements);
    }
}