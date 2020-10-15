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
        PrintStream fileOut = new PrintStream("./out.txt"); //This idea via Jerry Zhou on dev2qa
        System.setOut(fileOut);
        
        PriorityQueue<Event> events = new PriorityQueue<Event>(15, new EventComparator());
        checkoutCenter checkoutLanes = new checkoutCenter(4, 2, events);
        ArrayList<Customer> customers = readCustomerList("arrivalMedium.txt", events, checkoutLanes);
        System.out.println(customers);
        //Collections.sort(customers, new timeComparator());
        double time = 0;
        while (events.peek() != null) {
            time = events.peek().timeOfOccurence;
            Collections.sort(checkoutLanes, new LineComparator());
            events.poll().execute();
            checkoutLanes.update(time);
        }

    }

    /*
     * To add: make output specified to Dr.Cheathams requirements. Add tracker for how long customers had to wait in line.
     * Add output when customers are sorted into line - lane number, number of customers.
     * 
     */
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
