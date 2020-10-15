/*
 * The CheckedOut event is the final event for the customer. The time of occurence depends on how long
 * they waited in line. 
*/
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
        System.out.printf("%.2f", timeOfOccurence);
        System.out.println(": Customer " + thisCustomer.getCustomerNumber()
                + " has checked out and left the store.");
    }

}
