import java.util.ArrayList;
import java.util.PriorityQueue;
/*
 * The lane object represents a line of people waiting to check out.
 * It has a method to add a customer to itself, as well as data to calculate how long it will take
 * for each customer to check out.
*/

public class Lane extends PriorityQueue<Customer> {

    private static final long serialVersionUID = 1L;
    int capacity;
    private int laneNumber;
    double checkoutRate = .05;
    int paymentTime = 2;
    double currentWaitTime = 0;
    double timeTilCheckedOut = 0;
    String type;
    double timeToCheckoutCurrentCustomer;

    public Lane() {
        this.type = "Normal";
    }

    public Lane(boolean isExpress, int laneNumber) {
        if (isExpress == true) {
            this.type = "Express";
            this.checkoutRate = .1;
            this.paymentTime = 1;
        } else {
            this.type = "Normal";
        }
        this.laneNumber = laneNumber;
    }

    public void addCustomerToCheckoutLine(Customer c) {
        this.offer(c);
    }

    public Customer get(int d){
        PriorityQueue<Customer> f = new PriorityQueue<>();
        f = this;
        for(int i=0; i!=d; i++){
            f.poll();
        }
        return f.peek();
    }

    public int getLaneNumber(){
        return laneNumber;
    }
}
