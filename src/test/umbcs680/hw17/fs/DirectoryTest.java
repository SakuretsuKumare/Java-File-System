package umbcs680.hw17.fs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*  Should test that each directory is made properly with the correct attributes; like the name,
    size of the directory, and the parent. Size of directories should always be 0. Also verify
    that directories are directories. We also want to test that two different directories
    with the same attributes are equal. It should also check the amount of children in the
    directory and also get the children. It should be able to verify the sub directories and
    the sub files. Also verify the size of the children files.
 */

public class DirectoryTest {
    // Using implicit setup
    private static Directory rootDirectory;

    // Setting up fixtures
    @BeforeAll
    public static void setUp() {
        // Setting up the directory structure

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
    }

    // Creating two separate directories with the same attributes. They should be the same
    @Test
    public void testDifferentDirectoriesEquality() {
        LocalDateTime creationTime = LocalDateTime.now();
        // Has same parent
        Directory parentDirectory = new Directory(null, "Parent Directory", LocalDateTime.now());
        Directory directory1 = new Directory(parentDirectory, "Equal Directories", creationTime);
        Directory directory2 = new Directory(parentDirectory, "Equal Directories", creationTime);

        // They should have the same directory name
        assertEquals(directory1.name, directory2.name);
        // They should have the same size of 0 for directories
        assertEquals(directory1.getSize(), directory1.getSize());
        // They were both created at the same time
        assertEquals(directory1.creationTime, directory2.creationTime);
        assertTrue(directory1.creationTime.equals(directory2.creationTime));
    }

    // Verify the correct directory name
    @Test
    public void testDirectoryName() {
        assertEquals("repo", rootDirectory.name);
    }

    // Testing that the size of the directories are 0
    @Test
    public void testCorrectDirectorySize() {
        assertEquals(0, rootDirectory.getSize());
    }

    // All directories are directories
    @Test
    public void testDirectoryIsDirectory() {
        // Always returns true
        assertTrue(rootDirectory.isDirectory());
    }

    // This directory should not have a parent
    @Test
    public void testDirectoryGetParent() {
        // Should be null
        assertNull(rootDirectory.getParent());
    }

    // Verifying that it properly returns the correct children in the directory
    @Test
    public void testGetDirectoryChildren() {
        // This should contain the child directory
        assertTrue(rootDirectory.getChildren().contains(rootDirectory.getChildren().get(0)));
        // This should contain the file
        assertTrue(rootDirectory.getChildren().contains(rootDirectory.getChildren().get(1)));
    }

    // Verifying the elements inside the directory
    @Test
    public void testCountDirectoryChildren() {
        // The root directory contains a child directory, and 1 file. Should have 2 elements
        assertEquals(2, rootDirectory.countChildren());
    }

    // Verify if getSubDirectories() returns all the subdirectories
    @Test
    public void testGetSubDirectories() {
        List<Directory> subDirectories = rootDirectory.getSubDirectories();

        // Child directory is a subdirectory, returns true
        assertTrue(subDirectories.contains(rootDirectory.getChildren().get(0)));
        // Sub file is not a directory, returns false
        assertFalse(subDirectories.contains(rootDirectory.getChildren().get(1)));
    }

    // Getting the files in the directory
    @Test
    public void testGetFiles() {
        List<File> allFiles = rootDirectory.getFiles();

        // The child directory is not a file so it should return false
        assertFalse(allFiles.contains(rootDirectory.getChildren().get(0)));
        // The file should return true
        assertTrue(allFiles.contains(rootDirectory.getChildren().get(1)));
    }

    // Verifying the total size of all of the files in the directory.
    @Test
    public void testGetTotalSize() {
        // Only has a readme file of 1, and a child src directory of size 0
        assertEquals(1, rootDirectory.getTotalsize());
    }
}