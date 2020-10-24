import java.util.PriorityQueue;
/*
 * The lane object represents a line of people waiting to check out.
 * It has a method to add a customer to itself, as well as methods to calculate how long it will take
 * for each customer to check out. It is mostly interacted with from the checkoutCenter.
*/

public class Lane extends PriorityQueue<Customer> implements Comparable<Lane> {

    /*
     * Self explanatory local vars. 
    */
    private static final long serialVersionUID = 1L;
    private int laneNumber;
    private double timeToCheckout;
    private double checkoutRate;
    private double paymentTime;
    private boolean isExpress;
    private double avgWaitTime;
    private int numberCustomersTotal = 0;

    public Lane() {

    }

    /*
     * @isExpress is the boolean determining whether or not this lane is an express
     * lane.
     * 
     * @laneNumber is just a number designated to each lane to help keep track of
     * them.
     * 
     */
    public Lane(boolean isExpress, int laneNumber) {
        if (isExpress) {
            this.isExpress = true;
            this.checkoutRate = .1;
            this.paymentTime = 1;
        } else {
            this.isExpress = false;
            this.checkoutRate = .05;
            this.paymentTime = 2;
        }
        this.laneNumber = laneNumber;
    }


    /*
     * @custyToBeAdded is the customer to be added to this lane. This method is
     * called from the checkoutCenter, after the customer has been sorted to the
     * shortest lane based on express elgibility.
     */
    public void addCustomerToCheckoutLine(Customer custyToBeAdded) {

        // If there is nobody in line, they don't have to wait.
        if (this.peek() == null) {
            custyToBeAdded.setWaitTime(0);
        }

        // If there's one person in line, they have to wait for them to be done
        // shopping.
        // This method calculates how far along they are based on when they arrived.
        else if (this.size() == 1) {
            timeToCheckout = ((this.peek().getShoppingList() * this.checkoutRate + this.paymentTime)
                    - (custyToBeAdded.getDoneShoppingTime() - this.peek().getDoneShoppingTime()));
            custyToBeAdded.setWaitTime(this.timeToCheckout);
            if (timeToCheckout <= 0) {
                custyToBeAdded.setWaitTime(0);
            }
        }

        // If there's multiple people in line, the customer joining the line will have
        // to wait
        // For everyone ahead of them to check out, as well as the guy at the front of
        // the line.
        else if (this.size() > 1) {
            this.timeToCheckout = (custyToBeAdded.getDoneShoppingTime() - this.peek().getDoneShoppingTime()
                    - (this.peek().getShoppingList() * this.checkoutRate + this.paymentTime));
            custyToBeAdded.setWaitTime(this.timeToCheckout + calculateWaitTime());
            if (timeToCheckout <= 0) {
                custyToBeAdded.setWaitTime(0);
            }
        }
        // After their wait time is calculated, they get added to the line, and their
        // checked out
        // event gets added to the event queue.
        avgWaitTime += Math.abs(this.timeToCheckout);
        numberCustomersTotal++;
        custyToBeAdded.setPeopleInFront(this.size());
        this.offer(custyToBeAdded);
        custyToBeAdded.addCheckedOutEvent(this);

    }

    /*
     * If there are more than 2 people in this lane, then this method is called
     * every time a customer tries to join the lane. It calculates how long it will
     * take everyone in front of the customer to check out, excluding the customer
     * at the head of the line, whos time is calculated above.
     */

    public double calculateWaitTime() {
        double totalWaitTime = 0;
        for (Customer N : this) {
            if (N != this.peek()) {
                totalWaitTime += (N.getShoppingList() * this.checkoutRate + this.paymentTime);
            }
        }
        return totalWaitTime;
    }

    // Various getters for statistical purposes, as well
    // as for use in the checkoutCenter.
    public int getTotalCustomers() {
        return numberCustomersTotal;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public boolean isExpress() {
        return isExpress;
    }

    public double getCheckoutRate() {
        return checkoutRate;
    }

    public double getPaymentTime() {
        return paymentTime;
    }

    public double getAvgWaitTime() {
        return this.avgWaitTime / this.numberCustomersTotal;
    }

    // I don't think this method actually gets used.. but just in case I filled it
    // in
    // since the class can't be comparable without it.
    @Override
    public int compareTo(Lane o) {
        if (this.getLaneNumber() > o.laneNumber) {
            return 1;
        }
        if (o.getLaneNumber() > this.laneNumber) {
            return -1;
        } else
            return 0;
    }
}
