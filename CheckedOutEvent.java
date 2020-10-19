/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    Customer thisCustomer;
    private double waitTime;
    private int numOtherPeeps;
    private Lane x;

    public CheckedOutEvent(Customer custy, Lane N) {
        thisCustomer = custy;
        super.C = thisCustomer;
        this.timeOfOccurence = custy.getDoneShoppingTime() + custy.getWaitTime()
                + N.timeToCheckoutCurrentCustomer;
        super.timeOfOccurence = this.timeOfOccurence;
        x = N;
        numOtherPeeps = custy.getPeopleInFront();
        if (numOtherPeeps == 0) {
            this.waitTime = 0;
        } else { // Else, wait time is the time you checked out minus however long it took you to
                 // arrive and shop.
            this.waitTime = this.timeOfOccurence - thisCustomer.getDoneShoppingTime();
        }
        thisCustomer.setWaitTime(this.waitTime);
    }

    public void execute() {
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + thisCustomer.getExpressElgibility()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                timeOfOccurence, thisCustomer.getCustomerNumber(), x.getLaneNumber(), waitTime, numOtherPeeps,
                thisCustomer.getDoneShoppingTime(),
                this.getTimeOfOccurence() - (x.checkoutRate * thisCustomer.getShoppingList() + x.paymentTime));
        System.out.println();
    }

}
