package park.cinema;

import park.products.*;
import park.stores.CashDesk;
import park.stores.FoodStore;
import park.users.User;

import java.util.*;
import java.util.stream.Collectors;

public class Cinema {
    private String name;
    private CashDesk desk;
    private Set<Movie> movies;
    private FoodStore cinemaStore;


    public Cinema(String name) {
        this.name = name;
        this.desk = new CashDesk(500);
        this.movies = new HashSet<>();
        setCinemaStore();
    }

    public String getName() {
        return name;
    }

    public CashDesk getDesk() {
        return desk;
    }

    private void setCinemaStore() {
        String name = "'" + this.name + " cinema store'";
        cinemaStore = new FoodStore(name, new CashDesk(0));
    }

    public FoodStore getCinemaStore() {
        return this.cinemaStore;
    }

    public void updateCinemaStore(HashMap<Product, Integer> foodsToAdd) {
        this.cinemaStore.addProducts(foodsToAdd);
    }

    public void addMovie(Set<Movie> moviesToAdd) {
        movies.addAll(moviesToAdd);
    }

    public void removeMovie(String movieName) {
        movies.removeIf(x -> x.getName().equals(movieName));
        System.out.println("Done!\n");
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void displayMovies() {
        if (movies.size() == 0) {
            System.out.println("Sorry, there are no movies in the cinema.");
            return;
        }

        System.out.println("Movies you can watch:");
        movies.forEach(x -> System.out.print(x + " "));
        System.out.println();
    }

    public void watchMovie(User user, Movie movie) {
        boolean notAllowed = isMovieAllowed(user, movie);
        if (notAllowed) {
            System.out.printf("Sorry \"%s\" is not allowed for users at this age.\n", movie.getName());
            return;
        }

        if (user.hasFoodProducts()) {
            System.out.println("Sorry you can't ride the attraction before you consume the food products you have!");
            return;
        }

        Ticket userTicket = user.getUserTicket();

        if (userTicket.hasCredits()) {
            System.out.println(user.getName() + " is watching " + movie.getName());
            userTicket.use();
        } else {
            System.out.println("Sorry you don't have enough credits!");
        }
    }

    //TODO fix
    //a 16 year old can watch a thriller but cant watch animation
    private boolean isMovieAllowed(User user, Movie movie) {
        int userAge = user.getAge();
        MovieGenre genre = movie.getGenre();

        return (userAge >= 10 || genre == MovieGenre.ANIMATION) &&
                (userAge >= 18 || genre != MovieGenre.THRILLER);
    }


    public void addProductsToStore(HashMap<Product, Integer> productsToAdd) {
        this.cinemaStore.addProducts(productsToAdd);
    }

    public void removeProduct(Product product) {
        this.cinemaStore.removeProduct(product);
    }

    private boolean isFoodContained(Map<Product, Integer> productsInStock, Product product) {
        return productsInStock.containsKey(product);
    }

    public List<String> getAllMoviesNames() {
        return this.movies.stream()
                .map(Movie::getName)
                .collect(Collectors.toList());
    }

    public Movie getMovieByName(String movieName) {
        Movie wantedMovie;

        for (Movie currentMovie : movies) {
            if (currentMovie.getName().equals(movieName)) {
                return currentMovie;
            }
        }

        return null;
    }

    public List<String> getProductsNamesInCinema() {
        return this.cinemaStore.getAllProductsNames();
    }

    public void displayCinemaProducts() {
        System.out.println("Products in cinema:");
        cinemaStore.showProductsInStock();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
