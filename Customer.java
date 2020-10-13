import java.util.Queue;

public class Customer implements Comparable {
    double arrivalTime;
    ArrivalEvent myArrivalEvent;
    DoneShoppingEvent myDoneShoppingEvent;
    Queue<Event> orderOfEvents;
    private int shoppingList;
    private int myCustomerNumber;
    private double shoppingSpeed;
    private double timeBeforeCheckout;
    private boolean isElgibleForExpress = false;

    public Customer(double arrivalTime, int shoppingListSize, double shoppingSpeed, int customerNumber, Queue<Event> eventList,
            checkoutCenter checkoutLanes) {
        this.arrivalTime = arrivalTime;
        this.shoppingList = shoppingListSize;
        this.shoppingSpeed = shoppingSpeed;
        timeBeforeCheckout = shoppingListSize * shoppingSpeed;
        myCustomerNumber = customerNumber;
        myArrivalEvent = new ArrivalEvent(this);
        myDoneShoppingEvent = new DoneShoppingEvent(this, checkoutLanes);
        orderOfEvents = eventList;
        if (shoppingListSize < 10) {
            isElgibleForExpress = true;
        }
        orderOfEvents.offer(myArrivalEvent);
        orderOfEvents.offer(scheduleDoneShoppingEvent());
    }

    public void setTotalTimeInStore(int timeWaitingInLine) {
        double totalTimeInStore = timeBeforeCheckout + timeWaitingInLine;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public boolean getExpressElgibility(){
        return isElgibleForExpress;
    }
    public int getCustomerNumber(){
        return myCustomerNumber;
    }
    public double getTimeBeforeCheckout(){
        return timeBeforeCheckout;
    }

    public int getShoppingList() {
        return shoppingList;
    }

    public double getShoppingSpeed() {
        return shoppingSpeed;
    }

    public void scheduleCheckoutEvent(double x) {
        CheckedOutEvent checkoutTime = new CheckedOutEvent(this, x);
        orderOfEvents.add(checkoutTime);
    }

    public Event scheduleDoneShoppingEvent() {
        return myDoneShoppingEvent;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
