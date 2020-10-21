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
        ArrayList<Customer> customers = readCustomerList("arrivalMedium.txt", events, checkoutLanes);
        // driver loop
        double time = 0;
        while (events.peek() != null) {
            time = events.peek().getTimeOfOccurence();
            checkoutLanes.update(time);
            Collections.sort(checkoutLanes, new LineComparator());
            events.poll().execute();
        }

        // Calculate avg wait time.
        double averageWaitTime = 0;
        for (Customer N : customers) {
            if (N.getWaitTime() != 0) {
                System.out.printf("Customer " + N + " waited %.2f", N.getWaitTime());
                System.out.println("");
            }
            averageWaitTime += N.getWaitTime();
        }
        double averageWaitTimeP = 0;
        double avgForNormal = 0;
        int numNormal = 0;
        int numP = 0;
        for (Customer N : customers) {
            if(N.getExpressElgibility()){
                numP++;
            averageWaitTimeP += N.getWaitTime();}
            else{
                numNormal++;
                avgForNormal += N.getWaitTime();
            }
        }
        System.out.printf("Average Wait Time: %.3f", (averageWaitTime / customers.size()/2));
        System.out.println();
        System.out.println(avgForNormal/numNormal);
        System.out.println(averageWaitTimeP / numP);
    }

    /*
     * 
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
