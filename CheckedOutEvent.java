public class CheckedOutEvent extends Event {
    double timeCheckoutOccurs;
    Customer thisCustomer;

    public CheckedOutEvent(Customer custy, double time) {
        thisCustomer = custy;
        timeCheckoutOccurs = time;
        super.timeOfOccurence = timeCheckoutOccurs;
        super.C = thisCustomer;
        this.timeOfOccurence = timeCheckoutOccurs;
    }

    public void execute() {
        System.out.println(timeOfOccurence + ": Customer " + thisCustomer.getCustomerNumber()
                + " has checked out and left the store.");
    }

}
