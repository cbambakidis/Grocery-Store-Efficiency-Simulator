public class ArrivalEvent extends Event {

        /*
         * The arrival event has a time of occurence of when the customer arrives in the store, from the text file.
         * When it executes, it just prints that it has arrived in the store.
        */
    public ArrivalEvent(Customer C) {
        super.setTimeOfOccurence(C.getArrivalTime());
        super.setCustomer(C);
    }

    public void execute() {
        System.out.println(this.getTimeOfOccurence() + ": Arrival Customer " + this.getCustomer().getCustomerNumber());
        super.getCustomer().scheduleCheckout();
    }

}