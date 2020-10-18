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
        if(x.size()-1 == 0){
            waitTime = 0;
        }
        else{
        waitTime = this.timeOfOccurence - (x.checkoutRate * thisCustomer.getShoppingList() + x.paymentTime) - (thisCustomer.getTimeBeforeCheckout() + thisCustomer.getArrivalTime());
        }
        // this.waitTime = waitTime;
    }
    

    public void execute() {
        System.out.printf("%.2f", timeOfOccurence);
        System.out.println(": Finished Checkout Customer " + thisCustomer.getCustomerNumber() + " on Lane " + laneUsed + " (" + thisCustomer.getExpressElgibility() + ") (" +  (waitTime) + " minute wait, " + (numOtherPeeps-1) +  " people in line -- finished shopping at " + thisCustomer.myDoneShoppingEvent.getTimeofOccurence() +  " front of the line at " + Math.ceil(this.getTimeofOccurence()-(x.checkoutRate * thisCustomer.getShoppingList() + x.paymentTime)));
    }

}
