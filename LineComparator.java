import java.util.Comparator;

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
}
