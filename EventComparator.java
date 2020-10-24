import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    // Used to keep events sorted in the main event list. 
    //Keeps events that happen sooner, at the front.
    @Override
    public int compare(Event o1, Event o2) {
        if (o1.getTimeOfOccurence() > o2.getTimeOfOccurence()) {
            return 1;
        }
        if (o1.getTimeOfOccurence() < o2.getTimeOfOccurence()) {
            return -1;
        } else
            return 0;
    }

}
