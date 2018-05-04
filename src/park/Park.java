package  park;

import park.cinema.Cinema;
import park.cinema.Movie;
import park.cinema.MovieGenre;
import park.funzone.Attraction;
import park.funzone.AttractionDangerLevel;
import park.products.FoodProduct;
import park.products.Product;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public Set<Attraction> getAttractions() {
        return attractions;
    }

    public int getTicketsCounter() {
        return this.ticketsCounter;
    }

    public String setTicketsCounter() {
        this.ticketsCounter++;
        return "";
    }

    public void printAllStores() {
        stores.forEach(System.out::println);
    }

    public int getStoreIndex(String name) {
        return stores.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .map(x -> stores.indexOf(x))
                .orElse(-1);
    }

    public  Store getStoreByIndex(int index) {
        return this.stores.get(index);
    }

    public int findUserIndex(String name,String ticketNumber) {
        return users.stream()
                .filter(x -> x.getName().equals(name))
                .filter(x -> x.getTicketNumber(ticketNumber))
                .findFirst()
                .map(x -> users.indexOf(x))
                .orElse(-1);
    }

    public User getUserByIndex(int userIndex) {
        return this.users.get(userIndex);
    }

    public void addStores(List<Store> stores) {
        this.stores.addAll(stores);
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    public void addAttractions(HashMap<String, AttractionDangerLevel> attractions) {
        attractions.forEach((x,v) -> this.attractions.add(new Attraction(x, v)));
    }

    public void removeAttraction(String attractionName) {
        attractions.removeIf(x -> x.getName().equals(attractionName));
    }

    public void displayAttractions() {
        if (attractions.size() == 0) {
            System.out.println("Sorry the park does not have attractions yet.\n");
            return;
        }

        attractions.forEach(System.out::println);
        System.out.println();
    }

    public void addCinemas(Set<String> cinemas) {
        Set<Cinema> cinemasToAdd = cinemas.stream()
                .map(Cinema::new)
                .collect(Collectors.toSet());
        this.cinemas.addAll(cinemasToAdd);
    }

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

    public Set<Movie> getMoviesFromCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);
        return cinema.getMovies();
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

    public Cinema getCinemaByName(String cinemaName) {
        return cinemas.stream()
                .filter(x -> x.getName().equals(cinemaName))
                .findFirst()
                .get();
    }

    public void removeStore(Store store) {
        this.stores.remove(store);
    }

    public  void removeUser(User user) {
        this.users.remove(user);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updateUser(int index,User currentUser) {
        users.set(index,currentUser);
    }

    public void addProductsToStore(Store store,HashMap<Product,Integer> productsToAdd) {
        int index = this.stores.indexOf(store);

        this.stores.get(index).addProducts(productsToAdd);
    }

    public void addProductsToCinemaStore(String cinemaName, HashMap<Product,Integer> productsToAdd) {
        Cinema cinema = getCinemaByName(cinemaName);
        int index = this.cinemas.indexOf(cinema);

        this.cinemas.get(index).updateCinemaStore(productsToAdd);
    }


    public void showStoreCinemaProducts(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);

        cinema.getCinemaStore().showProductsInStock();
    }

    public boolean isThereStore(String name) {
        return stores.stream()
                .anyMatch(x -> x.getName().equals(name));
    }

    public void removeProductsFromStore(Store store,String foodName) {
        int index = this.stores.indexOf(store);
        Product product = this.stores.get(index).getProductByName(foodName);
        if (product == null) {
            System.out.println("There is no such product!");
        } else {
            this.stores.get(index).removeProduct(product);
            System.out.println("Product " + product.getName() + " successfully removed!");
        }
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

    public void showUserStatistics() {
        if (users.size() == 0) {
            System.out.println("Sorry there are no users in the park yet.");
            return;
        }

        System.out.println("\n----------USER STATISTICS ----------\n");
        System.out.println("Currently the park has " + users.size() + " users.");

        //print the stats
        users.forEach(x -> System.out.println());
    }

    public void showAttractionStatistics() {
        if (attractions.size() == 0) {
            System.out.println("Sorry there are no attractions in the park yet.");
            return;
        }

        System.out.println("---------- ATTRACTION STATISTICS ----------");
    }

    public void showCinemaStatistics() {
        if (cinemas.size() == 0) {
            System.out.println("Sorry there are no cinemas in the park yet.");
            return;
        }

        System.out.println("---------- CINEMA STATISTICS ----------");
    }

    public void showStoreStatistics() {
        if (stores.size() == 0) {
            System.out.println("Sorry there are no stores in the park yet.");
            return;
        }

        System.out.println("---------- STORE STATISTICS----------");
    }
}