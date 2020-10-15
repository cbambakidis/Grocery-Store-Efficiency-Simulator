public class ArrivalEvent extends Event {
    double timeOfOccurence;
    Customer AssociatedCustomer;
        /*
         * The arrival event has a time of occurence of when the customer arrives in the store, from the text file.
         * When it executes, it just prints that it has arrived in the store.
        */
    public ArrivalEvent(Customer C) {
        this.timeOfOccurence = C.getArrivalTime();
        super.timeOfOccurence = C.getArrivalTime();
        AssociatedCustomer = C;
    }

    public void execute() {
        System.out.println(timeOfOccurence + ": Arrival Customer " + AssociatedCustomer.getCustomerNumber());
    }

}