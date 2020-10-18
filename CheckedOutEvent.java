/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    Customer thisCustomer;
    private double waitTime;
    private int laneUsed;
    int numOtherPeeps;
    private Lane x;

    public CheckedOutEvent(Customer custy, double time, int lane, int numOtherPeopleInLine, Lane N) {
        thisCustomer = custy;
        super.timeOfOccurence = time;
        super.C = thisCustomer;
        this.timeOfOccurence = time;
        laneUsed = lane;
        x = N;
        numOtherPeeps = numOtherPeopleInLine;
        if (numOtherPeeps == 0) {
            waitTime = 0;
        } else {
            waitTime = this.timeOfOccurence - (x.checkoutRate * thisCustomer.getShoppingList() + x.paymentTime)
                    - (thisCustomer.getTimeBeforeCheckout() + thisCustomer.getArrivalTime());
        }
        thisCustomer.setWaitTime(waitTime);
    }

    public void execute() {
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + thisCustomer.getExpressElgibility()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                timeOfOccurence, thisCustomer.getCustomerNumber(), x.getLaneNumber(), waitTime, numOtherPeeps - 1,
                thisCustomer.myDoneShoppingEvent.getTimeofOccurence(),
                this.getTimeofOccurence() - (x.checkoutRate * thisCustomer.getShoppingList() + x.paymentTime));
         System.out.println();
    }

}
