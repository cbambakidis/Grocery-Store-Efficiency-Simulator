public class ArrivalEvent extends Event{
    double timeOfOccurence;
    Customer AssociatedCustomer;
    public ArrivalEvent(Customer C){
        this.timeOfOccurence = C.getArrivalTime();
        super.timeOfOccurence = C.getArrivalTime();
        AssociatedCustomer = C;
    }
    public void execute(){ 
        System.out.println(timeOfOccurence + ": Arrival Customer " + AssociatedCustomer.getCustomerNumber());
    }
        
    }