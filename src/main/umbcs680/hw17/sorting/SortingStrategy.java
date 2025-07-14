package umbcs680.hw17.sorting;
import umbcs680.hw17.fs.FSElement;
import java.util.LinkedList;

/*  Creating a sorting strategy interface to allow pluggable and exchangeable
    sorting policies without modifying the file system code.
 */

public interface SortingStrategy {
    LinkedList<FSElement> sort(LinkedList<FSElement> elements);
}