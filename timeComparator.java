import java.util.Comparator;

public class timeComparator implements Comparator<Customer> {

    @Override
    public int compare(Customer o1, Customer o2) {
        if(o1.getArrivalTime() > o2.getArrivalTime()){
            return 1;
        }
        if(o1.getArrivalTime() < o2.getArrivalTime()){
            return -1;
        }
        else return 0;
    }
    
}
