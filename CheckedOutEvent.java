/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    private double waitTime;
    private int numOtherPeeps;
    private Lane x;

    public CheckedOutEvent(Customer custy, Lane N) {
        N.poll();
        super.setCustomer(custy);
        super.setTimeOfOccurence(custy.getDoneShoppingTime() + custy.getWaitTime()
        + N.timeToCheckoutCurrentCustomer);
        x = N;
        numOtherPeeps = custy.getPeopleInFront();
        if (numOtherPeeps == 0) {
            this.waitTime = 0;
        } else { // Else, wait time is the time you checked out minus however long it took you to
                 // arrive and shop.
            this.waitTime = this.getTimeOfOccurence() - custy.getDoneShoppingTime();
        }
        custy.setWaitTime(this.waitTime);
    }

    public void execute() {
     
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + this.getCustomer().getExpressElgibility()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                this.getTimeOfOccurence(), this.getCustomer().getCustomerNumber(), x.getLaneNumber(), waitTime, numOtherPeeps,
                this.getCustomer().getDoneShoppingTime(),
                this.getTimeOfOccurence() - (x.checkoutRate * this.getCustomer().getShoppingList() + x.paymentTime));
        System.out.println();
    }

}
