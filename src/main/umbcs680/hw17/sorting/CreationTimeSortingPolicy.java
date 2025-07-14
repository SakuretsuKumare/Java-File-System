package umbcs680.hw17.sorting;
import umbcs680.hw17.fs.FSElement;
import java.util.Comparator;

/*  This class represents a sorting strategy by sorting the elements by their creation
    time in ascending order. Elements created sooner will be first in the list.
 */

public class CreationTimeSortingPolicy implements Comparator<FSElement> {
    @Override
    public int compare(FSElement element1, FSElement element2) {
        // Oldest files will be the first on the list
        return Comparator.comparing(FSElement::getCreationTime).compare(element1, element2);
    }
}