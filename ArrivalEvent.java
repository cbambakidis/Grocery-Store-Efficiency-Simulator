public class ArrivalEvent extends Event{
    double timeOfOccurence;
    public ArrivalEvent(Customer c){
        this.timeOfOccurence = c.getArrivalTime();
        System.out.println(timeOfOccurence + ": Arrival Customer " + c.myCustomerNumber);

    }
    public void execute(){        
    }
        
    }