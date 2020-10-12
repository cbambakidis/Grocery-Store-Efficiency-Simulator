public class ExpressLane extends NormalLane {
    private static final long serialVersionUID = 1L;
    int capacity;
    double currentWaitTime = 0;

    public ExpressLane() {
        this.type = "Express";
    }

    public void addCustomerToCheckoutLine(Customer c) {
        System.out.println("Test line executing.");

        this.offer(c);
        System.out.println("Test line executing 2.");

        // Adds the provided customer to this queue, schedules checkout event?
    }

    public void update(double time) {
        if (!this.isEmpty()) {
            System.out.println("Test line executing 3.");

            currentWaitTime = this.peek().shoppingList * checkoutRate + this.peek().arrivalTime;
            System.out.println(currentWaitTime);
        }
        if (currentWaitTime >= time) {
            this.peek().scheduleCheckoutEvent(time);
            System.out.println("Test line executing 5.");

            this.poll();

        }
    }
}
