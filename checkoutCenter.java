import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/*
 * The checkout center represents all the checkout lanes in the store.
 * Making this class lets me update the status of each lane easier from the main method.
 * This class creates the desired amount of regular and express lanes, and updates them given a time from 
 * the main method.
*/
public class CheckoutCenter extends ArrayList<Lane> implements Comparable<Lane> {
    private static final long serialVersionUID = 1L;
    private double localTime = 0;
    PriorityQueue<Event> eventsQ;
    double timeElapsed;

    public CheckoutCenter() {
    }

    public CheckoutCenter(int numberOfNormalLanes, int numberOfExpressLanes, PriorityQueue<Event> eventQ) {
        eventsQ = eventQ;
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
        if (time - localTime != 0) {
            timeElapsed = time - localTime + timeElapsed;
        }
        localTime = time; // Calulates time elapased between events.

        Collections.sort(this, new LineComparator());
    //     for (Lane N : this) {
    //         if (N.peek() != null) { // check if the checkout lane has someone in line.

    //             //PROBLEM: CUSTOMER IS BEING REMOVED FROM LANE BEFORE THEYVE EVEN FINISHED CHECKING OUT.
    //             //Creating checkout events until customer is removed. PROBLEM
    //             /*
    //              * Customers are being removed from the lane, before their checkout time of occurence.
    //              * They are being removed only after the time it takes for them to check out,
    //              * And they are out of the line in however long it takes them to
    //              * Maybe remove and add customers b
    //             */
                

    //             //Calculate time it will take to check out customer at front of line.
    //             //Check if enough time has passed to where the customer is checked out.
    //             //If it is, schedule checkout event.
    //             N.timeToCheckoutCurrentCustomer = N.peek().getShoppingList() * N.checkoutRate + N.paymentTime;
    //             if (timeElapsed >= N.timeToCheckoutCurrentCustomer) {
    //                 eventsQ.offer(new CheckedOutEvent(N.peek(), N));                
    //                 N.timeToCheckoutCurrentCustomer = 0;
    //             }
    //         }
    //         else {
    //             N.currentWaitTime = 0;
    //         }

    //     }
    // }

    /*
     * This method automatically sorts the customer into the best lane depending on
     * whether or not they get to use an express lane. Wait time is calculated within lane class.
     */
    public void addCustomerToALane(Customer C) {

        //Bad way to do it. Find different way to check if different size
        //Find lane with smallest size, then see if other lanes have a different size.
        
        boolean areAllEqual = false;
        int size = this.get(0).size();
        for(Lane N : this){
            if(N.size() < size){
                size = N.size();
            }
        }
        for(Lane N : this){
            if (N.size() == size){
                areAllEqual = true;
            }
            else {
                areAllEqual = false;
                break;
            }
        }

        // If they are all equal, add customer to random lane. Express customers can
        // only use express lanes.

        if (areAllEqual) {
            boolean hadBeenAdded = false;
            while (!hadBeenAdded) {
                int randomLaneNumber = ThreadLocalRandom.current().nextInt(0, this.size());
                if (!this.get(randomLaneNumber).isExpress && !C.getExpressElgibility()) {
                    this.get(randomLaneNumber).addCustomerToCheckoutLine(C);
                    if (C.getExpressElgibility()) {
                        System.out.println("Less than 12, chose lane " + this.get(randomLaneNumber).getLaneNumber());
                    } else {
                        System.out.println("More than 12, chose lane " + this.get(randomLaneNumber).getLaneNumber());
                    }
                    hadBeenAdded = true;
                }
                if (this.get(randomLaneNumber).isExpress && C.getExpressElgibility()) {
                    this.get(randomLaneNumber).addCustomerToCheckoutLine(C);
                    System.out.println("Less than 12, chose lane " + this.get(randomLaneNumber).getLaneNumber());
                    hadBeenAdded = true;
                }
            }
        }
        // If they're of different lengths, add customer to shortest lane. The
        // checkoutcenter is made automatically to
        // Sort by shortest lane. Only express customers can use express lane.
        else {
            if (C.getExpressElgibility()) {
                this.get(0).addCustomerToCheckoutLine(C);
                System.out.println("Less than 12, chose lane " + this.get(0).getLaneNumber());
            } else {
                for (Lane x : this) {
                    if (x.isExpress) {
                        x.addCustomerToCheckoutLine(C);
                        System.out.println("More than 12, chose lane " + x.getLaneNumber());
                        break;
                    }
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
