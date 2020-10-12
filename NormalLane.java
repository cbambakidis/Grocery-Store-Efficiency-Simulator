import java.util.LinkedList;
import java.util.PriorityQueue; //Change me to queue!!!!!!
import java.util.Queue;

public class NormalLane extends PriorityQueue<Customer>{

    private static final long serialVersionUID = 1L;
    int capacity;
    double checkoutRate = .05;
    int paymentTime = 2;
    double currentWaitTime = 0;
    String type;

    public NormalLane() {
        this.type = "Normal";
    }

    public void addCustomerToCheckoutLine(Customer c) {
        this.offer(c);
    }

}
