/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    private double waitTime;
    private int numOtherPeeps;
    private Lane laneTheyUsed;

    /*
     * @custy is the customer that the event belongs to.
     * @laneTheyCheckedOutIn is..well.. the lane they checked out in.
     * When this event is constructed, it's time of occurence is..
     * The time they finished shopping, plus
     * how long the customer takes to check out and pay, plus
     * how long they waited before they started checking out, which is calculated 
     * at the time they get added to the lane.
    */
    public CheckedOutEvent(Customer custy, Lane laneUsed) {
        super.setCustomer(custy);
        super.setTimeOfOccurence(custy.getDoneShoppingTime() + custy.getWaitTime()
                + ((custy.getShoppingList() * laneUsed.getCheckoutRate()) + laneUsed.getPaymentTime()));
        laneTheyUsed = laneUsed; 
        numOtherPeeps = custy.getPeopleInFront();
        waitTime = custy.getWaitTime();

        
    }

    /* 
     * The execute method for this class happens once the customer 
     * finishes checking out and paying.
     * Thus, the first thing we do is remove them from the line.
     * Then we output stuff about their checkout experience
    */ 
    public void execute() {
        laneTheyUsed.poll();
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + laneTheyUsed.size()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                this.getTimeOfOccurence(), this.getCustomer().getCustomerNumber(), laneTheyUsed.getLaneNumber(), waitTime,
                numOtherPeeps, this.getCustomer().getDoneShoppingTime(),
                this.getTimeOfOccurence() - (laneTheyUsed.getCheckoutRate() * this.getCustomer().getShoppingList() + laneTheyUsed.getPaymentTime()));
        System.out.println();
    }

}
