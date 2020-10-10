public class ArrivalEvent extends Event{
    private double timeEventHappens;
    double timeOfOccurence;
    private Customer d;
    public ArrivalEvent(Customer c){
        timeEventHappens = c.getArrivalTime();
        d = c;
        super.timeOfOccurence = c.getArrivalTime();
        this.timeOfOccurence = c.getArrivalTime();
    }
    public void execute(){
        System.out.println(timeEventHappens + ": Arrival Customer " + d.myCustomerNumber);
        
    }
        
    }