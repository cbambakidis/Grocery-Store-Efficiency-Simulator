import java.util.ArrayList;
public class DoneShoppingEvent extends Event{

    private double doneShoppingTime;
    double timeOfOccurence;
    Customer n;
    boolean isElgibleForExpress;
    ArrayList<NormalLane> options;
    public DoneShoppingEvent(Customer x, ArrayList<NormalLane> options){
        doneShoppingTime = x.timeBeforeCheckout;
        super.timeOfOccurence = x.timeBeforeCheckout + x.arrivalTime;
        this.timeOfOccurence = x.timeBeforeCheckout + x.arrivalTime;
        this.options = options;
        n = x;
        if(x.shoppingList < 10){
            isElgibleForExpress = false;
        }
    }    
    public void execute(){
        System.out.println(timeOfOccurence + ": Finished shopping customer " + n.myCustomerNumber);
        if(this.isElgibleForExpress){
            options.get(0).addCustomerToCheckoutLine(n);
            //code to sort through queues and sort self into one with least people.
        }
        else{
            for(int i=0; i<options.size(); i++){
                if (options.get(i).type == "Normal"){
                    options.get(i).addCustomerToCheckoutLine(n);
                    break;
                }
            }
        }
    }



}

//12 queues that represent checkout lines?
//.update() class that updates all queues in the class.
//ArrivalEvent
//ShoppingEvent
//CheckoutEvent
//Add to checkout queue based on # of items and lanes open.
//
    //each event will be derived from the customer objects, then each event object will be added to a list in main and used to update time.
    //event type: arrival, endshopping (setcehckoutlane), endCheckout, 
    //this class will define the checkout times and stuff it takes per customer.
    //type: exepress or regular.
    //Methods for each event.
    //prints when customer is added or removed (leaves store/enters line when finished shopping.)
