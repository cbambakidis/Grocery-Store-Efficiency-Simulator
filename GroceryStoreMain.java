import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;

public class GroceryStoreMain {
    public static void main(String[] args) throws IOException {

        checkoutCenter checkoutLanes = new checkoutCenter();
        PriorityQueue<Event> events = new PriorityQueue<Event>(10, new EventComparator());
        ArrayList<Customer> customers = readCustomerList("arrivalSimple.txt", events, checkoutLanes);
        Collections.sort(customers, new timeComparator());

        while (events.peek() != null) {
            double time = events.peek().timeOfOccurence;
            Collections.sort(checkoutLanes, new LineComparator());
            events.peek().execute();
            events.poll();
            checkoutLanes.update(time);
        }

    }

    public static ArrayList<Customer> readCustomerList(String fileName, Queue<Event> eventList,
            checkoutCenter checkoutLanes) throws IOException {
        FileReader reader = new FileReader(fileName);
        Scanner fileScanner = new Scanner(reader);
        ArrayList<Customer> CustomerList = new ArrayList<>();
        int count = 0;
        while (fileScanner.hasNext()) {
            double arrival = fileScanner.nextDouble();
            int itemsList = fileScanner.nextInt();
            double shoppingSpeed = fileScanner.nextDouble();
            Customer newCustomer = new Customer(arrival, itemsList, shoppingSpeed, count, eventList, checkoutLanes);
            CustomerList.add(newCustomer);
            count++;
        }
        reader.close();
        fileScanner.close();
        return CustomerList;
    }

}
