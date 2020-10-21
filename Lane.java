import java.util.PriorityQueue;
/*
 * The lane object represents a line of people waiting to check out.
 * It has a method to add a customer to itself, as well as data to calculate how long it will take
 * for each customer to check out.
*/

public class Lane extends PriorityQueue<Customer> {

    private static final long serialVersionUID = 1L;
    private int laneNumber;
    double checkoutRate;
    int paymentTime;
    boolean isExpress;
    double currentWaitTime = 0;
    double timeTilCheckedOut = 0;
    String type;
    double timeToCheckoutCurrentCustomer;

    public Lane() {
    }

    public Lane(boolean isExpress, int laneNumber) {
        if (isExpress) {
            this.isExpress = true;
            this.type = "E";
            this.checkoutRate = .1;
            this.paymentTime = 1;
        } else {
            this.isExpress = false;
            this.type = "N";
            this.checkoutRate = .05;
            this.paymentTime = 2;
        }
        this.laneNumber = laneNumber;
    }

    public void addCustomerToCheckoutLine(Customer c) {
        c.setWaitTime(calculateWaitTime()); //Here,since we have the wait time, we can proceed to calculate checkout time.
        c.setPeopleInFront(this.size());
        this.offer(c);

        c.addCheckedOutEvent(this);

    }
    
    public double calculateWaitTime(){
        double totalWaitTime = 0;
                    for(Customer N : this){
                    totalWaitTime += (N.getShoppingList()
                    * this.checkoutRate + this.paymentTime);
                    }
                    return totalWaitTime;
    }

    public int getLaneNumber() {
        return laneNumber;
    }
}
