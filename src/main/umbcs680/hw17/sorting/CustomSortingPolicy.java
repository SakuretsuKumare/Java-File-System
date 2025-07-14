package umbcs680.hw17.sorting;
import umbcs680.hw17.fs.Directory;
import umbcs680.hw17.fs.FSElement;
import umbcs680.hw17.fs.File;
import umbcs680.hw17.fs.Link;

import java.util.Comparator;

/*  This class represents a sorting strategy by sorting the elements directories first,
    then files, then links at the end. If two elements are of the same type, they will
    be sorted by alphabetical order. This custom class doesn't really use lambda expressions
    well.
 */

public class CustomSortingPolicy implements Comparator<FSElement> {
    @Override
    public int compare(FSElement element1, FSElement element2) {
        ////////// Directories come before Files and Links
        // If element 1 is a directory and element 2 isn't
        if (element1 instanceof Directory && !(element2 instanceof Directory)) {
            // Means that element 1 is a directory, so it comes before element2
            return -1;
        }

        // If element 1 isn't a directory and element 2 is
        else if (!(element1 instanceof Directory) && element2 instanceof Directory) {
            // Means that element 2 is a directory and it is before element 1
            return 1;
        }

        // If they are both directories, then sort by alphabetical order
        else if (element1 instanceof Directory && element2 instanceof Directory) {
            return element1.getName().compareTo(element2.getName());
        }

        // If One is a file and one is a link, prioritize files before links
        else if (element1 instanceof File && element2 instanceof Link) {
            return -1;
        }

        // If it's a link compared to a file, the second one takes priority
        else if (element1 instanceof Link && element2 instanceof File) {
            return 1;
        }

        // If they are both files, then compare them alphabetically
        else if (element1 instanceof File && element2 instanceof File) {
            return element1.getName().compareTo(element2.getName());
        }

        // If they are both links, compare their targets' names
        else if (element1 instanceof Link && element2 instanceof Link) {
            return ((Link) element1).getTargetElement().getName().compareTo(((Link) element2).getTargetElement().getName());
        }

        // If neither elements are files, then move down to links
        else {
            return element1.getName().compareTo(element2.getName());
        }
    }
}