/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
public class CheckedOutEvent extends Event {
    private double waitTime;
    private int numOtherPeeps;
    private Lane x;

    public CheckedOutEvent(Customer custy, Lane N) {
        super.setCustomer(custy);
        super.setTimeOfOccurence(custy.getDoneShoppingTime() + custy.getWaitTime()
                + ((custy.getShoppingList() * N.getCheckoutRate()) + N.getPaymentTime()));
        x = N; 
        numOtherPeeps = custy.getPeopleInFront();
        waitTime = custy.getWaitTime();
    }

    public void execute() {
        x.poll();
        System.out.printf("%.2f: Finished Checkout Customer %d on lane %d (" + this.getCustomer().getExpressElgibility()
                + ") (%.2f minute wait, %d other people in line -- finished shopping at %.2f front of the line at %.2f",
                this.getTimeOfOccurence(), this.getCustomer().getCustomerNumber(), x.getLaneNumber(), waitTime,
                numOtherPeeps, this.getCustomer().getDoneShoppingTime(),
                this.getTimeOfOccurence() - (x.getCheckoutRate() * this.getCustomer().getShoppingList() + x.getPaymentTime()));
        System.out.println();
    }

}
