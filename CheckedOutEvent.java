public class CheckedOutEvent extends Event{
    double timeCheckoutOccurs;
    Customer thisCustomer;
    public CheckedOutEvent (Customer custy, double time){
        thisCustomer = custy;
        timeCheckoutOccurs = time;
        super.timeOfOccurence = timeCheckoutOccurs;
        System.out.println(timeOfOccurence + "Checkout event constructed.");
    }
    public void execute(){
        System.out.println(timeOfOccurence + ": Customer " + thisCustomer.myCustomerNumber + " has checked out and left the store." );
    }




}
