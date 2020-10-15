    /*
     * The event class is pretty straightforward. It has a time of occurence and a field to do something when it
     * executes. 
    */
public class Event {
    double timeOfOccurence;
    String type;
    Customer C;

    public Event() {
    }

    public void execute() {
    }

    public double getTimeofOccurence() {
        return timeOfOccurence;
    }

}
