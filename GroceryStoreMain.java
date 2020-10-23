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
        double timeElapsed = 0;
        double currentTime = 0;
        while (events.peek() != null) {
            time = events.peek().getTimeOfOccurence();
            events.poll().execute();
            timeElapsed = time - currentTime;
            currentTime = time;
            Collections.sort(checkoutLanes, new LineComparator());
            checkoutLanes.update(timeElapsed);
        }

        // Calculate avg wait times.
        double averageWaitTime = 0;
        for (Customer N : customers) {
            if (N.getWaitTime() != 0) {
                System.out.printf("Customer " + N + " waited %.2f", N.getWaitTime());
                System.out.println("");
            }
            averageWaitTime += N.getWaitTime();
        }
        double expressWaitTime = 0;
        int counter = 0;
        for(Lane X : checkoutLanes){
            if(X.isExpress()){
                expressWaitTime+= X.getAvgWaitTime();
                counter++;
            }
        }
        expressWaitTime = expressWaitTime/counter;

        double normalWaitTime = 0;
        counter = 0;
        for(Lane X : checkoutLanes){
            if(!X.isExpress()){
                normalWaitTime+= X.getAvgWaitTime();
                counter++;
            }
        }
        normalWaitTime = normalWaitTime/counter;

        expressWaitTime = expressWaitTime/counter;
        double averageWaitTimeP = 0;
        double avgForNormal = 0;
        int numNormal = 0;
        int numP = 0;
        // Average wait times for normal and express lanes.
        for (Customer N : customers) {
            if (N.getExpressElgibility()) {
                numP++;
                averageWaitTimeP += N.getWaitTime();
            } else {
                numNormal++;
                avgForNormal += N.getWaitTime();
            }
        }

        // Average line length.
        double x = 0;
        for (double d : checkoutLanes.getAvgLength()) {
            x += d;
        }
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("SIMULATION WITH (" + checkoutLanes.getNumNormalLanes() + ") NORMAL LANES AND (" + checkoutLanes.getNumExpressLanes() + ") EXPRESS LANES COMPLETE. STATS:");
        System.out.println("TOTAL CUSTOMERS PROCESSED: " + customers.size() + " (" + numP + " with under 12 items, " + numNormal + " with over 12 items).");
        System.out.printf("AVERAGE WAIT TIME PER CUSTOMER: %.3f minutes", (averageWaitTime / customers.size()));
        System.out.println();
        System.out.printf("WAIT TIME FOR CUSTOMERS WITH OVER 12 ITEMS: %.3f minutes", avgForNormal / numNormal);
        System.out.println();
        System.out.printf("WAIT TIME FOR CUSTOMERS WITH 12 OR LESS ITEMS: %.3f minutes", averageWaitTimeP / numP);
        System.out.println();
        System.out.printf("AVERAGE WAIT TIME FOR NORMAL LANES: %.3f minutes", normalWaitTime);
        System.out.println();
        System.out.printf("AVERAGE WAIT TIME FOR EXPRESS LANES: %.3f minutes", expressWaitTime);
        System.out.println();
        System.out.printf("AVERAGE LINE LENGTH: %.3f customers", (x / checkoutLanes.getAvgLength().size()));
        System.out.println();
        System.out.println("LONGEST LINE LENGTH: " + checkoutLanes.getLongestLineSize());
        System.out.println("STATS BY LANE: ");
        System.out.println("\tLane Number\t\tIs Express\t\t# customers\t\tAverage wait time");
        Collections.sort(checkoutLanes);
        for (Lane N : checkoutLanes) {
            System.out.printf("\t\t" + N.getLaneNumber() + "\t\t\t\t" + N.isExpress() + "\t\t\t" + N.getTotalCustomers()
                    + "\t\t\t\t %.2f", N.getAvgWaitTime());
            System.out.println();
        }
        System.out.println("-------------------------------PROGRAM TERMINATED---------------------------------");
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
