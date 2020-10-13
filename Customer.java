import java.util.ArrayList;
import java.util.Queue;

public class Customer implements Comparable {
    // calculate shopping time here.
    double arrivalTime;
    ArrivalEvent myArrivalEvent;
    DoneShoppingEvent myDoneShoppingEvent;
    Queue<Event> orderOfEvents;
    int shoppingList;
    int myCustomerNumber;
    double shoppingSpeed;
    double timeBeforeCheckout;
    double timeToWait;
    double totalTimeInStore;
    boolean isElgibleForExpress = false;

    public Customer(double arrivalTime, int shoppingListSize, double shoppingSpeed, int customerNumber, Queue<Event> eventQ,
            checkoutCenter FUCK) {
        this.arrivalTime = arrivalTime;
        this.shoppingList = shoppingListSize;
        this.shoppingSpeed = shoppingSpeed;
        timeBeforeCheckout = shoppingListSize * shoppingSpeed;
        myCustomerNumber = customerNumber; // Based on order in txt file list of customers.
        myArrivalEvent = new ArrivalEvent(this);
        myDoneShoppingEvent = new DoneShoppingEvent(this, FUCK);
        orderOfEvents = eventQ;
        if (shoppingListSize < 10) {
            isElgibleForExpress = true;
        }
        orderOfEvents.offer(myArrivalEvent);
        orderOfEvents.offer(scheduleDoneShoppingEvent());
    }

    public void setTotalTimeInStore(int timeWaitingInLine) {
        totalTimeInStore = timeBeforeCheckout + timeWaitingInLine;
    }

    public double getArrivalTime() {
        return arrivalTime;
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

    public String toString() {
        String returnString = "Arrival time: " + arrivalTime + " shopping list: " + shoppingList + " shopping speed: "
                + shoppingSpeed;
        return returnString;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
