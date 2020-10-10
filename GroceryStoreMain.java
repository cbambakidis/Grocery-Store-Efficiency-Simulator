import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;

public class GroceryStoreMain {
    public static void main(String[] args) throws IOException {
        // Arraylist of objects -> Priorityqueue based on time in.
        // will update queues/checkout ques based on in gmae time derived from next
        // customer object.
        ExpressLane lane1 = new ExpressLane();
        NormalLane lane2 = new NormalLane();
        ExpressLane lane3 = new ExpressLane();
        NormalLane lane4 = new NormalLane();
        ExpressLane lane5 = new ExpressLane();
        NormalLane lane6 = new NormalLane();
        ArrayList<NormalLane> checkoutCenter = new ArrayList<NormalLane>();
        Collections.sort(checkoutCenter, new LineComparator()); // inside each step.
        checkoutCenter.add(lane1);
        checkoutCenter.add(lane2);
        checkoutCenter.add(lane3);
        checkoutCenter.add(lane4);
        checkoutCenter.add(lane5);
        checkoutCenter.add(lane6);
        PriorityQueue<Event> events = new PriorityQueue<Event>(500, new EventComparator());
        ArrayList<Customer> customers = readCustomerList("C:/Users/Costa Bambakidis/Downloads/Project 3 data/Project 3 data/arrivalSimple.txt", events, checkoutCenter);
        Collections.sort(customers, new timeComparator());
        double time = 0;
        while (events.peek() != null) {
            for (int i = 0; i < customers.size(); i++) {
                time = customers.get(i).getArrivalTime();
                for (int g = 0; g < checkoutCenter.size(); g++) {
                    Collections.sort(checkoutCenter, new LineComparator()); // inside each step.
                    checkoutCenter.get(g).update(time);
                    if (events.peek() != null) {
                        events.poll().execute();
                    } else
                        return;
                }
            }
        }

        // CheckedOut event is scheduled/added to eventlist based on time returned after
        // adding customer to queue.
        //

    }

    public static ArrayList<Customer> readCustomerList(String fileName, Queue<Event> x, ArrayList<NormalLane> d)
            throws IOException {
        FileReader reader = new FileReader(fileName);
        Scanner fileScanner = new Scanner(reader);
        ArrayList<Customer> CustomerList = new ArrayList<>();
        int count = 0;
        while (fileScanner.hasNext()) {
            double arrival = fileScanner.nextDouble();
            int itemsList = fileScanner.nextInt();
            double shoppingSpeed = fileScanner.nextDouble();
            Customer newCusty = new Customer(arrival, itemsList, shoppingSpeed, count, x, d);
            CustomerList.add(newCusty);
            count++;
        }
        reader.close();
        fileScanner.close();
        return CustomerList;
    }

}
