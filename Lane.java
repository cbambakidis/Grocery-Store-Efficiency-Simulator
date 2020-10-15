import java.util.PriorityQueue; 
/*
 * The lane object represents a line of people waiting to check out.
 * It has a method to add a customer to itself, as well as data to calculate how long it will take
 * for each customer to check out.
*/

public class Lane extends PriorityQueue<Customer> {

    private static final long serialVersionUID = 1L;
    int capacity;
    double checkoutRate = .05;
    int paymentTime = 2;
    double currentWaitTime = 0;
    double timeTilCheckedOut = 0;
    String type;
    double timeToCheckoutCurrentCustomer;

    public Lane() {
        this.type = "Normal";
    }

    public Lane(boolean isExpress) {
        if (isExpress == true) {
            this.type = "Express";
            this.checkoutRate = .1;
            this.paymentTime = 1;
        } else {
            this.type = "Normal";
        }
    }

    public void addCustomerToCheckoutLine(Customer c) {
        this.offer(c);
    }
}
