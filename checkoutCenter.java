import java.util.ArrayList;
import java.util.Collections;

public class checkoutCenter extends ArrayList<NormalLane> implements Comparable<NormalLane> {
    private static final long serialVersionUID = 1L;

    public checkoutCenter() {
        ExpressLane lane1 = new ExpressLane();
        NormalLane lane2 = new NormalLane();
        ExpressLane lane3 = new ExpressLane();
        NormalLane lane4 = new NormalLane();
        ExpressLane lane5 = new ExpressLane();
        NormalLane lane6 = new NormalLane();
        this.add(lane1);
        this.add(lane2);
        this.add(lane3);
        this.add(lane4); // MAKE TWO CHECKOUT CENTERS, ONE FOR EXPRESS AND ONE FOR NORMAL
        this.add(lane5);
        this.add(lane6);
    }

    public checkoutCenter(int numberOfNormalLanes, int numberOfExpressLanes) {
        for (int a = 0; a < numberOfExpressLanes; a++) {
            NormalLane normalCheckoutLane = new NormalLane();
            this.add(normalCheckoutLane);
        }
        for (int b = 0; b < numberOfExpressLanes; b++) {
            ExpressLane expressCheckoutLane = new ExpressLane();
            this.add(expressCheckoutLane);
        }
    }

    public void update(double time) {
        
        double timeElapsed;
        Collections.sort(this, new LineComparator());
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).peek() != null) {
                this.get(i).currentWaitTime = (this.get(i).peek().getShoppingList() * this.get(i).checkoutRate
                        + this.get(i).paymentTime + time);
                this.get(i).peek().scheduleCheckoutEvent(this.get(i).currentWaitTime + this.get(i).peek().getArrivalTime());
                this.get(i).poll();
            //Keep customers in their lines until timeelapsed meets time it takes for them to check out, then schedule checkout event for this time, then move on to next?
            }

            else {
                this.get(i).currentWaitTime = 0;
            }

        }
    }

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
    public int compareTo(NormalLane o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
