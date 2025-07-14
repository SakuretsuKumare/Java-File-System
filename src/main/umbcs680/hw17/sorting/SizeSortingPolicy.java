package umbcs680.hw17.sorting;
import umbcs680.hw17.fs.FSElement;
import java.util.Comparator;

/*  This class represents a sorting strategy by sorting the elements by their size
    in ascending order.
 */

public class SizeSortingPolicy implements Comparator<FSElement> {
    @Override
    public int compare(FSElement element1, FSElement element2) {
        // Sorting from smallest size to biggest with lambda expressions
        return Comparator.comparingLong(FSElement::getSize).compare(element1, element2);
    }
}