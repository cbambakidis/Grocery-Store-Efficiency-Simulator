
/*
     * The event class is pretty straightforward. It has a time of occurence and a field to do something when it
     * executes. 
    */
public class Event{
    private double timeOfOccurence;
    private Customer C;

    public Event() {
    }

    public void setTimeOfOccurence(double d){
        this.timeOfOccurence = d;
    }

    public void setCustomer(Customer c){
        C = c;
    }

    public Customer getCustomer(){
        return C;
    }

    public void execute() {
    }

    public double getTimeOfOccurence() {
        return timeOfOccurence;
    }

}
