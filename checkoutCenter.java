import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * The checkout center represents all the checkout lanes in the store.
 * Making this class lets me update the status of each lane easier from the main method.
 * This class creates the desired amount of regular and express lanes, and updates them given a time from 
 * the main method.
*/
public class CheckoutCenter extends ArrayList<Lane> implements Comparable<Lane> {
    private static final long serialVersionUID = 1L;
    double timeElapsed;

    public CheckoutCenter() {
    }

    public CheckoutCenter(int numberOfNormalLanes, int numberOfExpressLanes, PriorityQueue<Event> eventQ) {
        for (int a = 0; a < numberOfNormalLanes; a++) {
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
        Collections.sort(this, new LineComparator());
    }

    /*
     * This method automatically sorts the customer into the best lane depending on
     * whether or not they get to use an express lane. Wait time is calculated
     * within lane class.
     */
    public void addCustomerToALane(Customer C) {

        boolean areAllEqual = false;
        int size = this.get(0).size();
        for (Lane N : this) {
            if (N.size() < size) {
                size = N.size();
            }
        }

        for (Lane N : this) {
            if (N.size() == size) {
                areAllEqual = true;
            } else {
                areAllEqual = false;
                break;
            }
        }

        // Inelgible for express, unequal length lines. First normal line.
        if (!C.getExpressElgibility() && !areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress) {
                    N.addCustomerToCheckoutLine(C);
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + N.type);
                    return;
                }
            }
        }
        // Elgible for express, unequal length lines. Shortest line regardless.
        if (C.getExpressElgibility() && !areAllEqual) {
            this.get(0).addCustomerToCheckoutLine(C);
            System.out.println("Less than 12, chose lane " + this.get(0).getLaneNumber() + this.get(0).type);
            return;
        }

        // Inelgible for express, equal length lines. Shortest normal line.
        if (!C.getExpressElgibility() && areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress) {
                    N.addCustomerToCheckoutLine(C);
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + N.type);
                    return;
                }
            }
        }
        // Elgible for express, equal length lines. First express line.
        if (C.getExpressElgibility() && areAllEqual) {
            for (Lane N : this) {
                if (N.isExpress) {
                    N.addCustomerToCheckoutLine(C);
                    System.out.println("Less than 12, chose lane " + N.getLaneNumber() + N.type);
                    return;
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
