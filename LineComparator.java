import java.util.Comparator;

public class LineComparator implements Comparator<NormalLane> {
//Method to sort arraylist of registers by register with shortest checkout line.
//Somewhere, add a method to make sure customers get sorted into shortest line by type (express or regular.)
    @Override
    public int compare(NormalLane o1, NormalLane o2) {
        if(o1.getEmptySpaces() < o2.getEmptySpaces()){
            return 1;
        }
        if(o1.getEmptySpaces() > o2.getEmptySpaces()){
            return -1;
        }
        else return 0;
    }
    
}
