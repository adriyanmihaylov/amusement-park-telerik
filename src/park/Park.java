package  park;

import park.cinema.Cinema;
import park.cinema.Movie;
import park.cinema.MovieGenre;
import park.funzone.Attraction;
import park.funzone.AttractionDangerLevel;
import park.products.Product;
import park.stores.FoodStore;
import park.stores.Store;
import park.users.User;

import java.util.*;
import java.util.stream.Collectors;

public class Park {
    private String name;
    protected String password;
    private List<Store> stores;
    private List<User> users;
    private List<Cinema> cinemas;
    private Set<Attraction> attractions;
    private int ticketsCounter;

    public Park(String name,String password) {
        setName(name);
        this.password = password;
        this.stores = new ArrayList<>();
        this.users = new ArrayList<>();
        this.cinemas = new ArrayList<>();
        this.attractions = new HashSet<>();
        this.ticketsCounter = 1;
    }
    /**---------------------------------Getters and setters-------------------------------------*/
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getTicketsCounter() {
        return this.ticketsCounter;
    }

    //TODO change function to void
    public String setTicketsCounter() {
        this.ticketsCounter++;
        return "";
    }


    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**---------------------------------USER functions-------------------------------------*/

    //TODO change the getter functions of USER
    public int findUserIndex(String name,String ticketNumber) {
        return users.stream()
                .filter(x -> x.getName().equals(name))
                .filter(x -> x.getTicketNumber(ticketNumber))
                .findFirst()
                .map(x -> users.indexOf(x))
                .orElse(-1);
    }

    //TODO change getUserByIndex to private
    public User getUserByIndex(int userIndex) {
        return this.users.get(userIndex);
    }

    //TODO remove index
    public void updateUser(int index,User currentUser) {
        users.set(index,currentUser);
    }

    //TODO change this to Private
    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    private void removeUser(User user) {
        this.users.remove(user);
    }


    /**----------------------------------------CINEMAS----------------------------------------------*/

    //TODO change this to Private
    public void addCinemas(Set<String> cinemas) {
        Set<Cinema> cinemasToAdd = cinemas.stream()
                .map(Cinema::new)
                .collect(Collectors.toSet());
        this.cinemas.addAll(cinemasToAdd);
    }

    //TODO change this to Private
    public void removeCinema(String cinemaName) {
        cinemas.removeIf(x -> x.getName().equals(cinemaName));
    }

    public void addMoviesToCinemas(String cinemaName, HashMap<String, MovieGenre> movies) {
        Set<Movie> moviesToAdd = movies.entrySet()
                .stream()
                .map((x) -> new Movie(x.getKey(), x.getValue()))
                .collect(Collectors.toSet());

        Cinema cinema = getCinemaByName(cinemaName);
        cinema.addMovie(moviesToAdd);
    }

    public void removeMovieFromCinema(String cinemaName, String movieName) {
        Cinema cinema = getCinemaByName(cinemaName);
        cinema.removeMovie(movieName);
    }

    public void displayMoviesInCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);
        cinema.displayMovies();
        System.out.println();
    }

    public void removeProductsFromCinemaStore(String cinemaName,String foodName) {
        Cinema cinema = getCinemaByName(cinemaName);
        Product product = cinema.getCinemaStore().getProductByName(foodName);
        if(product == null) {
            System.out.println("There is no such product!");
        } else {
            this.cinemas.get(this.cinemas.indexOf(cinema)).removeProduct(product);
            System.out.println("Product " + product.getName() + " successfully removed!");
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
    /**----------------------------------------STORES----------------------------------------------*/

    //TODO change addStores to Private
    public void addStores(List<Store> stores) {
        this.stores.addAll(stores);
    }

    //TODO change getStoreByName() to private
    public Store getStoreByName(String storeName) {
        return stores.stream()
                .filter(st -> st.getName().equals(storeName))
                .findFirst()
                .get();
    }

    public void removeStore(String storeName) {
        this.stores.remove(getStoreByName(storeName));
    }

    public void addProductsToStore(String storeName, HashMap<Product,Integer> productsToAdd) {
        Store store = getStoreByName(storeName);
        store.addProducts(productsToAdd);
    }

    public void addProductsToCinemaStore(String cinemaName, HashMap<Product,Integer> productsToAdd) {
        Cinema cinema = getCinemaByName(cinemaName);

        cinema.updateCinemaStore(productsToAdd);
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

    public void removeProductsFromStore(String storeName,String foodName) {
        Store store = getStoreByName(storeName);
        store.removeProduct(store.getProductByName(foodName));
    }

    public void goShopping(String shopName, String productName, User currentUser) {
        Store shop = this.getStoreByName(shopName);
        Product product = shop.getProductByName(productName);

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
        System.out.printf("You successfully bought: %s and you have %.2f money left!", product, currentUser.getBudget());
    }

    /**----------------------------------------ATTRACTIONS----------------------------------------------*/

    public void addAttractions(HashMap<String, AttractionDangerLevel> attractions) {
        attractions.forEach((x,v) -> this.attractions.add(new Attraction(x, v)));
    }

    public void removeAttraction(String attractionName) {
        attractions.removeIf(x -> x.getName().equals(attractionName));
    }

    //TODO change displayAttractions()
    public void displayAttractions() {
        if (attractions.size() == 0) {
            System.out.println("Sorry the park does not have attractions yet.\n");
            return;
        }

        attractions.forEach(System.out::println);
        System.out.println();
    }

    /**-------------------------------Show Statistics functions- NOT FINISHED----------------------------------*/
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
     /**-------------------------------GetNames functions- FINISHED----------------------------------*/

    public List<String> getStoreAllProductsNames(String storeName) {
        Store store = getStoreByName(storeName);

        return store.getAllProductsNames();
    }

    public List<String> getAllCinemasNames() {
        return this.cinemas.stream()
                .map(Cinema::getName)
                .collect(Collectors.toList());
    }

    public List<String> getMoviesFromCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);

        return  cinema.getAllMoviesNames();
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
}