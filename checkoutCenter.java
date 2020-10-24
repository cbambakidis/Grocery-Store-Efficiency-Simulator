import java.util.ArrayList;
import java.util.Collections;

/*
 * The checkout center represents all the checkout lanes in the store.
 * This class creates the desired amount of regular and express lanes, and updates them given a time from 
 * the main method.
*/

public class CheckoutCenter extends ArrayList<Lane> implements Comparable<Lane> {
    private static final long serialVersionUID = 1L;
    private ArrayList<Double> avgSizes = new ArrayList<>();
    private int longestLineSize = 0;
    private int numNormalLanes;
    private int numExpressLanes;

    /*
     * Makes a new checkout center, with desired amount of normal and express lanes.
     * Also gives them each a lane number.
     * 
     * @numberOfNormalLanes is the number of desired normal checkout lanes for the
     * simulation.
     * 
     * @numberOfExpressLanes is the number of desired express lanes.
     */
    public CheckoutCenter(int numberOfNormalLanes, int numberOfExpressLanes) {
        int lastNum = 0;
        numNormalLanes = numberOfNormalLanes;
        numExpressLanes = numberOfExpressLanes;
        for (int a = 0; a < numberOfNormalLanes; a++) {
            Lane normalCheckoutLane = new Lane(false, a);
            this.add(normalCheckoutLane);
            lastNum++;
        }
        for (int b = 0; b < numberOfExpressLanes; b++) {
            Lane expressCheckoutLane = new Lane(true, b + lastNum);
            this.add(expressCheckoutLane);
        }
    }

    /*
     * This method is called from the main method, and simply gathers statistics on
     * lane size, as well as sorts each lane, each time the sim clock is advanced.
     */

    public void update() {
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

        // Customer is inelgible for express, and unequal length lines. First normal
        // line.
        if (!C.getExpressElgibility() && !areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress()) {
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + " (" + N.size() + ")");
                    N.addCustomerToCheckoutLine(C);
                    return;
                }
            }
        }
        // Customer is elgible for express, and unequal length lines. Shortest line
        // regardless of type.
        if (C.getExpressElgibility() && !areAllEqual) {
            System.out.println(
                    "Less than 12, chose lane " + this.get(0).getLaneNumber() + " (" + this.get(0).size() + ")");
            this.get(0).addCustomerToCheckoutLine(C);
            return;
        }

        // Customer is inelgible for express, and lines are equal length. Shortest
        // normal line.
        if (!C.getExpressElgibility() && areAllEqual) {
            for (Lane N : this) {
                if (!N.isExpress()) {
                    System.out.println("More than 12, chose lane " + N.getLaneNumber() + " (" + N.size() + ")");
                    N.addCustomerToCheckoutLine(C);
                    return;
                }
            }
        }
        // Customer is elgible for express, and lines are of equal length. First express
        // line.
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

    //Various getters, mostly for statistics.
    public int getNumNormalLanes() {
        return numNormalLanes;
    }

    public int getNumExpressLanes() {
        return numExpressLanes;
    }

    public ArrayList<Double> getAvgLength() {
        return avgSizes;
    }

    public int getLongestLineSize() {
        return longestLineSize;
    }

    @Override
    public int compareTo(Lane o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
