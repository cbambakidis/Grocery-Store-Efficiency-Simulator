import java.util.ArrayList;

/*
 * The DoneShopping event is what is created when the customer finishes shopping.
 * It adds the customer to the checkout center when it executes. It also determines if the
 * Customer can use the express lane.
*/
public class DoneShoppingEvent extends Event {
    private double doneShoppingTime;
    double timeOfOccurence;
    Customer thisCustomer;
    boolean isElgibleForExpress;
    ArrayList<Lane> options;
    checkoutCenter checkoutLanes;

    public DoneShoppingEvent(Customer C, checkoutCenter checkoutLanes) {
        doneShoppingTime = C.getTimeBeforeCheckout();
        this.timeOfOccurence = C.getTimeBeforeCheckout() + C.arrivalTime;
        super.timeOfOccurence = C.getTimeBeforeCheckout() + C.arrivalTime;
        this.checkoutLanes = checkoutLanes;
        thisCustomer = C;
        super.C = thisCustomer;
        if (C.getShoppingList() < 10) {
            isElgibleForExpress = true;
        }
    }

    public void execute() {
        System.out.println(timeOfOccurence + ": Finished shopping customer " + thisCustomer.getCustomerNumber());
        checkoutLanes.addCustomerToALane(thisCustomer);
    }

}