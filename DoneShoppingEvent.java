import java.util.ArrayList;

public class DoneShoppingEvent extends Event {

    private double doneShoppingTime;
    double timeOfOccurence;
    Customer thisCustomer;
    boolean isElgibleForExpress;
    ArrayList<NormalLane> options;
    checkoutCenter checkoutLanes;
    public DoneShoppingEvent(Customer C, checkoutCenter checkoutLanes) {
        doneShoppingTime = C.getTimeBeforeCheckout();
        this.timeOfOccurence = C.getTimeBeforeCheckout() + C.arrivalTime;
        this.checkoutLanes = checkoutLanes;
        thisCustomer = C;
        super.C = thisCustomer;
        if (C.getShoppingList() < 10) {
            isElgibleForExpress = true;
        }
    }

    public void execute() {
        checkoutLanes.addCustomerToALane(thisCustomer);
    }

}