package umbcs680.hw17.sorting;
import umbcs680.hw17.fs.FSElement;
import java.util.Comparator;

/*  This class represents a sorting strategy by sorting the elements alphabetically
    in ascending order by name.
 */

public class AlphabeticalSortingPolicy implements Comparator<FSElement> {
    @Override
    public int compare(FSElement element1, FSElement element2) {
        // Ascending order, from lowest to highest using lambda expressions
        return Comparator.comparing(FSElement::getName).compare(element1, element2);
    }
}