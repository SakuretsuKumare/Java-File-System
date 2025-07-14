package umbcs680.hw17.fs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*  Should test that each file is made properly with the correct attributes; like the name,
    size of the file, and the parent. Files also cannot have a negative size. Also verify
    that files are not a directory. Also verify that two different files with the same
    attributes are equal.
 */

public class FileTest {
    // Using implicit setup
    private static File file;

    // Setting up fixtures
    @BeforeAll
    public static void setUp() {
        // Making a file to be used for every test method
        file = new File("Just a file", LocalDateTime.now(), 83, null);
    }

    /// Creating two separate files with the same attributes. They should be the same
    @Test
    public void testDifferentFilesEquality() {
        LocalDateTime creationTime = LocalDateTime.now();
        File file = new File("Equal Files", creationTime, 57, null);
        File file2 = new File("Equal Files", creationTime, 57, null);

        // They should have the same file name
        assertEquals(file.name, file2.name);
        // They should have the same file size
        assertEquals(file.getSize(), file2.getSize());
        // They were both created at the same time
        assertEquals(file.creationTime, file2.creationTime);
        assertTrue(file.creationTime.equals(file2.creationTime));
    }

    // Verify the correct file name
    @Test
    public void testFileName() {
        assertEquals("Just a file", file.name);
    }

    // Properly make a file with the correct size of 83
    @Test
    public void testCorrectFileSize() {
        assertEquals(83, file.getSize());
    }

    // Testing if someone makes a file with negative size, reset to 0 if it is negative
    @Test
    public void testNegativeFileSize() {
        File negativeFileSize = new File("Negative File Size", LocalDateTime.now(), -100, null);
        // The size of the file should be reset to 0
        assertEquals(0, negativeFileSize.getSize());
    }

    // All files should never be a directory
    @Test
    public void testFileIsDirectory() {
        // Always returns false
        assertFalse(file.isDirectory());
    }

    // This file should not have a parent
    @Test
    public void testFileParent() {
        assertNull(file.parent); // Null
    }
}