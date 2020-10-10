public class ExpressLane extends NormalLane {
    private static final long serialVersionUID = 1L;
    int capacity;
    double currentWaitTime = 0;
    public ExpressLane(){
        this.type = "Express";
    }
    
    public void addCustomerToCheckoutLine(Customer c){
        this.offer(c);
        //Adds the provided customer to this queue, schedules checkout event?
    }
    public void update(double time){
        if(!this.isEmpty()){
        currentWaitTime = this.peek().shoppingList*checkoutRate+this.peek().arrivalTime;
    }
        if(currentWaitTime >= time){
            this.peek().scheduleCheckoutEvent(time);
            this.poll();
            
        }
}
}
