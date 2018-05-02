package  park;

import park.cinema.Cinema;
import park.cinema.Movie;
import park.cinema.MovieGenre;
import park.funzone.Attraction;
import park.stores.Store;
import park.users.User;

import java.util.*;
import java.util.stream.Collectors;

public class Park {
    private String name;
    protected String password;
    private List<Store> stores;
    private List<User> users;
    private Set<Cinema> cinemas;
    private Set<Attraction> attractions;
    private int ticketsCounter;

    public Park(String name,String password) {
        setName(name);
        this.password = password;
        this.stores = new ArrayList<>();
        this.users = new ArrayList<>();
        this.cinemas = new HashSet<>();
        this.attractions = new HashSet<>();
        this.ticketsCounter = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Cinema> getCinemas() {
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
        stores
                .stream()
                .map(Store::toString)
                .forEach(System.out::println);

    }

    //TODO make it STREAM()
    public int getStoreIndex(String name) {
        for (Store store : this.stores) {
            if (store.getName().equals(name)) {
                return this.stores.indexOf(store);
            }
        }
        return -1;
    }

    public  Store getStoreByIndex(int index) {
        return this.stores.get(index);
    }

    //TODO make a stream()
    public int findUserIndex(String name,String ticketNumber) {
        if(this.users.size() < 1) {
            return -1;
        }
        for (User user: users) {
            if(user.getName().equals(name) && user.getTicketNumber(ticketNumber)) {
                return this.users.indexOf(user);
            }
        }
       return -1;
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

    public void addAttractions(List<Attraction> attractions) {
        this.attractions.addAll(attractions);
    }

    public void addCinemas(HashSet<String> cinemas) {
        Set<Cinema> cinemasToAdd = cinemas.stream()
                .map(Cinema::new)
                .collect(Collectors.toSet());
        this.cinemas.addAll(cinemasToAdd);
    }

    public void removeCinema(String cinemaName) {
        cinemas.removeIf(x -> x.getName().equals(cinemaName));
    }

    //TODO test this
    public void addMoviesToCinemas(String cinemaName, HashMap<String, String> movies) {
        Set<Movie> moviesToAdd = movies.entrySet()
                .stream()
                .map((x) -> new Movie(x.getKey(), MovieGenre.valueOf(x.getValue().toUpperCase())))
                .collect(Collectors.toSet());

        cinemas.stream()
                .filter(x -> x.getName().equals(cinemaName))
                .forEach(x -> x.addMovie(moviesToAdd));
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

    public Set<Movie> getMoviesFromCinema(String cinemaName) {
        Cinema cinema = getCinemaByName(cinemaName);
        return cinema.getMovies();
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

    public void removeAttraction(Attraction attraction) {
        this.attractions.remove(attraction);
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

    public void addProductsToStore(Store store) {

    }

    public boolean isThereStore(String name) {
        for (Store store: this.stores) {
            if(store.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeProductsFromStore(Store store) {

    }
}