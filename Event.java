
/*
     * The event class is pretty straightforward. It has a time of occurence and a field to do something when it
     * executes. The subclasses choose what happens on execution.
     * The thing in common with all event subtypes is a customer it's associated with, and 
     * a time of occurence, i.e when the event happens.
    */
public class Event{
    private double timeOfOccurence;
    private Customer C;

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
