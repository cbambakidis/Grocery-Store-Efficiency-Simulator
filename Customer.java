import java.util.Queue;
public class Customer implements Comparable {
    private double arrivalTime;
    private ArrivalEvent myArrivalEvent;
    private DoneShoppingEvent myDoneShoppingEvent;
    private Queue<Event> orderOfEvents;
    private int shoppingList;
    private int myCustomerNumber;
    private double shoppingSpeed;
    private double timeBeforeCheckout;
    private double totalTimeInStore;
    private boolean isElgibleForExpress = false;
    private int numberPeepsInFront = 0;
    private double checkoutTime;

    /*
     * Makes a new customer object, using the information from the text file, as
     * well as giving the customer access to the event list and checkout center so
     * that it can schedule itself to be done shopping. Upon construction, the
     * customer object calculates how much time it will need to be done shopping,
     * then makes an event for it. Offers both the arrival and done shopping events
     * to the event list. Does not add Checked Out event, because that time of
     * occurence depends on how long they wait in line. We have getters for each of
     * the private variables.
     */
    public Customer(double arrivalTime, int shoppingListSize, double shoppingSpeed, int customerNumber,
            Queue<Event> eventList, CheckoutCenter checkoutLanes) {
        this.arrivalTime = arrivalTime;
        this.shoppingList = shoppingListSize;
        this.shoppingSpeed = shoppingSpeed;
        myCustomerNumber = customerNumber;
        timeBeforeCheckout = (this.shoppingSpeed * this.shoppingList);
        myArrivalEvent = new ArrivalEvent(this);
        myDoneShoppingEvent = new DoneShoppingEvent(this, checkoutLanes);
        orderOfEvents = eventList;
        if (shoppingListSize <= 12) {
            isElgibleForExpress = true;
        }
        orderOfEvents.offer(myArrivalEvent);
    }
     
    public void scheduleCheckout(){
    orderOfEvents.offer(myDoneShoppingEvent);
    }

    public void setWaitTime(double timeWaitingInLine) {
        totalTimeInStore = timeWaitingInLine;
    }

    public void setPeopleInFront(int x) {
        this.numberPeepsInFront = x;
    }

    public double getWaitTime() {
        return totalTimeInStore;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public boolean getExpressElgibility() {
        return isElgibleForExpress;
    }

    public int getCustomerNumber() {
        return myCustomerNumber;
    }

    public double getTimeBeforeCheckout() {
        return timeBeforeCheckout;
    }

    public int getShoppingList() {
        return shoppingList;
    }

    public double getDoneShoppingTime() {
        return myDoneShoppingEvent.getTimeOfOccurence();
    }

    public int getPeopleInFront() {
        return numberPeepsInFront;
    }

    public String toString() {
        return myCustomerNumber + "";
    }

    public void addCheckedOutEvent(Lane N) {
        CheckedOutEvent d = new CheckedOutEvent(this, N);
        orderOfEvents.add(d);
        checkoutTime = d.getTimeOfOccurence(); 
    }

    public double getCheckoutTime(){
        return checkoutTime;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
