import exceptions.NameException;
import park.Park;
import park.products.tickets.Ticket;
import park.products.tickets.TicketType;
import park.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static Park park = new Park("Disneyland");

    public static void main(String[] args) throws Exception {
        //TODO create menu methods for each case
        mainMenu();
    }

    public static void mainMenu() throws Exception {
        String[] options = {"Buy ticket", "Find User", "Go shopping", "Watch a movie", "Ride some attraction", "Exit"};
        printOptions(options);

        switch (readCommand()) {
            case "1": // ticket menu
                // TODO call method..
                ticketMenu();
                break;
            case "2":
                // TODO call method..
                break;
            case "3":
                // TODO call method..
                break;
            case "4":
                break;
            case "5":
                break;
            case "6": // exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        mainMenu();
    }

    public static void ticketMenu() throws Exception {
        String[] options = {"SingleTicket", "GroupTicket", "Exit"};
        printOptions(options);
        List<User> users = new ArrayList<>();
        switch (readCommand()) {
            case "1": // TODO create person and ticket
                System.out.println("Create person + ticket");
                users.addAll(createUser(1));
                break;
            case "2":
                //TODO create groups and tickets
                System.out.println("Create persons + tickets");
                users.addAll(createUser(readNumberOfUsers()));
                break;
            case "3":  //exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        park.addUsers(users);
    }

    public static int readNumberOfUsers() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        int numberOfUsers;
        try {
            numberOfUsers = Integer.parseInt(input);
            System.out.println();
        } catch (Exception e) {
            // System.out.println(e);
            System.out.print("Please enter a valid number: ");
            return readNumberOfUsers();
        }
        System.out.println();
        return numberOfUsers;
    }

    //TODO check if there is such a user already
    public static List<User> createUser(int numberOfUsers) throws Exception {
        List<User> users = new ArrayList<>();
        TicketType ticketType = null;
        if (numberOfUsers == 5) {
            ticketType = ticketType.SMALLGROUP;
        } else if (numberOfUsers > 5) {
            ticketType = ticketType.BIGGROUP;
        }
        for (int i = 0; i < numberOfUsers; i++) {

            System.out.print("Please enter a name: ");
            String name = readName();
            System.out.print("Please enter age: ");
            int age = readAge();
            System.out.print("What's the budget of " + name + ": ");
            double budget = readDouble();
            if (ticketType == null) {
                if (age < 18) {
                    ticketType = ticketType.UNDER18;
                } else {
                    ticketType = readTicketType();
                }
            }
            users.add(new User(name, age, budget, ticketType));
        }

        return users;
    }

    public static String readName() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        try {
            validateName(name);
        } catch (Exception e) {
            System.out.println(e);
            System.out.print("Please enter valid name ");
            return readName();
        }
        return name;
    }

    public static String validateName(String name) throws NameException {
        if (name.length() < 3 || name.length() > 35) {
            throw new NameException("The name has to be between 3 and 35 symbols!");
        } else if (!name.matches("[a-zA-Z]+")) {
            throw new NameException("The name must contain only letters!");
        } else {
            return name;
        }
    }

    private static int readAge() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        int age;
        try {
            age = Integer.parseInt(input);
            System.out.println();
        } catch (Exception e) {
            // System.out.println(e);
            System.out.print("Please enter valid age: ");
            return readAge();
        }
        System.out.println();
        return age;
    }

    private static double readDouble() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        double number;
        try {
            number = Double.parseDouble(input);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
            System.out.print("Please enter valid number: ");
            return readDouble();
        }
        return number;
    }

    private static TicketType readTicketType() throws IOException {
        String[] options = {"Adult", "Pensioner", "Disabled"};
        printOptions(options);
        String command = readCommand();
        switch (command) {
            case "1":
                return TicketType.ADULT;
            case "2":
                return TicketType.PENSIONEER;
            case "3":
                return TicketType.DISABLED;
            default:
                System.out.println("Not a valid choice!");
                System.out.println("Please enter your choice again!");
                return readTicketType();
        }
    }

    public static String readCommand() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine().toLowerCase();
        return command;
    }

    public static void printOptions(String[] options) {
        IntStream.range(0, options.length)
                .mapToObj(i -> (i + 1) + "." + options[i])
                .forEach(System.out::println);
    }
}