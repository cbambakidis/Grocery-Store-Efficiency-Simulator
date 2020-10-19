import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;
public class GroceryStoreMain {
    public static void main(String[] args) throws IOException {
        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);

        PriorityQueue<Event> events = new PriorityQueue<Event>(15, new EventComparator());
        CheckoutCenter checkoutLanes = new CheckoutCenter(4, 2, events);
        ArrayList<Customer> customers = readCustomerList("arrivalSimple.txt", events, checkoutLanes);
        // driver loop
        double time = 0;
        while (events.peek() != null) {
            time = events.peek().timeOfOccurence;
            checkoutLanes.update(time);
            Collections.sort(checkoutLanes, new LineComparator());
            events.poll().execute();
        }

        // Calculate avg wait time.
        double averageWaitTime = 0;
        for (Customer N : customers) {
            if (N.getWaitTime() != 0) {
                System.out.println("Customer " + N + " waited " + N.getWaitTime());
            }
            averageWaitTime += N.getWaitTime();
        }

        System.out.println("Average Wait Time: " + averageWaitTime / customers.size());

    }

    /*
     * The wait time is always zero. The line size never exceeds 1 in any lane. Some
     * timesofoccurence are screwed up. Cast each checkout line to an arraylist then
     * manually calculate wait time for each customer based on customers in front?
     */
    public static ArrayList<Customer> readCustomerList(String fileName, Queue<Event> eventList,
            CheckoutCenter checkoutLanes) throws IOException {
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
