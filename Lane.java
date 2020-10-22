import java.util.PriorityQueue;
/*
 * The lane object represents a line of people waiting to check out.
 * It has a method to add a customer to itself, as well as data to calculate how long it will take
 * for each customer to check out.
*/

public class Lane extends PriorityQueue<Customer> implements Comparable<Lane> {

    private static final long serialVersionUID = 1L;
    private int laneNumber;
    private double timeToCheckout;
    private double checkoutRate;
    private double paymentTime;
    private boolean isExpress;
    private int numberCustomersTotal = 0;
     Customer customerCurrentlyCheckingOut;
    public Lane(){

    }
    public Lane(boolean isExpress, int laneNumber) {
        if (isExpress) {
            this.isExpress = true;
            this.checkoutRate = .1;
            this.paymentTime = 1;
        } else {
            this.isExpress = false;
            this.checkoutRate = .05;
            this.paymentTime = 2;
        }
        this.laneNumber = laneNumber;
    }
    //PIPE TIME ELAPSED STRAIGHT THROUGH ONCE A CUSTOMER IS BEING ADDED TO CHECKOUT.

    public void addCustomerToCheckoutLine(Customer c) {
        //If there's already someone checking out, they have to wait
        //For them to check out. If there's multiple people in line, it will be 
        //Their checkout times plus however far along guy in the lead is.
        if(this.peek() == null){
            c.setWaitTime(0);
        }
        else if(this.size() == 1) {
        this.timeToCheckout = -(c.getDoneShoppingTime() - this.peek().getDoneShoppingTime() - (this.peek().getShoppingList() * this.checkoutRate + this.paymentTime));
        System.out.println(this.peek().getDoneShoppingTime() + "::" + c.getDoneShoppingTime());
        c.setWaitTime(this.timeToCheckout);   
        }
        
        else if(this.size() > 1){
            this.timeToCheckout = (this.peek().getShoppingList() * this.checkoutRate + this.paymentTime) - c.getDoneShoppingTime() - this.peek().getDoneShoppingTime();
            c.setWaitTime(this.timeToCheckout + calculateWaitTime());        
        }
        numberCustomersTotal++;
        c.setPeopleInFront(this.size());
        this.offer(c);
        c.addCheckedOutEvent(this);
        
    }

//     public void update(double timeElapsed){
//         if(this.peek() != null){
//         this.timeToCheckout = this.peek().getShoppingList() * this.checkoutRate + this.paymentTime - timeElapsed;
//     }
// }

    public Customer currentCustomer(){
        return this.customerCurrentlyCheckingOut;
    }
    
    public double calculateWaitTime(){
        double totalWaitTime = 0;
                    for(Customer N : this){
                        if(N != this.peek()){
                    totalWaitTime += (N.getShoppingList()
                    * this.checkoutRate + this.paymentTime);
                    }
                }
                    return totalWaitTime;
    }
    public int getTotalCustomers(){
        return numberCustomersTotal;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public boolean isExpress(){
        return isExpress;
    }

    public double getCheckoutRate(){
        return checkoutRate;
    }

    public double getPaymentTime(){
        return paymentTime;
    }

    @Override
    public int compareTo(Lane o) {
        if(this.getLaneNumber() > o.laneNumber){
            return 1;
        }
        if(o.getLaneNumber() > this.laneNumber){
            return -1;
        }
        else return 0;
    }
}
