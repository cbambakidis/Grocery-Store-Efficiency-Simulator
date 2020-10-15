import java.util.ArrayList;
import java.util.Collections;

/*
 * The checkout center represents all the checkout lanes in the store.
 * Making this class lets me update the status of each lane easier from the main method.
 * This class creates the desired amount of regular and express lanes, and updates them given a time from 
 * the main method.
*/
public class checkoutCenter extends ArrayList<Lane> implements Comparable<Lane> {
    private static final long serialVersionUID = 1L;

    public checkoutCenter() {
    }

    public checkoutCenter(int numberOfNormalLanes, int numberOfExpressLanes) {

        for (int a = 0; a < numberOfExpressLanes; a++) {
            Lane normalCheckoutLane = new Lane();
            this.add(normalCheckoutLane);
        }
        for (int b = 0; b < numberOfExpressLanes; b++) {
            Lane expressCheckoutLane = new Lane(true);
            this.add(expressCheckoutLane);
        }
    }

    /*
     * This lane takes the current event time and uses it to update all the lines.
     * It (hopefully) checks people out and schedules CheckedOutEvents
     */

    public void update(double time) {

        Collections.sort(this, new LineComparator());
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).peek() != null) {
                this.get(i).currentWaitTime = (this.get(i).peek().getShoppingList() * this.get(i).checkoutRate
                        + this.get(i).paymentTime + time);
                this.get(i).peek()
                        .scheduleCheckoutEvent(this.get(i).currentWaitTime + this.get(i).peek().getArrivalTime());
                this.get(i).poll();
                // Keep customers in their lines until timeelapsed meets time it takes for them
                // to check out,
                // then schedule checkout event for this time, then move on to next?
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
        // Print stats on which lane it's being added to, and wait time.
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
