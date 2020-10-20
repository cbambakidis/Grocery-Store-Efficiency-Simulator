/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    private double waitTime;
    private int numOtherPeeps;
    private Lane x;

    public CheckedOutEvent(Customer custy, Lane N) {
        ///////
        super.setCustomer(custy);
        System.out.println(custy.getDoneShoppingTime() + " " + custy.getWaitTime() + " "
                + custy.getShoppingList() + " " + N.checkoutRate + " " + N.paymentTime + " " + N.type + " " + custy.getExpressElgibility());
        super.setTimeOfOccurence(custy.getDoneShoppingTime() + custy.getWaitTime()
                + ((custy.getShoppingList() * N.checkoutRate) + N.paymentTime));
        x = N; // if this doesn't work go back to timeotcheckoutcurrentcutomer method in update
               // method.
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
        x.poll();
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + this.getCustomer().getExpressElgibility()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                this.getTimeOfOccurence(), this.getCustomer().getCustomerNumber(), x.getLaneNumber(), waitTime,
                numOtherPeeps, this.getCustomer().getDoneShoppingTime(),
                this.getTimeOfOccurence() - (x.checkoutRate * this.getCustomer().getShoppingList() + x.paymentTime));
        System.out.println();
    }

}
