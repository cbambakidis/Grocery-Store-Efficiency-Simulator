
/*
 * The DoneShopping event is what is created when the customer finishes shopping.
 * It adds the customer to the checkout center when it executes. It also determines if the
 * Customer can use the express lane.
*/
public class DoneShoppingEvent extends Event {
    private double timeOfOccurence;
    private Customer thisCustomer;
    private checkoutCenter checkoutLanes;

    public DoneShoppingEvent(Customer C, checkoutCenter checkoutLanes) {
        this.timeOfOccurence = C.getTimeBeforeCheckout() + C.getArrivalTime();
        super.timeOfOccurence = this.timeOfOccurence;
        this.checkoutLanes = checkoutLanes;
        thisCustomer = C;
        super.C = thisCustomer;
    }


    public void execute() {
        System.out.printf("%.2f", timeOfOccurence);
        System.out.println(": Finished shopping customer " + thisCustomer.getCustomerNumber());
        checkoutLanes.addCustomerToALane(thisCustomer);
    }

}