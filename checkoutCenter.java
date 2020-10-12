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

    }

    public void update(double time) {
        Collections.sort(this, new LineComparator());
        for (int i = 0; i < this.size(); i++) {

            if (this.get(i).peek() != null) {
                this.get(i).currentWaitTime = this.get(i).peek().shoppingList * this.get(i).checkoutRate
                        + this.get(i).paymentTime;
            }

            else {
                this.get(i).currentWaitTime = 0;
            }

            if (this.get(i).currentWaitTime >= time) {
                this.get(i).peek().scheduleCheckoutEvent(time + this.get(i).currentWaitTime);
                this.get(i).poll();
            }
        }
    }

    public void addCustomerToALane(Customer C) {
        if (C.isElgibleForExpress) {
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
