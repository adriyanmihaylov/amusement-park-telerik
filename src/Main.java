import exceptions.AgeException;
import exceptions.PositiveIntegerException;
import exceptions.MoneyException;
import exceptions.NameException;
import park.Park;
import park.cinema.Cinema;
import park.users.UserType;
import park.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
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

   /**-----------------------------------------------------------------------------------------*/
    /**ADMIN METHODS*/

    /**Stores functions*/
    //TODO add functionality
    private static void storesMenu() throws IOException {
        String[] options = {"Remove store", "Add new store", "Get store products ", "Add products to store", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                removeStore();
                break;
            case "2":
                addStore();
                break;
            case "3":
                getProducts();
                break;
            case "4":
                addProductsToStore();
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        storesMenu();
    }

    private static void removeStore() {

    }

    private  static void addStore() {

    }

    private static void getProducts() {

    }

    private  static void addProductsToStore() {

    }

    /**Attractions functions*/
    //TODO add functionality
    private static void attractionsMenu() throws IOException {
        String[] options = {"Remove attraction", "Add new attraction", "Show all attractions ", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                removeAttraction();
                break;
            case "2":
                addNewAttraction();
                break;
            case "3":
                showAttractions();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        attractionsMenu();
    }

    private static void removeAttraction() {
    }
    private static void addNewAttraction() {
    }
    private static void showAttractions() {
    }

    /**Cinema functions*/ //TODO KRASIMIR ZAHARIEV
    //TODO add functionality
    private static void cinemaMenu() throws Exception {
        String[] options = {"Add new Cinema", "Edit cinema", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                addNewCinema();
                break;
            case "2":
                editCinema();
                break;
            case "3": //Exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }

        cinemaMenu();
    }

    private static void editCinema() throws Exception {
        if (park.getCinemas().size() < 1) {
            System.out.println("Sorry the park does not have a cinema yet.\n");
            return;
        }

        System.out.println("Choose cinema from the list: ");
        String chosenOption = chooseCinema();
        if (chosenOption.equals("Exit")) {
            return;
        }

        manageCinema(chosenOption);
    }

    private static String chooseCinema() throws IOException {
        Set<Cinema> cinemasInPark = park.getCinemas();
        String[] options = new String[cinemasInPark.size() + 1];
        List<String> test = cinemasInPark.stream()
                .map(Cinema::getName)
                .collect(Collectors.toList());
        //change options = cinemasInPark.toArray(options);
        //to see the error;
        options = test.toArray(options);
        options[options.length - 1] = "Exit";
        printOptions(options);

        int command = readPositiveInteger();

        if (command > options.length) {
            //TODO clear the console
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseCinema();
        }

        return options[command - 1];
    }

    private static void addNewCinema() throws Exception {
        System.out.println("How many cinemas do you want to add to the park ?");

        HashSet<String> cinemas = new HashSet<>();
        int numberOfCinemas = readPositiveInteger();
        String cinemaName;
        for (int i = 0; i < numberOfCinemas; i++) {
            System.out.printf("Please enter the name of cinema #%d: ", i + 1);
            cinemaName = readName();
            if (park.getCinemas().contains(cinemaName)) {
                System.out.println("This cinema is already in the park !");
            } else {
                cinemas.add(cinemaName);
            }
        }

        park.addCinemas(cinemas);
        System.out.println("Done !\n");
    }

    private static void manageCinema(String cinemaName) throws Exception {
        System.out.printf("Manage %s cinema:\n", cinemaName);
        String[] options = {"Remove cinema", "Add movies", "Remove movies", "Display movies", "Add foods to cinema's store",
                "Remove foods from cinema store", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                removeCinema(cinemaName);
                return;
            case "2":
                addMovie(cinemaName);
                break;
            case "3":
                //removeMovie(cinemaName);
                break;
            case "4":
                displayMovies(cinemaName);
                break;
            case "5":
                //addFoodToCinema();
                break;
            case "6":
                //removeFoodFromCinema();
                break;
            case "7": //Exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }

        manageCinema(cinemaName);
    }

    private static void removeCinema(String cinemaName) throws IOException {
        park.removeCinema(cinemaName);
        System.out.println("Done !\n");
    }

    //TODO this method must be in Park which calls method in Cinema
    private static void displayMovies(String cinemaName) {
        park.getCinemas()
                .stream()
                .filter(x -> x.getName().equals(cinemaName))
                .forEach(Cinema::displayMovies);
    }

    //TODO ! METHOD IS NOT DONE
    private static void addMovie(String cinemaName) throws Exception {
        System.out.println("How many movies do you want to add to the cinema ?");

        HashMap<String, String> movies = new HashMap<>();
        int numberOfMovies = readPositiveInteger();
        String movieName, movieGenre;
        for (int i = 0; i < numberOfMovies; i++) {
            System.out.printf("Please enter the name of movie #%d: ", i + 1);
            movieName = readName();
            System.out.println("Please choose one of the following genres:");
            movieGenre = chooseGenre();
            movies.put(movieName, movieGenre);
        }

        park.addMoviesToCinemas(cinemaName, movies);
        System.out.println("Done !\n");
    }

    private static String chooseGenre() throws IOException {
        String[] options = {"Animation", "Drama", "Thriller", "Action", "Comedy", "Musical"};
        printOptions(options);

        switch (readString()) {
            case "1":
                return options[0];
            case "2":
                return options[1];
            case "3":
                return options[2];
            case "4":
                return options[3];
            case "5":
                return options[4];
            case "6":
                return options[5];
            default:
                System.out.println("Invalid choice! Please choose from the following genres: ");
                return chooseGenre();
        }
    }


    /**END OF ADMIN METHODS*/
    /**-----------------------------------------------------------------------------------------*/

    /**-----------------------------------------------------------------------------------------*/
    /**USER METHODS*/

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

    /**METHOD FINISHED**/
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
        String[] options = {"Add credits", "Go shopping", "Watch a movie", "Ride attractions", "Exit"};
        printOptions(options);
        String command = readString();
        switch (command) {
            case "1":
                addCredits(indexOfUser, currentUser);
                break;
            case "2":
                goShopping(indexOfUser,currentUser);
                break;
            case "3":
                watchMovie(indexOfUser,currentUser);
                break;
            case "4":
                rideAttractions(indexOfUser,currentUser);
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        parkMenu(currentUser, indexOfUser);
    }

    //TODO TEST addCredits with wrong input
    private static void addCredits(int currentUserIndex,User currentUser) throws Exception {
        System.out.println("1 ticket = 10 credits");
        System.out.println("Please enter the number of tickets:  ");

        int numberOfTickets = readPositiveInteger();
        if (currentUser.getBudget() < numberOfTickets * currentUser.getTicketPrice()) {
            System.out.println("Sorry you don't have enough money!");
        } else {
            currentUser.addCredits(numberOfTickets);
            park.updateUser(currentUserIndex, currentUser);
            System.out.printf("User %s now has %d credits and budget of %.2f$\n", currentUser.getName(), currentUser.getUserTicketCredits(), currentUser.getBudget());
        }
    }

    //TODO add functionality
    private static void goShopping(int indexOfUser, User currentUser) {
    }

    //TODO add functionality
    private static void watchMovie(int indexOfUser, User currentUser) {
    }

    //TODO add functionality
    public static void rideAttractions(int indexOfUser, User currentUser) {
    }

    /**END OF USER METHODS*/
    /**-----------------------------------------------------------------------------------------*/


    /**CREATING USERS AND SELLING TICKETS TO THEM*/
    /**-----------------------------------------------------------------------------------------*/

    /**METHOD FINISHED**/
    public static int readSizeOfGroup() throws IOException {
        System.out.print("Enter number of members: ");
        int numberOfUsers = readPositiveInteger();

        if(numberOfUsers < 2) {
            System.out.println("The group can not have less than 2 members!");
            return -1;
        }
        return numberOfUsers;
    }

    /**METHOD FINISHED**/
    //TODO clear the console
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
            return new ArrayList<>();
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
        String name = readString();
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
        String input = readString();
        int age;
        try {
            age = validateAge(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Please enter a valid age: ");
            return readAge();
        }
        return age;
    }

    /**[!] METHOD FINISHED**/
    private static int validateAge(String input) throws AgeException, PositiveIntegerException {
        int age;
        try {
            age = Integer.parseInt(input);
        } catch (Exception e) {
            throw new PositiveIntegerException("The entered is not a number!");
        }
        if (age < 1 || age > 114) {
            throw new AgeException("Invalid age! Age has to be between 1 and 114");
        }
        return age;
    }

    /**[!] METHOD FINISHED**/
    private static double readMoney() throws IOException {
        String input = readString();
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

    //TODO create something better - change setTicketsCounter to void and make everything stream()
    public static List<User> buyTickets(List<User> users) {
        users.forEach(user -> user.addTicket(park.getTicketsCounter() + park.setTicketsCounter()));
        return users;
    }
    /**-----------------------------------------------------------------------------------------*/


    /**[!] METHOD FINISHED**/
    private static int readPositiveInteger() throws IOException {
        String input = readString();
        int number;
        try {
            number = validatePositiveInteger(input);
        } catch (PositiveIntegerException e) {
            System.out.println(e);
            System.out.print("Please enter a valid number: ");
            return readPositiveInteger();
        }
        return number;
    }

    /**[!] METHOD FINISHED**/
    private static int validatePositiveInteger(String input) throws PositiveIntegerException {
        int number;
        try {
            number = Integer.parseInt(input);
        } catch (Exception e) {
            throw new PositiveIntegerException("The entered is not a number!");
        }
        if(number < 0) {
            throw  new PositiveIntegerException("Please enter positive number!");
        }

        return number;
    }

    public static  int findUserInPark() throws Exception {
        System.out.print("Please enter user name: ");
        String name = readName();
        System.out.print("Please enter ticket number: ");
        String ticketNumber = readString();
        System.out.println();

        return park.findUserIndex(name, ticketNumber);
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