import exceptions.*;
import park.InputDataCollection;
import park.Park;
import park.cinema.MovieGenre;
import park.funzone.AttractionLevel;
import park.products.*;
import park.users.UserTicketPrice;

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
                if (userIndex < 0) {
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
                park.setIsInAdminMode();
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
        }

        if (isTrueMenu()) {
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
        createStore(numberOfStores);
    }

    private static void printProductsInStore() throws Exception {
        String storeName = chooseStore();

        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        } else {
            park.showStoreProducts(storeName);
        }
    }

    private static void createStore(int numberOfStores) throws Exception {
        List<InputDataCollection> newStores = new ArrayList<>();
        int flag = 0;

        for (int i = 0; i < numberOfStores; i++) {
            System.out.print("Enter the name of store " + (i + 1) + ": ");
            String name = readName();
            if (!park.isThereStore(name)) {
                for (InputDataCollection store : newStores) {
                    if (store.getFirst().equals(name)) {
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
                String storeType = storeTypeMenu();

                newStores.add(new InputDataCollection(name, budgetMoney + "", storeType));
            } else {
                System.out.println("There is such a store already!");
            }
        }
        if (newStores.size() > 0) {
            park.createStore(newStores);
        } else {
            System.out.println("No stores were added!");
        }
    }

    public static String storeTypeMenu() throws IOException {
        String[] options = {"Food store", "Souvenir store"};
        printOptions(Arrays.asList(options));
        switch (readString()) {
            case "1":
                return "Food";
            case "2":
                return "Souvenir";
            default:
                System.out.println("Invalid choice!");
                System.out.println("Please try again!");
                return storeTypeMenu();
        }
    }

    /**
     * ------------------------------PRODUCTS functions-------------------------------
     */

    private static String chooseProduct(String storeName, int currentUserIndex) throws Exception {
        List<String> options = new ArrayList<>();
        if (!storeName.isEmpty()) {
            options = park.getStoreAllProductsNames(storeName);

            if(options.size() == 0) {
                options = park.getProductsNamesInCinemaStore(storeName);
            }
        } else if (currentUserIndex >= 0) {
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
        String productName = chooseProduct(storeName, -1);
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
        System.out.println("How many attractions do you want to add to the park?");

        int attractionsCount = readPositiveInteger();

        HashMap<String, AttractionLevel> attractions = new HashMap<>();
        String attractionName;
        AttractionLevel dangerLevel;
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

    private static AttractionLevel chooseDangerLevel() throws IOException {
        String[] options = {"Low", "Medium", "High"};
        printOptions(Arrays.asList(options));

        String command = readString();
        switch (command) {
            case "1":
                return AttractionLevel.LOW;
            case "2":
                return AttractionLevel.MEDIUM;
            case "3":
                return AttractionLevel.HIGH;
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

        if (isTrueMenu()) {
            park.removeAttraction(chosenOption);
            System.out.println("Done!\n");
        }
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

    private static String chooseCinema() throws Exception {
        List<String> options = park.getAllCinemasNames();

        if (options.size() == 0) {
            System.out.println("Sorry there are no cinemas in the park!");
            return "";
        }
        options.add("Exit");

        System.out.println("Please choose a cinema");
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
        String productName = chooseProduct(cinemaName,-1);

        if (productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.removeProductsFromCinemaStore(cinemaName, productName);
    }

    private static void deleteCinema(String name) {
        if (isTrueMenu()) {
            park.removeCinema(name);
            System.out.println("Done !\n");
        }
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
            return chooseProduct(cinemaName, -1);
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
                return MovieGenre.ANIMATION;
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

    /**
     * ---------------------------------------USER functions------------------------------------------------
     */

    public static void buyTicketMenu() throws Exception {
        String[] options = {"See ticket prices", "SingleTicket", "GroupTicket", "Exit"};
        printOptions(Arrays.asList(options));

        switch (readString()) {
            case "1":
                park.getAllTicketsPrices().forEach(System.out::println);
                break;
            case "2":
                createUsers(1);
                break;
            case "3":
                int sizeOfGroup = readSizeOfGroup();
                if (sizeOfGroup > 0) {
                    createUsers(sizeOfGroup);
                }
                break;
            case "4":  //exit
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
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

    //TODO - add cinemaStore functionality
    private static  void goOnCinema(int indexOfUser) throws Exception {
        String cinemaName = chooseCinema();
        if (cinemaName.equals("Exit") || cinemaName.isEmpty()) {
            return;
        }

        String[] options = {"Watch a movie","Buy something from the store", "Exit cinema"};
        printOptions(Arrays.asList(options));
        int command = readPositiveInteger();
        switch (command) {
            case 1:
                watchMovie(cinemaName,indexOfUser);
                break;
            case 2:
                goShoppingInCinemaStore(cinemaName,indexOfUser);
                //TODO buy something from cinema store
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid command");
                System.out.println("Please choose an option from the list!");
                break;
        }
        goOnCinema(indexOfUser);
    }

    private static void parkMenu(int indexOfUser) throws Exception {
        String[] options = {"Add credits", "Go shopping", "Consume a product", "Enter Cinema", "Ride attractions", "Show user info", "Exit"};
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
                break;
            case "4":
               goOnCinema(indexOfUser);
                break;
            case "5":
                rideAttractions(indexOfUser);
                break;
            case "6":
                showUserInfo(indexOfUser);
                break;
            case "7":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }

        parkMenu(indexOfUser);
    }

    private static void consumeProduct(int userIndex) throws Exception {
        String productName = chooseProduct("", userIndex);

        if (productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.consumeProduct(userIndex, productName);
    }

    private static void addCredits(int userIndex) throws Exception {
        System.out.printf("Your budget is %.2f$\n", park.getUserBudget(userIndex));
        System.out.printf("Price for 1 ticket is %.2f$\n", park.getUserTicketPrice(userIndex));
        System.out.println("1 ticket has 10 credits");
        System.out.println("Please enter the number of tickets:  ");

        int numberOfTickets = readPositiveInteger();
        park.addCredits(userIndex, numberOfTickets);
    }

    private static void showUserInfo(int userIndex) {
        park.showUserInfo(userIndex);
    }

    //TODO change the products in the user to hashmap
    private static void goShopping(int userIndex) throws Exception {
        String storeName = chooseStore();
        if (storeName.equals("Exit") || storeName.isEmpty()) {
            return;
        }

        String productName = chooseProduct(storeName, -1);

        if(productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.userBuyProduct(storeName, productName, userIndex);
    }

    private static void goShoppingInCinemaStore(String cinemaName, int indexOfUser) throws Exception {
        String productName = chooseProduct(cinemaName,-1);
        if(productName.equals("Exit") || productName.isEmpty()) {
            return;
        }

        park.userBuyProduct(cinemaName,productName,indexOfUser);
    }

    private static void watchMovie(String cinemaName,int userIndex) throws Exception {
        String movieName = chooseMovie(cinemaName);
        if (movieName.equals("Exit") || cinemaName.isEmpty()) {
            return;
        }

        park.userWatchMovie(userIndex, cinemaName, movieName);
    }

    //TODO test if the logic is working
    public static void rideAttractions(int userIndex) throws Exception {
        String attractionName = chooseAttraction();
        if (attractionName.equals("Exit") || attractionName.isEmpty()) {
            return;
        }

        park.userRideAttraction(userIndex, attractionName);
    }

    /**------------------------------------END OF USER functions----------------------------------------------*/

    /**
     * -----------------------------CREATING USERS AND SELLING TICKETS TO THEM-----------------------------------
     */

    public static int readSizeOfGroup() {
        System.out.print("Enter number of members: ");
        int numberOfUsers = readPositiveInteger();

        if (numberOfUsers < 2) {
            System.out.println("The group can not have less than 2 members!");
            return -1;
        }
        return numberOfUsers;
    }

    public static void createUsers(int numberOfUsers) throws Exception {
        Map<InputDataCollection, UserTicketPrice> newUsers = new HashMap<>();
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

            if (userTicketType != userTicketType.SMALLGROUP && userTicketType != userTicketType.BIGGROUP) {
                if (age < 18) {
                    userTicketType = userTicketType.UNDER18;
                } else {
                    userTicketType = readTicketType();
                }
            }

            maxAge = Math.max(maxAge, age);
            newUsers.put(new InputDataCollection(name, age + "", budget + ""), userTicketType);
            System.out.println("\n");
        }

        if (maxAge < 14) {
            System.out.println("You can not enter the park because there is no user older than 14 with you!");
        } else {
            park.createUsers(newUsers);
        }
    }

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

    /**
     * ------------------------------------Exception methods -----------------------------------------------------
     */
    public static String readName() {
        String name;
        try {
            name = readString();
            validateName(name);
        } catch (NameException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter valid name ");
            return readName();
        } catch (IOException e) {
            System.out.println("Error when reading name!");
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

    private static int readAge() {
        String input;
        int age;
        try {
            input = readString();
            age = validateAge(input);
        } catch (AgeException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter a valid age: ");
            return readAge();
        } catch (PositiveIntegerException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter a valid age: ");
            return readAge();
        } catch (IOException e) {
            System.out.println("Error when reading age!");
            System.out.print("Please enter a valid age: ");
            return readAge();
        }
        return age;
    }

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

    private static double readMoney() {
        String input;
        double money;
        try {
            input = readString();
            money = validateMoney(input);
        } catch (MoneyException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter again: ");
            return readMoney();
        } catch (IOException e) {
            System.out.println("Error when reading money!");
            System.out.print("Please enter again: ");
            return readMoney();
        }
        return money;
    }

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

    private static int readPositiveInteger() {
        String input;
        int number;
        try {
            input = readString();
            number = validatePositiveInteger(input);
        } catch (PositiveIntegerException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter a valid number: ");
            return readPositiveInteger();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter a valid number: ");
            return readPositiveInteger();
        }
        return number;
    }

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

    /**
     * ---------------------------------------------------------------------------------------------------------
     */
    private static boolean isTrueMenu() {
        System.out.println("Are you sure you want to do that?");
        String[] options = {"Yes", "No"};
        printOptions(Arrays.asList(options));

        int command = readPositiveInteger();
        switch (command) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                System.out.println("Wrong input! Try again!");
                return isTrueMenu();
        }
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