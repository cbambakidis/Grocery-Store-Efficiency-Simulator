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
        //

        checkoutCenter FUCK = new checkoutCenter();
        PriorityQueue<Event> events = new PriorityQueue<Event>(10, new EventComparator());
        ArrayList<Customer> customers = readCustomerList("arrivalSimple.txt", events, FUCK);
        Collections.sort(customers, new timeComparator());

        while(events.peek() != null){
        double time = events.peek().timeOfOccurence;
        Collections.sort(FUCK, new LineComparator());
        events.peek().execute();
        events.poll();
        FUCK.update(time);
        System.out.println("Time: " + time);
        }

        double time = 0;


    }

    public static ArrayList<Customer> readCustomerList(String fileName, Queue<Event> x,  checkoutCenter FUCK)
            throws IOException {
        FileReader reader = new FileReader(fileName);
        Scanner fileScanner = new Scanner(reader);
        ArrayList<Customer> CustomerList = new ArrayList<>();
        int count = 0;
        while (fileScanner.hasNext()) {
            double arrival = fileScanner.nextDouble();
            int itemsList = fileScanner.nextInt();
            double shoppingSpeed = fileScanner.nextDouble();
            Customer newCusty = new Customer(arrival, itemsList, shoppingSpeed, count, x, FUCK);
            CustomerList.add(newCusty);
            count++;
        }
        reader.close();
        fileScanner.close();
        return CustomerList;
    }

}
