import exceptions.AgeException;
import exceptions.MoneyException;
import exceptions.NameException;
import park.Park;
import park.cinema.Cinema;
import park.users.UserType;
import park.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    private static Park park = new Park("Disneyland", "123456");

    public static void main(String[] args) throws Exception {
        mainMenu();
    }
    /**[!] METHOD FINISHED**/
    public static void mainMenu() throws Exception {
        String[] options = {"User menu", "Admin menu", "Exit"};
        printOptions(options);

        switch (readString()) {
            case "1":
                userMenu();
                break;
            case "2":
                System.out.print("Enter admin password: ");
                if (park.checkPassword(readString())) {
                    adminMenu();
                } else {
                    System.out.println("Username and password doesn't match!");
                }
                break;
            case "3": // exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        mainMenu();
    }
    /**METHOD FINISHED**/
    public static void userMenu() throws Exception {
        String[] options = {"Buy ticket", "Enter the park", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                buyTicketMenu();
                break;
            case "2":
                parkMenu(null,-1);
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        userMenu();
    }

    /**METHOD FINISHED**/
    public static void buyTicketMenu() throws Exception {
        String[] options = {"SingleTicket", "GroupTicket", "Exit"};
        printOptions(options);
        List<User> users = new ArrayList<>();

        switch (readString()) {
            case "1":
                users.addAll(createUser(1));
                break;
            case "2":
                int sizeOfGroup = readSizeOfGroup();
                if(sizeOfGroup > 0) {
                    users.addAll(createUser(sizeOfGroup));
                }break;
            case "3":  //exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        park.addUsers(users);
    }

    //TODO create methods - goShopping, watchAMovie, goOnAttractions
    private static void parkMenu(User currentUser, int indexOfUser) throws Exception {
        if (currentUser == null) {
            indexOfUser = findUserInPark();
            if (indexOfUser < 0) {
                System.out.println("There is no such user!");
                return;
            } else {
                currentUser = park.getUserByIndex(indexOfUser);
            }
        }
        String[] options = {"Add credits", "Go shopping", "Watch a movie", "Disneyland attractions", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                addCredits(indexOfUser, currentUser);
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        parkMenu(currentUser, indexOfUser);
    }

    //TODO create Admin functionality - create remove everything they want
    private static void adminMenu() throws Exception {
        String[] options = {"Stores","Attractions","Cinema","Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                storesMenu();
                break;
            case "2":
                attractionsMenu();
                break;
            case "3":
                cinemaMenu();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        adminMenu();
    }

    //TODO add functionality
    private static void storesMenu() throws IOException {
        String[] options = {"Remove store", "Add new store", "Get store products ", "Add products to store", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        storesMenu();
    }

    //TODO add functionality
    private static void attractionsMenu() throws IOException {
        String[] options = {"Remove attraction", "Add new attraction", "Show all attractions ", "Change attraction", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        attractionsMenu();
    }

    //TODO add functionality
    private static void cinemaMenu() throws Exception {
        String[] options = {"Add new Cinema", "Add movie", "Remove movie", "Add foods to cinema's store", "Remove foods from cinema store", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                addNewCinema();
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        cinemaMenu();
    }

    /**METHOD FINISHED**/
    public static int readSizeOfGroup() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter number of members: ");
        String input = reader.readLine();
        int numberOfUsers;
        try {
            numberOfUsers = Integer.parseInt(input);
            System.out.println();
        } catch (Exception e) {
            // System.out.println(e);
            System.out.print("Please enter a valid number: ");
            return readSizeOfGroup();
        }

        if(numberOfUsers < 2) {
            System.out.println("The group can not have less than 2 members!");
            return -1;
        }

        return numberOfUsers;
    }

    /**METHOD FINISHED**/
    public static List<User> createUser(int numberOfUsers) throws Exception {
        List<User> users = new ArrayList<>();
        UserType userTypeOfTicket = null;
        if (numberOfUsers == 5) {
            userTypeOfTicket = userTypeOfTicket.SMALLGROUP;
        } else if (numberOfUsers > 5) {
            userTypeOfTicket = userTypeOfTicket.BIGGROUP;
        }
        int maxAge = 1;

        for (int i = 0; i < numberOfUsers; i++) {
            System.out.print("Please enter a name: ");
            String name = readName();
            System.out.print("Please enter age: ");
            int age = readAge();
            System.out.print("What's the budget of " + name + ": ");
            double budget = readMoney();

            if (userTypeOfTicket == null) {
                if (age < 18) {
                    userTypeOfTicket = userTypeOfTicket.UNDER18;
                } else {
                    userTypeOfTicket = readTicketType();
                }
            }
            maxAge = Math.max(maxAge,age);
            users.add(new User(name, age, budget, userTypeOfTicket));
            System.out.println();
        }
        if(maxAge < 14) {
            System.out.println("You can not enter the park because there is no user older than 14 with you!");
            return new ArrayList<User>();
        }
        else { // TODO clear the console here
            users = buyTickets(users);
            System.out.println("Successfully added");
            users.forEach(user -> System.out.println(user.toString()));
            return users;
        }
    }

    //TODO think if methods marked with [!] can be Interfaces
    /**[!] METHOD FINISHED**/
    public static String readName() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        try {
            validateName(name);
        } catch (NameException e) {
            System.out.println(e);
            System.out.print("Please enter valid name ");
            return readName();
        }
        return name;
    }

    /**[!] METHOD FINISHED**/
    public static String validateName(String name) throws NameException {
        if (name.length() < 3 || name.length() > 35) {
            throw new NameException("The name has to be between 3 and 35 symbols!");
        } else if (!name.matches("[a-zA-Z]+")) {
            throw new NameException("The name must contain only letters!");
        } else {
            return name;
        }
    }

    /**[!] METHOD FINISHED**/
    private static int readAge() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        int age;
        try {
            age = validateAge(input);
        } catch (AgeException e) {
            System.out.println(e);
            System.out.print("Please enter a valid age: ");
            return readAge();
        }
        return age;
    }

    /**[!] METHOD FINISHED**/
    private static int validateAge(String input) throws AgeException {
        int age;
        try {
            age = Integer.parseInt(input);
        } catch (Exception e) {
            throw new AgeException("The entered is not a number!");
        }
        if (age < 1 || age > 114) {
            throw new AgeException("Invalid age! Age has to be between 1 and 114");
        }
        return age;
    }

    /**[!] METHOD FINISHED**/
    private static double readMoney() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        double money;
        try {
            money = validateMoney(input);
        } catch (MoneyException e) {
            System.out.println(e);
            System.out.print("Please enter again: ");
            return readMoney();
        }
        return money;
    }

    /**[!] METHOD FINISHED**/
    private static double validateMoney(String input) throws MoneyException {
        double money;
        try {
            money = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new MoneyException("The entered is not a number!");
        }
        if(money < 0) {
            throw new MoneyException("Money can't be less than 0! ");
        }

        return money;
    }

    /**METHOD FINISHED**/
    private static UserType readTicketType() throws IOException {
        String[] options = {"Adult", "Pensioner", "Disabled"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                return UserType.ADULT;
            case "2":
                return UserType.PENSIONER;
            case "3":
                return UserType.DISABLED;
            default:
                System.out.println("Not a valid choice!");
                System.out.println("Please enter your choice again!");
                return readTicketType();
        }
    }

    //TODO create something better - change setTicketsCounter to void and make everything in stream()
    public static List<User> buyTickets(List<User> users) {
        users.forEach(user -> user.addTicket(park.getTicketsCounter() + park.setTicketsCounter()));
        return users;
    }

    public static  int findUserInPark() throws Exception {
        System.out.print("Please enter user name: ");
        String name = readName();
        System.out.print("Please enter ticket number: ");
        String ticketNumber = readString();
        System.out.println();

        return park.findUserIndex(name, ticketNumber);
    }

    //TODO create exception method to validate int - when reading numberOfTicket(look method readAge())
    private static void addCredits(int currentUserIndex,User currentUser) throws Exception {
        System.out.println("1 ticket = 10 credits");
        System.out.println("Please enter the number of tickets:  ");

        int numberOfTickets = Integer.parseInt(readString());
        if (currentUser.getBudget() < numberOfTickets * currentUser.getTicketPrice()) {
            System.out.println("Sorry you don't have enough money!");
        } else {
            currentUser.addCredits(numberOfTickets);
            park.updateUser(currentUserIndex, currentUser);
            System.out.printf("User %s now has %d credits and budget of %.2f$\n", currentUser.getName(), currentUser.getUserTicketCredits(), currentUser.getBudget());
        }
    }

    //TODO create exception method to validate int - when reading numberOfCinemas
    private static void addNewCinema() throws Exception {
        System.out.println("How many cinemas do you want to add to the park ?");

        HashSet<String> cinemas = new HashSet<>();
        int numberOfCinemas = Integer.parseInt(readString());
        String cinemaName;
        for (int i = 0; i < numberOfCinemas; i++) {
            System.out.printf("Please, enter the name of cinema %d: ", i + 1);
            cinemaName = readName();
            cinemas.add(cinemaName);
        }

        park.addCinemas(cinemas);

        System.out.println("Done !\n");
    }

    /**METHOD FINISHED**/
    private static String readString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();
        return command;
    }

    /**METHOD FINISHED**/
    private static void printOptions(String[] options) {
        IntStream.range(0, options.length)
                .mapToObj(i -> (i + 1) + "." + options[i])
                .forEach(System.out::println);
    }
}