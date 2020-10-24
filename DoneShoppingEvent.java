
/*
 * The DoneShopping event is what is created when the customer finishes shopping.
 * It adds the customer to the checkout center when it executes. 
*/
public class DoneShoppingEvent extends Event {
    private CheckoutCenter checkoutLanes;

    public DoneShoppingEvent(Customer C, CheckoutCenter checkoutLanes) {
        super.setTimeOfOccurence(C.getTimeBeforeCheckout() + C.getArrivalTime());
        this.checkoutLanes = checkoutLanes;
        super.setCustomer(C);
    }

    public void execute() {
        System.out.printf("%.2f", this.getTimeOfOccurence());
        System.out.println(": Finished shopping customer " + this.getCustomer().getCustomerNumber());
        checkoutLanes.addCustomerToALane(this.getCustomer());
    }

}