public class ExpressLane extends NormalLane {
    private static final long serialVersionUID = 1L;
    int capacity;
    double currentWaitTime = 0;
    double checkoutRate = .1;
    int paymentTime = 1;
    public ExpressLane() {
        this.type = "Express";
    }

    public void addCustomerToCheckoutLine(Customer c) {
        this.offer(c);
        // Adds the provided customer to this queue, schedules checkout event?
    }
}
