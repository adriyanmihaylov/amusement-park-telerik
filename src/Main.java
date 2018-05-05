import exceptions.*;
import park.Park;
import park.cinema.MovieGenre;
import park.funzone.AttractionDangerLevel;
import park.products.*;
import park.stores.CashDesk;
import park.stores.FoodStore;
import park.stores.SouvenirStore;
import park.stores.Store;
import park.users.UserTicketPrice;
import park.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    private static Park park = new Park("Disneyland", "123456");

    public static void main(String[] args) throws Exception {
        mainMenu();
    }

    public static void mainMenu() throws Exception {
        String[] options = {"User menu", "Admin menu", "Exit"};
        printOptions(Arrays.asList(options));

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

    public static void userMenu() throws Exception {
        String[] options = {"Buy ticket", "Enter the park", "Exit"};
        printOptions(Arrays.asList(options));
        String command = readString();
        switch (command) {
            case "1":
                buyTicketMenu();
                break;
            case "2":
                int userIndex = findUserInPark();
                if(userIndex < 0) {
                    System.out.println("There is no such user!");
                } else {
                    parkMenu(userIndex);
                }
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        userMenu();
    }

    private static void adminMenu() throws Exception {
        String[] options = {"Stores", "Attractions", "Cinema", "Park Statistics", "Exit"};
        printOptions(Arrays.asList(options));
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
                parkStatistics();
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        adminMenu();
    }

    /**------------------------------------ADMIN functions-----------------------------------------------------*/

    /**
     * ------------------------------STORES functions---------------------------------
     */

    private static void storesMenu() throws Exception {
        String[] options = {"Remove store", "Add new store", "Get store products ", "Add/Remove products", "Exit"};
        printOptions(Arrays.asList(options));
        String command = readString();

        switch (command) {
            case "1":
                removeStore();
                break;
            case "2":
                addStore();
                break;
            case "3":
                printProductsInStore();
                break;
            case "4":
                addRemoveProductsMenu(chooseStore());
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        storesMenu();
    }

    private static void removeStore() throws Exception {
        System.out.println("Which store do you want to remove: ");
        String storeName = chooseStore();

        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        } else {
            park.removeStore(storeName);
            System.out.println(storeName + " was successfully removed!");
        }
    }

    private static String chooseStore() throws Exception {
        List<String> options = park.getStoresNames();
        if (options.size() == 0) {
            System.out.println("There are no stores in the park!");
            return "";
        }
        options.add("Exit");
        System.out.println("Please choose a store");

        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseStore();
        }

        return options.get(command - 1);
    }

    private static void addStore() throws Exception {
        System.out.print("How many stores do you want to add: ");
        int numberOfStores = readPositiveInteger();
        List<Store> stores = createStore(numberOfStores);

        if (stores.size() > 0) {
            park.addStores(stores);
            System.out.println("Stores were successfully added!");
        } else {
            System.out.println("No stores were added!");
        }
    }

    private static void printProductsInStore() throws Exception {
        String storeName = chooseStore();

        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        } else {
            park.showStoreProducts(storeName);
        }
    }

    public static List<Store> createStore(int numberOfStores) throws Exception {
        List<Store> stores = new ArrayList<>();
        int flag = 0;

        for (int i = 0; i < numberOfStores; i++) {
            System.out.print("Enter the name of store " + (i + 1) + ": ");
            String name = readName();
            if (!park.isThereStore(name)) {
                for (Store store : stores) {
                    if (store.getName().equals(name)) {
                        System.out.println("There is such a store already!");
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    flag = 0;
                    continue;
                }

                System.out.print("What's the budget of store " + name + ": ");
                Double budgetMoney = readMoney();

                System.out.println("What kind of store is " + name + "");
                int storeType = storeTypeMenu();

                switch (storeType) {
                    case 1:
                        stores.add(new FoodStore(name, new CashDesk(budgetMoney)));
                        break;
                    default:
                        stores.add(new SouvenirStore(name, new CashDesk(budgetMoney)));
                        break;
                }
            } else {
                System.out.println("There is such a store already!");
            }
        }
        return stores;
    }

    public static int storeTypeMenu() throws IOException {
        String[] options = {"Food store", "Souvenir store"};
        printOptions(Arrays.asList(options));
        switch (readString()) {
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                System.out.println("Invalid choice!");
                System.out.println("Please try again!");
                return storeTypeMenu();
        }
    }

    /**
     * ------------------------------PRODUCTS functions-------------------------------
     */

    private static String chooseProduct(String storeName,int currentUserIndex) throws Exception {
        List<String> options = new ArrayList<>();
        if (!storeName.isEmpty()) {
            options = park.getStoreAllProductsNames(storeName);
        } else if(currentUserIndex >= 0) {
            options = park.getUserAllProductsNames(currentUserIndex);
        }

        if (options.size() == 0) {
            System.out.println("There are no products!");
            return "";
        }

        options.add("Exit");
        System.out.println("Please choose a product: ");
        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseProduct(storeName, currentUserIndex);
        }
        return options.get(command - 1);
    }

    private static void addRemoveProductsMenu(String storeName) throws Exception {
        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        }

        String[] options = {"Add products", "Remove products", "Exit"};
        printOptions(Arrays.asList(options));
        switch (readString()) {
            case "1":
                if (park.getStoreType(storeName).equals("FoodStore")) {
                    park.addProductsToStore(storeName, createFoodProduct());
                } else {
                    park.addProductsToStore(storeName, createSouvenirProduct());
                }
                break;
            //TODO check if there is no such product before removing
            case "2":
                removeProductFromStore(storeName);
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid command!");
                System.out.println("Please try again!");
                break;
        }
        addRemoveProductsMenu(storeName);
    }

    private static void removeProductFromStore(String storeName) throws Exception {
        String productName = chooseProduct(storeName,-1);
        if (productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.removeProductsFromStore(storeName, productName);
    }

    private static HashMap<Product, Integer> createFoodProduct() throws Exception {
        System.out.print("Enter number of products to add: ");
        int numberOfProducts = readPositiveInteger();

        HashMap<Product, Integer> products = new HashMap<>();

        String expirationDate;
        for (int i = 0; i < numberOfProducts; i++) {
            System.out.printf("Product #%d name: ", i + 1);
            String productName = readName();
            //TODO - see if this product exist and stop if exist

            System.out.printf("Enter price of product %s: ", productName);
            double productPrice = readMoney();

            //TODO throws exception
            System.out.print("Please enter expiration date: ");
            expirationDate = readString();

            System.out.print("Please enter product quantity: ");
            int productQuantity = readPositiveInteger();

            System.out.println("Please choose one of the following: ");
            int command = chooseFoodProductType();
            switch (command) {
                case 1:
                    products.put(new ProductToEat(productName, productPrice, expirationDate), productQuantity);
                    break;
                case 2:
                    products.put(new ProductToDrink(productName, productPrice, expirationDate), productQuantity);
                    break;
            }
        }
        return products;
    }

    private static HashMap<Product, Integer> createSouvenirProduct() throws Exception {
        System.out.print("Enter number of products to add: ");
        int numberOfProducts = readPositiveInteger();

        HashMap<Product, Integer> products = new HashMap<>();

        for (int i = 0; i < numberOfProducts; i++) {
            System.out.printf("Product #%d name: ", i + 1);
            String productName = readName();
            //TODO - see if this product exist and stop if exist

            System.out.printf("Enter price of product %s: ", productName);
            double productPrice = readMoney();

            System.out.println("Please enter product quantity: ");
            int productQuantity = readPositiveInteger();
            products.put(new Souvenir(productName, productPrice), productQuantity);
        }
        return products;
    }

    private static int chooseFoodProductType() throws IOException {
        String[] options = {"The product is for eating", "The product is for drinking"};
        printOptions(Arrays.asList(options));

        switch (readString()) {
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                System.out.println("Invalid choice! Please choose from the following:\n");
                return chooseFoodProductType();
        }
    }

    /**
     * ------------------------------ATTRACTIONS functions-------------------------------
     */

    private static void attractionsMenu() throws Exception {
        String[] options = {"Add new attraction", "Remove attraction", "Show all attractions ", "Exit"};
        printOptions(Arrays.asList(options));
        String command = readString();
        switch (command) {
            case "1":
                addNewAttraction();
                break;
            case "2":
                removeAttraction();
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

    private static String chooseAttraction() throws IOException {
        List<String> options = park.getAttractionsNames();
        if (options.size() == 0) {
            System.out.println("There are no attractions in the park!");
            return "";
        }
        options.add("Exit");

        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseAttraction();
        }

        return options.get(command - 1);
    }

    private static void addNewAttraction() throws Exception {
        System.out.println("How many attraction do you want to add to the park ? ");

        int attractionsCount = readPositiveInteger();

        HashMap<String, AttractionDangerLevel> attractions = new HashMap<>();
        String attractionName;
        AttractionDangerLevel dangerLevel;
        for (int i = 0; i < attractionsCount; i++) {
            System.out.printf("Please enter attraction name #%d: ", i + 1);
            attractionName = readName();
            System.out.println("Choose attraction danger level: ");
            dangerLevel = chooseDangerLevel();
            attractions.put(attractionName, dangerLevel);
        }

        park.addAttractions(attractions);
        System.out.println("Done!\n");
    }

    private static AttractionDangerLevel chooseDangerLevel() throws IOException {
        String[] options = {"Low", "Medium", "High"};
        printOptions(Arrays.asList(options));

        String command = readString();
        switch (command) {
            case "1":
                return AttractionDangerLevel.LOW;
            case "2":
                return AttractionDangerLevel.MEDIUM;
            case "3":
                return AttractionDangerLevel.HIGH;
            default:
                System.out.println("Invalid choice! Please choose one of the following: ");
                return chooseDangerLevel();
        }
    }

    private static void removeAttraction() throws IOException {
        System.out.println("Choose attraction from the list: ");
        String chosenOption = chooseAttraction();
        if (chosenOption.equals("Exit") || chosenOption.isEmpty()) {
            return;
        }

        park.removeAttraction(chosenOption);
        System.out.println("Done!\n");
    }

    private static void showAttractions() {
        park.displayAttractions();
    }

    /**
     * ------------------------------ CINEMA functions-------------------------------
     */
    private static void cinemaMenu() throws Exception {
        String[] options = {"Add new Cinema", "Edit cinema", "Exit"};
        printOptions(Arrays.asList(options));
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

    private static String chooseProductInCinemaStore(String cinemaName) throws IOException {
        List<String> options = park.getProductsNamesInCinemaStore(cinemaName);

        if (options.size() == 0) {
            System.out.println("There are no products in " + cinemaName + " store !");
            return "";
        }
        options.add("Exit");

        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseProductInCinemaStore(cinemaName);
        }
        return options.get(command - 1);
    }

    private static String chooseCinema() throws Exception {
        List<String> options = park.getAllCinemasNames();

        if (options.size() == 0) {
            System.out.println("Sorry there are no cinemas in the park!");
            return "";
        }
        options.add("Exit");

        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseCinema();
        }
        return options.get(command - 1);
    }

    private static void addNewCinema() throws Exception {
        System.out.println("How many cinemas do you want to add to the park ?");

        Set<String> cinemas = new HashSet<>();
        int numberOfCinemas = readPositiveInteger();
        String cinemaName;
        for (int i = 0; i < numberOfCinemas; i++) {
            System.out.printf("Please enter the name of cinema #%d: ", i + 1);
            cinemaName = readName();
            if (park.getAllCinemasNames().contains(cinemaName)) {
                System.out.println("This cinema is already in the park !");
            } else {
                cinemas.add(cinemaName);
            }

        }

        park.addCinemas(cinemas);
        System.out.println("Done !\n");
    }

    private static void editCinema() throws Exception {
        String cinemaName = chooseCinema();
        if (cinemaName.equals("Exit") || cinemaName.isEmpty()) {
            return;
        }

        manageCinema(cinemaName);
    }

    private static void manageCinema(String cinemaName) throws Exception {
        //TODO clear the console
        System.out.printf("Cinema %s\n", cinemaName.toUpperCase());
        String[] options = {"Add movies", "Remove movies", "Display movies", "Add foods", "Show cinema's store foods",
                "Remove foods", "Delete cinema", "Exit"};
        printOptions(Arrays.asList(options));

        String command = readString();
        switch (command) {
            case "1":
                addMovie(cinemaName);
                break;
            case "2":
                removeMovie(cinemaName);
                break;
            case "3":
                displayMovies(cinemaName);
                break;
            case "4":
                park.addProductsToCinemaStore(cinemaName, createFoodProduct());
                break;
            case "5":
                park.showCinemaStoreProducts(cinemaName);
                break;
            case "6":
                removeProductFromCinemaStore(cinemaName);
                break;
            case "7":
                deleteCinema(cinemaName);
                return;
            case "8": //EXIT
                return;
            case "9":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        manageCinema(cinemaName);
    }

    private static void removeProductFromCinemaStore(String cinemaName) throws Exception {
        String productName = chooseProductInCinemaStore(cinemaName);

        if (productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.removeProductsFromCinemaStore(cinemaName, productName);
    }

    private static void deleteCinema(String name) {
        park.removeCinema(name);
        System.out.println("Done !\n");
    }

    private static String chooseMovie(String cinemaName) throws Exception {
        List<String> options = park.getMoviesFromCinema(cinemaName);

        if (options.size() == 0) {
            System.out.println("There are no movies in cinema " + cinemaName + "!");
            return "";
        }
        options.add("Exit");

        printOptions(options);

        int command = readPositiveInteger();
        if (command > options.size() || command < 1) {
            System.out.println("Invalid choice! Choose one of the following: ");
            return chooseProduct(cinemaName,-1);
        }
        return options.get(command - 1);
    }

    private static void addMovie(String cinemaName) throws Exception {
        System.out.println("How many movies do you want to add to the cinema ?");

        HashMap<String, MovieGenre> movies = new HashMap<>();
        int numberOfMovies = readPositiveInteger();
        String movieName;
        MovieGenre movieGenre;
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

    private static void removeMovie(String cinemaName) throws Exception {
        String movieName = chooseMovie(cinemaName);

        if (movieName.equals("Exit") || movieName.isEmpty()) {
            return;
        }

        park.removeMovieFromCinema(cinemaName, movieName);
    }

    private static void displayMovies(String cinemaName) {
        park.displayMoviesInCinema(cinemaName);
    }

    private static MovieGenre chooseGenre() throws IOException {
        String[] options = {"Animation", "Drama", "Thriller", "Action", "Comedy", "Musical"};
        printOptions(Arrays.asList(options));

        switch (readString()) {
            case "1":
                return MovieGenre.ACTION;
            case "2":
                return MovieGenre.DRAMA;
            case "3":
                return MovieGenre.THRILLER;
            case "4":
                return MovieGenre.ACTION;
            case "5":
                return MovieGenre.COMEDY;
            case "6":
                return MovieGenre.MUSICAL;
            default:
                System.out.println("Invalid choice! Please choose from the following genres: ");
                return chooseGenre();
        }
    }

    /**
     * -------------------------------PRINTING STATISTICS functions-------------------------
     */

    private static void parkStatistics() throws IOException {
        String[] options = {"User Statistics", "Attraction Statistics",
                "Cinema Statistics", "Store Statistics", "Exit"};
        printOptions(Arrays.asList(options));

        switch (readString()) {
            case "1":
                userStatistics();
                break;
            case "2":
                attractionStatistics();
                break;
            case "3":
                cinemaStatistics();
                break;
            case "4":
                storeStatistics();
                break;
            case "5":
                //EXIT
                return;
            default:
                System.out.println("Invalid choice! Please choose from the following: ");
                parkStatistics();
        }

        parkStatistics();
    }

    private static void userStatistics() {
        park.showUserStatistics();
    }

    private static void attractionStatistics() {
        park.showAttractionStatistics();
    }

    private static void cinemaStatistics() {
        park.showCinemaStatistics();
    }

    private static void storeStatistics() {
        park.showStoreStatistics();
    }

    /**--------------------------------------END OF ADMIN functions-----------------------------------------*/

    /**---------------------------------------USER functions------------------------------------------------*/

    public static void buyTicketMenu() throws Exception {
        String[] options = {"SingleTicket", "GroupTicket", "Exit"};
        printOptions(Arrays.asList(options));
        List<User> users = new ArrayList<>();

        switch (readString()) {
            case "1":
                users.addAll(createUsers(1));
                break;
            case "2":
                int sizeOfGroup = readSizeOfGroup();
                if (sizeOfGroup > 0) {
                    users.addAll(createUsers(sizeOfGroup));
                }
                break;
            case "3":  //exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        park.addUsers(users);
    }

    // TODO - ADD CONSUME PRODUCTS/LIST PRODUCTS
    public static int findUserInPark() throws Exception {
        System.out.print("Please enter user name: ");
        String name = readName();
        System.out.print("Please enter ticket number: ");
        String ticketNumber = readString();
        System.out.println();

        return park.findUserIndex(name, ticketNumber);
    }

    private static void parkMenu(int indexOfUser) throws Exception {
        String[] options = {"Add credits", "Go shopping", "Consume a product", "Watch a movie", "Ride attractions", "Exit"};
        printOptions(Arrays.asList(options));
        String command = readString();
        switch (command) {
            case "1":
                addCredits(indexOfUser);
                break;
            case "2":
                goShopping(indexOfUser);
                break;
            case "3":
                consumeProduct(indexOfUser);
            case "4":
                watchMovie(indexOfUser);
                break;
            case "5":
                rideAttractions(indexOfUser);
                break;
            case "6":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        parkMenu(indexOfUser);
    }

    private static void consumeProduct(int userIndex) throws Exception{
        String productName = chooseProduct("",userIndex);

        if (productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.consumeProduct(userIndex, productName);
    }

    private static void addCredits(int userIndex) throws Exception {
        System.out.printf("Your budget is %.2f$\n", park.getUserBudget(userIndex));
        System.out.printf("Price for 1 ticket is %.2f$\n",park.getUserTicketPrice(userIndex));
        System.out.println("1 ticket has 10 credits");
        System.out.println("Please enter the number of tickets:  ");

        int numberOfTickets = readPositiveInteger();
        park.addCredits(userIndex, numberOfTickets);
    }

    //TODO maybe change the products in the user to hashmap
    private static void goShopping(int userIndex) throws Exception {
        String storeName = chooseStore();
        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        }

        String productName = chooseProduct(storeName,-1);

        //TODO test if the logic is working
        park.goShopping(storeName, productName, userIndex);
    }

    //TODO add functionality
    private static void watchMovie(int userIndex) {
    }

    //TODO add functionality
    public static void rideAttractions(int userIndex) {
    }

    /**------------------------------------END OF USER functions----------------------------------------------*/


    /**CREATING USERS AND SELLING TICKETS TO THEM*/
    /**-----------------------------------------------------------------------------------------*/

    public static int readSizeOfGroup() throws IOException {
        System.out.print("Enter number of members: ");
        int numberOfUsers = readPositiveInteger();

        if (numberOfUsers < 2) {
            System.out.println("The group can not have less than 2 members!");
            return -1;
        }
        return numberOfUsers;
    }

    //TODO clear the console
    public static List<User> createUsers(int numberOfUsers) throws Exception {
        List<User> users = new ArrayList<>();
        UserTicketPrice userTicketType = null;

        if (numberOfUsers == 5) {
            userTicketType = userTicketType.SMALLGROUP;
        } else if (numberOfUsers > 5) {
            userTicketType = userTicketType.BIGGROUP;
        }
        int maxAge = 1;

        for (int i = 0; i < numberOfUsers; i++) {
            System.out.print("Please enter a name: ");
            String name = readName();
            System.out.print("Please enter age: ");
            int age = readAge();
            System.out.print("What's the budget of " + name + ": ");
            double budget = readMoney();
            if(userTicketType != userTicketType.SMALLGROUP && userTicketType != userTicketType.BIGGROUP) {
                if (age < 18) {
                    userTicketType = userTicketType.UNDER18;
                } else {
                    userTicketType = readTicketType();
                }
            }

            maxAge = Math.max(maxAge, age);
            users.add(new User(name, age, budget,userTicketType));
            System.out.println();
        }
        if (maxAge < 14) {
            System.out.println("You can not enter the park because there is no user older than 14 with you!");
            return new ArrayList<>();
        } else { // TODO clear the console here
            buyTickets(users);
            System.out.println("Successfully added");
            users.forEach(k -> System.out.println(k.toString()));
            return users;
        }
    }

    //TODO think if methods marked with [!] can be Interfaces
    /** [!] METHOD FINISHED*/
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

    /** [!] METHOD FINISHED*/
    public static String validateName(String name) throws NameException {
        if (name.length() < 3 || name.length() > 35) {
            throw new NameException("The name has to be between 3 and 35 symbols!");
        } else if (!name.matches("[a-zA-Z]+")) {
            throw new NameException("The name must contain only letters!");
        } else {
            return name;
        }
    }

    /** [!] METHOD FINISHED*/
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

    /** [!] METHOD FINISHED*/
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

    /** [!] METHOD FINISHED*/
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

    /** [!] METHOD FINISHED*/
    private static double validateMoney(String input) throws MoneyException {
        double money;
        try {
            money = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new MoneyException("The entered is not a number!");
        }
        if (money < 0) {
            throw new MoneyException("Money can't be less than 0! ");
        }

        return money;
    }

    /** [!] METHOD FINISHED*/
    private static UserTicketPrice readTicketType() throws IOException {
        String[] options = {"Adult", "Pensioner", "Disabled"};
        printOptions(Arrays.asList(options));
        String command = readString();
        switch (command) {
            case "1":
                return UserTicketPrice.ADULT;
            case "2":
                return UserTicketPrice.PENSIONER;
            case "3":
                return UserTicketPrice.DISABLED;
            default:
                System.out.println("Not a valid choice!");
                System.out.println("Please enter your choice again!");
                return readTicketType();
        }
    }

    public static List<User> buyTickets(List<User> users) {
        users.forEach((user -> user.addTicket(park.addTicketsCounter(), park.getTicketsPrices(user.getUserType()))));
        return users;
    }

    /**-----------------------------------------------------------------------------------------*/
    /** [!] METHOD FINISHED*/
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

     /** [!] METHOD FINISHED*/
    private static int validatePositiveInteger(String input) throws PositiveIntegerException {
        int number;
        try {
            number = Integer.parseInt(input);
        } catch (Exception e) {
            throw new PositiveIntegerException("The entered is not a number!");
        }
        if (number < 0) {
            throw new PositiveIntegerException("Please enter positive number!");
        }

        return number;
    }


    private static String readString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();
        return command;
    }

    private static void printOptions(List<String> options) {
        IntStream.range(0, options.size())
                .mapToObj(i -> (i + 1) + "." + options.get(i))
                .forEach(System.out::println);
    }
}