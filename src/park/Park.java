package  park;

import park.cinema.Cinema;
import park.cinema.Movie;
import park.cinema.MovieGenre;
import park.funzone.Attraction;
import park.funzone.AttractionDangerLevel;
import park.products.Product;
import park.stores.CashDesk;
import park.stores.FoodStore;
import park.stores.SouvenirStore;
import park.stores.Store;
import park.users.User;
import park.users.UserTicketPrice;

import java.util.*;
import java.util.stream.Collectors;

public class Park {
    private String name;
    protected String password;
    private List<Store> stores;
    private List<User> users;
    private List<Cinema> cinemas;
    private Set<Attraction> attractions;
    private EnumMap<UserTicketPrice, Double> ticketsPrices;
    private int ticketsCounter;
    private boolean isInAdminMode;

    public Park(String name, String password) {
        setName(name);
        this.password = password;
        this.stores = new ArrayList<>();
        this.users = new ArrayList<>();
        this.cinemas = new ArrayList<>();
        this.attractions = new HashSet<>();
        setTicketsPrice();
        this.ticketsCounter = 1;
        setIsInAdminMode();
    }

    /**
     * ---------------------------------Getters and setters-------------------------------------
     */
    public String getName() {
        return name;
    }

    private void setTicketsPrice() {
        this.ticketsPrices = new EnumMap<>(UserTicketPrice.class);
        ticketsPrices.put(UserTicketPrice.UNDER18, (double) 15);
        ticketsPrices.put(UserTicketPrice.PENSIONER, (double) 15);
        ticketsPrices.put(UserTicketPrice.DISABLED, (double) 15);
        ticketsPrices.put(UserTicketPrice.ADULT, (double) 20);
        ticketsPrices.put(UserTicketPrice.SMALLGROUP, (double) 14);
        ticketsPrices.put(UserTicketPrice.BIGGROUP, (double) 13);
    }

    public double getTicketsPrices(UserTicketPrice type) {
        return this.ticketsPrices.get(type);
    }

    private void setName(String name) {
        this.name = name;
    }

    private String addTicketsCounter() {
        return this.ticketsCounter++ + "";
    }

    public void setIsInAdminMode() {
        isInAdminMode = false;
    }
    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            isInAdminMode = true;
        }
        return true;
    }

    /**
     * ---------------------------------Creating users and other user functions-------------------------------------
     */
    public void createUsers(Map<InputDataCollection,UserTicketPrice> newUsers) {
        if(isInAdminMode) {
            List<User> users = new ArrayList<>();
            newUsers.forEach((k, v) ->
                    users.add(new User(
                            k.getFirst(),
                            Integer.parseInt(k.getSecond()),
                            Double.parseDouble(k.getThird()),
                            v)));
            buyTickets(users);
            addUsers(users);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public int findUserIndex(String name, String ticketNumber) {
        return users.stream()
                .filter(x -> x.getName().equals(name))
                .filter(x -> x.getTicketNumber(ticketNumber))
                .findFirst()
                .map(x -> users.indexOf(x))
                .orElse(-1);
    }

    public void consumeProduct(int userIndex, String productName) {
        User currentUser = this.getUserByIndex(userIndex);
        Product product = currentUser.getProductByName(productName);

        if (product == null) {
            System.out.println("Sorry the product was not found.");
            return;
        }

        //TODO test if the logic is working
        currentUser.consumeProduct(product);
    }

    public double getUserBudget(int userIndex) {
        return getUserByIndex(userIndex).getBudget();
    }

    public double getUserTicketPrice(int userIndex) {
        return getUserByIndex(userIndex).getTicketPrice();
    }

    public void addCredits(int userIndex, int numberOfTickets) {
        User user = getUserByIndex(userIndex);
        if (user.getBudget() < numberOfTickets * user.getTicketPrice()) {
            System.out.println("Sorry you don't have enough money!");
        } else {
            user.addCredits(numberOfTickets);
            System.out.printf("%s now has %d credits and budget of %.2f$\n", user.getName(), user.getUserTicketCredits(), user.getBudget());
        }
    }

    private void buyTickets(List<User> users) {
        users.forEach((user -> user.addTicket(addTicketsCounter(), getTicketsPrices(user.getUserType()))));
    }

    private void addUsers(List<User> users) {
        this.users.addAll(users);
        System.out.println("Successfully added!");
        users.forEach(user -> System.out.println(user.toString()));
    }

    private User getUserByIndex(int userIndex) {
        return this.users.get(userIndex);
    }

    private void removeUser(User user) {
        this.users.remove(user);
    }

    /**
     * ----------------------------------------CINEMAS----------------------------------------------
     */

    public void addCinemas(Set<String> cinemas) {
        Set<Cinema> cinemasToAdd = cinemas.stream()
                .map(Cinema::new)
                .collect(Collectors.toSet());
        this.cinemas.addAll(cinemasToAdd);
    }

    public void removeCinema(String cinemaName) {
        if(isInAdminMode) {
            cinemas.removeIf(x -> x.getName().equals(cinemaName));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void addMoviesToCinemas(String cinemaName, HashMap<String, MovieGenre> movies) {
        if(isInAdminMode) {
            Set<Movie> moviesToAdd = movies.entrySet()
                    .stream()
                    .map((x) -> new Movie(x.getKey(), x.getValue()))
                    .collect(Collectors.toSet());

            Cinema cinema = getCinemaByName(cinemaName);
            cinema.addMovie(moviesToAdd);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void removeMovieFromCinema(String cinemaName, String movieName) {
        if (isInAdminMode) {
            Cinema cinema = getCinemaByName(cinemaName);
            cinema.removeMovie(movieName);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void displayMoviesInCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);
        cinema.displayMovies();
        System.out.println();
    }

    public void removeProductsFromCinemaStore(String cinemaName, String foodName) {
        if (isInAdminMode) {
            Cinema cinema = getCinemaByName(cinemaName);
            Product product = cinema.getCinemaStore().getProductByName(foodName);
            if (product == null) {
                System.out.println("There is no such product!");
            } else {
                this.cinemas.get(this.cinemas.indexOf(cinema)).removeProduct(product);
                System.out.println("Product " + product.getName() + " successfully removed!");
            }
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void showCinemaStoreProducts(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);

        cinema.getCinemaStore().showProductsInStock();
    }

    private Cinema getCinemaByName(String cinemaName) {
        return cinemas.stream()
                .filter(x -> x.getName().equals(cinemaName))
                .findFirst()
                .get();
    }

    /**
     * ----------------------------------------STORES----------------------------------------------
     */
    public void createStore(List<InputDataCollection> stores) {
        if (isInAdminMode) {
            stores.stream().filter(store -> store.getThird().equals("Food"))
                    .forEach(store ->
                            addStore(new FoodStore(store.getFirst(), new CashDesk(Double.parseDouble(store.getSecond())))));

            stores.stream().filter(store -> store.getThird().equals("Souvenir"))
                    .forEach(store ->
                            addStore(new SouvenirStore(store.getFirst(), new CashDesk(Double.parseDouble(store.getSecond())))));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void removeStore(String storeName) {
        if (isInAdminMode) {
            this.stores.remove(getStoreByName(storeName));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void addProductsToStore(String storeName, HashMap<Product, Integer> productsToAdd) {
        if (isInAdminMode) {
            Store store = getStoreByName(storeName);
            store.addProducts(productsToAdd);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void addProductsToCinemaStore(String cinemaName, HashMap<Product, Integer> productsToAdd) {
        if (isInAdminMode) {
            Cinema cinema = getCinemaByName(cinemaName);

            cinema.updateCinemaStore(productsToAdd);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public String getStoreType(String storeName) {
        Store store = getStoreByName(storeName);

        if (store.getClass().equals(FoodStore.class)) {
            return "FoodStore";
        }
        return "SouvenirStore";
    }

    public boolean isThereStore(String name) {
        return stores.stream()
                .anyMatch(x -> x.getName().equals(name));
    }

    public void removeProductsFromStore(String storeName, String foodName) {
        if (isInAdminMode) {
            Store store = getStoreByName(storeName);
            store.removeProduct(store.getProductByName(foodName));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    //TODO add product quantity
    public void goShopping(String shopName, String productName, int currentUserIndex) {
        Store shop = this.getStoreByName(shopName);
        Product product = shop.getProductByName(productName);
        User currentUser = getUserByIndex(currentUserIndex);

        if (product == null) {
            return;
        }

        if (product.getPrice() > currentUser.getBudget()) {
            System.out.println("Sorry you don't have enough money to buy this product!");
            return;
        }

        shop.removeOneProduct(product);
        currentUser.addBoughtProduct(product);
        shop.addMoney(product.getPrice());
        System.out.printf("You successfully bought: %s and you have %.2f money left!\n", product, currentUser.getBudget());
    }

    private void addStore(Store newStore) {
        if (isInAdminMode) {
            this.stores.add(newStore);
        } else {
            System.out.println("You are not an admin!");
        }
    }

    private Store getStoreByName(String storeName) {
        return stores.stream()
                .filter(st -> st.getName().equals(storeName))
                .findFirst()
                .get();
    }


    /**
     * ----------------------------------------ATTRACTIONS----------------------------------------------
     */

    public void addAttractions(HashMap<String, AttractionDangerLevel> attractions) {
        if (isInAdminMode) {
            attractions.forEach((x, v) -> this.attractions.add(new Attraction(x, v)));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void removeAttraction(String attractionName) {
        if (isInAdminMode) {
            attractions.removeIf(x -> x.getName().equals(attractionName));
        } else {
            System.out.println("You are not an admin!");
        }
    }

    public void displayAttractions() {
        if (attractions.size() == 0) {
            System.out.println("Sorry the park does not have attractions yet.\n");
            return;
        }

        attractions.forEach(System.out::println);
    }

    /**
     * -------------------------------Show Statistics functions- NOT FINISHED----------------------------------
     */
    public void showUserStatistics() {
        if (users.size() == 0) {
            System.out.println("Sorry there are no users in the park yet.");
            return;
        }

        System.out.println("\n\t\t\t\t---------- USER STATISTICS ----------\n");
        System.out.println("\t\t\t\t\t" + users.size() + " users in the park\n");
        users.forEach(System.out::println);
        System.out.println("\n\t\t\t\t-------------------------------------\n");
    }

    public void showAttractionStatistics() {
        if (attractions.size() == 0) {
            System.out.println("Sorry there are no attractions in the park yet.");
            return;
        }

        System.out.println("\n\t\t\t\t---------- ATTRACTION STATISTICS ----------\n");
        attractions.forEach(System.out::println);
        System.out.println("\n\t\t\t\t-------------------------------------------\n");
    }

    public void showCinemaStatistics() {
        if (cinemas.size() == 0) {
            System.out.println("Sorry there are no cinemas in the park yet.");
            return;
        }

        System.out.println("\n\t\t\t\t---------- CINEMA STATISTICS ----------\n");
        for (Cinema cinema : cinemas) {
            System.out.printf("Cinema \"%s\"\n\n", cinema);
            cinema.displayMovies();
            System.out.println();
            cinema.displayCinemaProducts();
        }
        System.out.println("\n\t\t\t\t---------------------------------------\n");
    }

    public void showStoreStatistics() {
        if (stores.size() == 0) {
            System.out.println("Sorry there are no stores in the park yet.");
            return;
        }

        System.out.println("\n\t\t\t\t---------- STORE STATISTICS----------\n");
        for (Store store : stores) {
            System.out.println(store + "\n");
            store.showProductsInStock();
        }
        System.out.println("\n\t\t\t\t-------------------------------------\n");
    }

    public void showStoreProducts(String storeName) {
        Store store = getStoreByName(storeName);
        store.showProductsInStock();
    }

    /**
     * -------------------------------GetNames functions- FINISHED----------------------------------
     */

    public List<String> getStoreAllProductsNames(String storeName) {
        Store store = getStoreByName(storeName);

        return store.getAllProductsNames();
    }

    public List<String> getUserAllProductsNames(int userIndex) {
        User user = getUserByIndex(userIndex);

        return user.getAllProductsNames();
    }

    public List<String> getAllCinemasNames() {
        return this.cinemas.stream()
                .map(Cinema::getName)
                .collect(Collectors.toList());
    }

    public List<String> getMoviesFromCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);

        return cinema.getAllMoviesNames();
    }

    public List<String> getAttractionsNames() {
        return this.attractions.stream()
                .map(Attraction::getName)
                .collect(Collectors.toList());
    }

    public List<String> getStoresNames() {
        return this.stores.stream()
                .map(Store::getName)
                .collect(Collectors.toList());
    }

    public List<String> getProductsNamesInCinemaStore(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);

        return cinema.getProductsNamesInCinema();
    }

    public List<String> getAllTicketsPrices() {
        List<String> ticketPrices = new ArrayList<>();
        ticketPrices.add(String.format("%-10s", "Ticket") + " | Price");

        ticketPrices.addAll(this.ticketsPrices.entrySet().stream()
                .map(ticket -> String.format("%1$-10s | %2$.2f$", ticket.getKey(), ticket.getValue()))
                .collect(Collectors.toList()));

        return ticketPrices;
    }
}