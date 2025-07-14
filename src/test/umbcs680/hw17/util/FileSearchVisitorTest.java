package umbcs680.hw17.util;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.FileSystem;
import umbcs680.hw17.fs.Link;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;

/*  This is supposed to test whether or not the FileSearchVisitor can properly
    find all the files within the file system. Not checking for directories or
    links. If it cannot find the file, return null.
 */

public class FileSearchVisitorTest {
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

    // Can properly find every file within the file system
    @Test
    public void testFileSearch() {
        // Creating a search visitor for the readme file
        FileSearchVisitor readmeFileVisitor = new FileSearchVisitor("readme.md");
        rootDirectory.accept(readmeFileVisitor);
        // Checks if there was a found file with that same name
        assertEquals("readme.md", readmeFileVisitor.getFoundFile());

        // Creating a search visitor for the A.java file
        FileSearchVisitor aFileVisitor = new FileSearchVisitor("A.java");
        rootDirectory.accept(aFileVisitor);
        assertEquals("A.java", aFileVisitor.getFoundFile());

        // Creating a search visitor for the B.java file
        FileSearchVisitor bFileVisitor = new FileSearchVisitor("B.java");
        rootDirectory.accept(bFileVisitor);
        assertEquals("B.java", bFileVisitor.getFoundFile());

        // Creating a search visitor for the ATest.java file
        FileSearchVisitor aTestFileVisitor = new FileSearchVisitor("ATest.java");
        rootDirectory.accept(aTestFileVisitor);
        assertEquals("ATest.java", aTestFileVisitor.getFoundFile());

        // Creating a search visitor for the BTest.java file
        FileSearchVisitor bTestFileVisitor = new FileSearchVisitor("BTest.java");
        rootDirectory.accept(bTestFileVisitor);
        assertEquals("BTest.java", bTestFileVisitor.getFoundFile());
    }

    // Verifying searching for a file that cannot be found, returns null
    @Test
    public void testSearchForNonexistantFile() {
        // A visitor for a file that is not in the file system
        FileSearchVisitor fakeFileSearchVisitor = new FileSearchVisitor("NotInFS.java");
        rootDirectory.accept(fakeFileSearchVisitor);

        // Cannot find the file within the file system, returns no found file
        assertNull(fakeFileSearchVisitor.getFoundFile());
    }
}