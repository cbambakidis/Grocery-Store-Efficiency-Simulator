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
    private ArrayList<Double> avgSizes = new ArrayList<>();
    private int longestLineSize = 0;
    private int numNormalLanes;
    private int numExpressLanes;
    public CheckoutCenter(int numberOfNormalLanes, int numberOfExpressLanes, PriorityQueue<Event> eventQ) {
        int lastNum = 0;
        for (int a = 0; a < numberOfNormalLanes; a++) {
            Lane normalCheckoutLane = new Lane(false, a);
            this.add(normalCheckoutLane);
            lastNum++;
            numNormalLanes++;
        }
        for (int b = 0; b < numberOfExpressLanes; b++) {
            Lane expressCheckoutLane = new Lane(true, b + lastNum);
            this.add(expressCheckoutLane);
            numExpressLanes++;
        }
    }

    // Problem: account for customer being halfway through checkout when new
    // customer is added to line.
    /*
     * This lane takes the current event time and uses it to update all the lines.
     * It (hopefully) checks people out and schedules CheckedOutEvents
     */

    public void update(double time) {
        Collections.sort(this, new LineComparator());
        // Get average lane size stats.
        double avgLaneSize = 0;
        for (Lane X : this) {
            avgLaneSize += X.size();

        }
        avgLaneSize = avgLaneSize / this.size();
        avgSizes.add(avgLaneSize);

        // Get longest line size
        for (Lane X : this) {
            if (X.size() >= longestLineSize) {
                longestLineSize = X.size();
            }
        }
        


    }

    public int getLongestLineSize() {
        return longestLineSize;
    }

    /*
     * This method automatically sorts the customer into the best lane depending on
     * whether or not they get to use an express lane. Wait time is calculated
     * within lane class.
     */
    public void addCustomerToALane(Customer C) {
        // Check lane equality
        boolean areAllEqual = false;
        int size = this.get(0).size();
        for (Lane L : this) {
            if (L.size() < size) {
                size = L.size();
            }
        }
        for (Lane L : this) {
            if (L.size() == size) {
                areAllEqual = true;
            } else {
                areAllEqual = false;
                break;
            }
        }

        // Inelgible for express, unequal length lines. First normal line.
        if (!C.getExpressElgibility() && !areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress()) {
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + " (" + N.size() + ")");
                    N.addCustomerToCheckoutLine(C);
                    return;
                }
            }
        }
        // Elgible for express, unequal length lines. Shortest line regardless.
        if (C.getExpressElgibility() && !areAllEqual) {
            System.out.println(
                    "Less than 12, chose lane " + this.get(0).getLaneNumber() + " (" + this.get(0).size() + ")");
            this.get(0).addCustomerToCheckoutLine(C);
            return;
        }

        // Inelgible for express, equal length lines. Shortest normal line.
        if (!C.getExpressElgibility() && areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress()) {
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + " (" + N.size() + ")");
                    N.addCustomerToCheckoutLine(C);
                    return;
                }
            }
        }
        // Elgible for express, equal length lines. First express line.
        if (C.getExpressElgibility() && areAllEqual) {
            for (Lane N : this) {
                if (N.isExpress()) {
                    System.out.println("Less than 12, chose lane " + N.getLaneNumber() + " (" + N.size() + ")");
                    N.addCustomerToCheckoutLine(C);
                    return;
                }
            }
        }

    }

    public int getNumNormalLanes(){
        return numNormalLanes;
    }

    public int getNumExpressLanes(){
        return numExpressLanes;
    }

    public ArrayList<Double> getAvgLength() {
        return avgSizes;
    }

    @Override
    public int compareTo(Lane o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
