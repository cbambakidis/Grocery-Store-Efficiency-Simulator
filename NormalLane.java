import java.util.PriorityQueue;

public class NormalLane extends PriorityQueue<Customer>{
    int capacity;
    int checkoutRate;
    double currentWaitTime = 0;
    String type;
    public NormalLane(){
        this.type = "Normal";
    }
    public void addCustomerToCheckoutLine(Customer c){
        this.offer(c);
        //Adds the provided customer to this queue, schedules checkout event?
    }
    public void update(double time){
        if(!this.isEmpty()){
            currentWaitTime = 0;}
            else{
                if(this.peek() != null){
        currentWaitTime = this.peek().shoppingList*checkoutRate;}}
        if(currentWaitTime >= time){
            this.peek().scheduleCheckoutEvent(time);
            this.poll();
        }

        //checks if sim time, or time that's passed = calculated checkout time+time of arrival.
        //schedule checkout events and add to main event queue? We will only know
        //how long it will take for a customer to check out once they're added to the line..
    }
    public int getEmptySpaces(){
        //returns the amount of free spaces.
        return 1;
    }
    // public CheckedOutEvent scheduleCheckoutEvent(Customer c){

    //     //Check how long it will take for the given customer to be checked out. make new
    //     //checkout event with timeofoccurence calculated to be added to eventqueue. 
    //     return null;
    // }

}

