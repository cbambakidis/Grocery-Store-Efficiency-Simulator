import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;

public class LineComparator implements Comparator<NormalLane> {

    @Override
    public int compare(NormalLane o1, NormalLane o2) {
        if (o1.size() > o2.size()) {
            return 1;
        }
        if (o1.size() < o2.size()) {
            return -1;
        } else
            return 0;
    }


    // Method to sort arraylist of registers by register with shortest checkout
    // line.
    // Somewhere, add a method to make sure customers get sorted into shortest line
    // by type (express or regular.)


}
