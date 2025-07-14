package umbcs680.hw17.fs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/*  These should test that the file system should properly return only one instance as a singleton
    class.
 */

public class FileSystemTest {
    // Using implicit setup
    private static FileSystem fileSystem;
    private static Directory rootDirectory;

    // Setting up fixtures
    @BeforeAll
    public static void setUp() {
        // Creating the file system and making the structure of the file system
        fileSystem = FileSystem.getFileSystem();

        // The parent directory has a src directory and a readme.md file
        rootDirectory = new Directory(null, "repo", LocalDateTime.now()); // Root directory
        Directory srcDirectory = new Directory(rootDirectory, "src", LocalDateTime.now());
        rootDirectory.appendChild(srcDirectory);
        File readMeFile = new File("readme.md", LocalDateTime.now(), 1, rootDirectory);
        rootDirectory.appendChild(readMeFile);

        // The src directory has 2 directories; a main and a test directory
        Directory mainDirectory = new Directory(srcDirectory, "main", LocalDateTime.now());
        Directory testDirectory = new Directory(srcDirectory, "test", LocalDateTime.now());
        srcDirectory.appendChild(mainDirectory);
        srcDirectory.appendChild(testDirectory);

        // The main directory has 2 files, A.java and B.java
        File aJavaFile = new File("A.java", LocalDateTime.now(), 133, mainDirectory);
        File bJavaFile = new File("B.java", LocalDateTime.now(), 147, mainDirectory);
        mainDirectory.appendChild(aJavaFile);
        mainDirectory.appendChild(bJavaFile);

        // The test directory has 2 files, ATest.java and BTest.java
        File aTestJavaFile = new File("ATest.java", LocalDateTime.now(), 129, testDirectory);
        File bTestJavaFile = new File("BTest.java", LocalDateTime.now(), 138, testDirectory);
        testDirectory.appendChild(aTestJavaFile);
        testDirectory.appendChild(bTestJavaFile);

        // The root directory will be set to the file system
        fileSystem.appendRootDir(rootDirectory);
    }

    // Should only return 1 instance for singleton. If another instance is created, it should be the same instance
    @Test
    public void testFileSystemSingleton() {
        // Creating 2 instances of the file system
        FileSystem fileSystem1 = FileSystem.getFileSystem();
        FileSystem fileSystem2 = FileSystem.getFileSystem();

        // Should just be the first instance because it's a singleton class
        assertSame(fileSystem1, fileSystem2);
    }

    // Verifying the root directory as the parent directory
    @Test
    public void testGetRootDirectories() {
        // The root directory should be the repo
        LinkedList<Directory> repoDirectory = fileSystem.getRootDirectory();

        // Should be the first element of the file system
        assertTrue(repoDirectory.contains(rootDirectory));
    }

    // Verifying that the main and test directory both have the same src parent directory
    @Test
    public void testVerifyDirectoryEqualityRoot() {
        // The src directory has 2 sub directories, the main and the test directories
        Directory srcParentDirectory = (Directory) rootDirectory.getChildren().get(0);
        Directory mainDirectory = (Directory) srcParentDirectory.getChildren().get(0);
        Directory testDirectory = (Directory) srcParentDirectory.getChildren().get(1);

        // They should both have the same src parent directory
        assertSame(srcParentDirectory, mainDirectory.getParent());
        assertSame(srcParentDirectory, testDirectory.getParent());
    }

    // Testing to get all the files in the file system
    @Test
    public void testGetFilesInFileSystem() {
        List<File> filesInRootDir = rootDirectory.getFiles();

        // It has 1 readme, 2 java, and 2 test files. 5 total
        assertEquals(5, filesInRootDir.size());
    }
}