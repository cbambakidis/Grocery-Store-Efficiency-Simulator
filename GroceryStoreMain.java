
/*
 * @author cbambakidis
 * CS1181, Fall 2020, D.C.E (During Coronavirus Era)
 * Project 3
*/
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;
/*
 * This project is intended to simulate the daily actions of customers in a grocery store,
 * and in a way, model their behavior when it comes to checkout lines. The purpose is to 
 * gain an understanding of how wait time is affected by the amount of regular and express lanes
 * open at a time.
*/
import java.util.InputMismatchException;

public class GroceryStoreMain {
    public static void main(String[] args) throws IOException {

        PriorityQueue<Event> events = new PriorityQueue<Event>(15, new EventComparator());
        CheckoutCenter checkoutLanes = new CheckoutCenter(4, 2);
        Scanner keyScan = new Scanner(System.in);
        /*
         * Output is printed to the out.txt file, not the console!
         */
        boolean hasScanned = false;
        int choice = 0;
        String textFileChoice = new String();
        System.out.print("Enter (1) for arrival simple, (2) for arrival medium, and (3) for arrival big: ");
        try {
            while (!hasScanned) {
                choice = keyScan.nextInt();
                if (choice == 1 || choice == 2 || choice == 3) {
                    hasScanned = true;
                } else {
                    System.out.println("Please enter 1, 2, or 3.");
                }
            }
        } catch (InputMismatchException x) {
            System.out.println("Input Mismatch.");
            System.exit(0);
        }
        keyScan.close();
        switch (choice) {
            case 1:
                textFileChoice = "arrivalSimple.txt";
                break;
            case 2:
                textFileChoice = "arrivalMedium.txt";
                break;
            case 3:
                textFileChoice = "arrivalBig.txt";
                break;
        }
        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        ArrayList<Customer> customers = readCustomerList(textFileChoice, events, checkoutLanes);
        // driver loop
        while (events.peek() != null) {
            events.poll().execute();
            Collections.sort(checkoutLanes, new LineComparator());
            checkoutLanes.update();
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
        double expressLaneWaitTime = 0;
        int counter = 0;
        for (Lane X : checkoutLanes) {
            if (X.isExpress()) {
                expressLaneWaitTime += X.getAvgWaitTime();
                counter++;
            }
        }
        expressLaneWaitTime = expressLaneWaitTime / counter;

        double normalLaneWaitTime = 0;
        counter = 0;
        for (Lane X : checkoutLanes) {
            if (!X.isExpress()) {
                normalLaneWaitTime += X.getAvgWaitTime();
                counter++;
            }
        }
        normalLaneWaitTime = normalLaneWaitTime / counter;

        double averageWaitTimePriorityCustomers = 0;
        double averageWaitTimeForNormalCustomers = 0;
        int numNormalCustomers = 0;
        int numPriorityCustomers = 0;
        // Average wait times for normal and express lanes.
        for (Customer N : customers) {
            if (N.getExpressElgibility()) {
                numPriorityCustomers++;
                averageWaitTimePriorityCustomers += N.getWaitTime();
            } else {
                numNormalCustomers++;
                averageWaitTimeForNormalCustomers += N.getWaitTime();
            }
        }

        // Average line length.
        double x = 0;
        for (double d : checkoutLanes.getAvgLength()) {
            x += d;
        }

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("SIMULATION WITH (" + checkoutLanes.getNumNormalLanes() + ") NORMAL LANES AND ("
                + checkoutLanes.getNumExpressLanes() + ") EXPRESS LANES - STATS:");
        System.out.println("TOTAL CUSTOMERS PROCESSED: " + customers.size() + " (" + numPriorityCustomers
                + " with under 12 items, " + numNormalCustomers + " with over 12 items).");
        System.out.printf("AVERAGE WAIT TIME PER CUSTOMER: %.3f minutes", (averageWaitTime / customers.size()));
        System.out.println();
        System.out.printf("WAIT TIME FOR CUSTOMERS WITH OVER 12 ITEMS: %.3f minutes",
                averageWaitTimeForNormalCustomers / numNormalCustomers);
        System.out.println();
        System.out.printf("WAIT TIME FOR CUSTOMERS WITH 12 OR LESS ITEMS: %.3f minutes",
                averageWaitTimePriorityCustomers / numPriorityCustomers);
        System.out.println();
        System.out.printf("AVERAGE WAIT TIME FOR NORMAL LANES: %.3f minutes", normalLaneWaitTime);
        System.out.println();
        System.out.printf("AVERAGE WAIT TIME FOR EXPRESS LANES: %.3f minutes", expressLaneWaitTime);
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
        System.out.println("------------------------------SIMULATION COMPLETE--------------------------------");
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println(
                "Simulated completed. Please view output in out.txt file. Stats will be at the bottom of the file.");

    }

    /*
     * This method reads in all the customers from the desired text file. Arrival
     * events are scheduled upon customer construction. The method returns an
     * arraylist of customer objects from the text file.
     * 
     * @fileName name of the file to read customers from.
     * 
     * @eventList the main list of events that will happen. Each customer has access
     * to this list.
     * 
     * @checkoutLanes is the checkout center with all the lanes for once they're
     * done checking out.
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
