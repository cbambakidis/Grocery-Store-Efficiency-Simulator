import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * The checkout center represents all the checkout lanes in the store.
 * Making this class lets me update the status of each lane easier from the main method.
 * This class creates the desired amount of regular and express lanes, and updates them given a time from 
 * the main method.
*/
public class checkoutCenter extends ArrayList<Lane> implements Comparable<Lane> {
    private static final long serialVersionUID = 1L;
    private double localTime = 0;
    PriorityQueue<Event> eventsQ;
    double timeElapsed;

    public checkoutCenter() {
    }

    public checkoutCenter(int numberOfNormalLanes, int numberOfExpressLanes, PriorityQueue<Event> eventQ) {
        eventsQ = eventQ;
        for (int a = 0; a < numberOfExpressLanes; a++) {
            Lane normalCheckoutLane = new Lane(false, a);
            this.add(normalCheckoutLane);
        }
        for (int b = 0; b < numberOfExpressLanes; b++) {
            Lane expressCheckoutLane = new Lane(true, b);
            this.add(expressCheckoutLane);
        }
    }

    /*
     * This lane takes the current event time and uses it to update all the lines.
     * It (hopefully) checks people out and schedules CheckedOutEvents
     */

    public void update(double time) {
        if (time - localTime != 0) {
            timeElapsed = time - localTime + timeElapsed;
        }
        localTime = time;
        Collections.sort(this, new LineComparator());
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).peek() != null) { // check if the checkout lane has someone in line.

                // If the current customer hasn't already had their checkout time calculated,
                // calculate it.
                if (this.get(i).timeToCheckoutCurrentCustomer == 0) {
                    this.get(i).timeToCheckoutCurrentCustomer = this.get(i).peek().getShoppingList()
                            * this.get(i).checkoutRate + this.get(i).paymentTime;
                }

                // Now check to see if the time that has elapsed since the last event
                // is long enough for the current customer to check out.
                if (timeElapsed >= this.get(i).timeToCheckoutCurrentCustomer) {
                    // If enough time has passed to where the customer is done checking out,
                    // schedule its
                    // Checked out event.
                    eventsQ.offer(
                            new CheckedOutEvent(this.get(i).peek(), time + this.get(i).timeToCheckoutCurrentCustomer,
                                    this.get(i).getLaneNumber(), this.get(i).size(), this.get(i)));
                    this.get(i).poll(); // Remove the customer from the queue
                    this.get(i).timeToCheckoutCurrentCustomer = 0;
                }

            }

            else {
                this.get(i).currentWaitTime = 0;
            }

        }
    }

    /*
     * This method automatically sorts the customer into the best lane depending on
     * whether or not they get to use an express lane.
     */

    public void addCustomerToALane(Customer C) {
        // Adds customer to a lane based on if they're elgible for express or not.
        // Checkout center automatically puts the shortest line at front of array, so we
        // can add them to
        // The first (and thus shortest) regular line if they're not elgible for
        // express.
        // Express customers go to teh shortest line regardless.
        if (C.getExpressElgibility() == true) {
            this.get(0).addCustomerToCheckoutLine(C);
        } else {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).type == "Normal") {
                    this.get(i).addCustomerToCheckoutLine(C);
                    break;
                }
            }
        }
    }

    @Override
    public int compareTo(Lane o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
