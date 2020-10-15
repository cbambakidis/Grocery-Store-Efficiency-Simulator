import java.util.Comparator;

public class LineComparator implements Comparator<Lane> {

    @Override
    public int compare(Lane o1, Lane o2) {
        if (o1.size() > o2.size()) {
            return 1;
        }
        if (o1.size() < o2.size()) {
            return -1;
        } else
            return 0;
    }
}
