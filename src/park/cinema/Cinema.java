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
    private EnumMap<MovieGenre,Integer> moviesAgeRestrictions;
    private FoodStore cinemaStore;


    public Cinema(String name) {
        this.name = name;
        this.desk = new CashDesk(500);
        this.movies = new HashSet<>();
        setMoviesAgeRestrictions();
        setCinemaStore();
    }

    public String getName() {
        return name;
    }

    public CashDesk getDesk() {
        return desk;
    }

    private void setCinemaStore() {
        String name = this.name + " store";
        cinemaStore = new FoodStore(name, new CashDesk(0));
    }

    public FoodStore getCinemaStore() {
        return this.cinemaStore;
    }

    private void setMoviesAgeRestrictions() {
        moviesAgeRestrictions = new EnumMap<>(MovieGenre.class);
        moviesAgeRestrictions.put(MovieGenre.COMEDY, 0);
        moviesAgeRestrictions.put(MovieGenre.ANIMATION, 0);
        moviesAgeRestrictions.put(MovieGenre.MUSICAL, 0);
        moviesAgeRestrictions.put(MovieGenre.ACTION, 13);
        moviesAgeRestrictions.put(MovieGenre.DRAMA, 15);
        moviesAgeRestrictions.put(MovieGenre.THRILLER, 17);
    }

    public int getMovieAgeRestriction(Movie movie) {
        return moviesAgeRestrictions.get(movie.getGenre());
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

        System.out.printf("Movies in cinema %-15s\n",this.getName());
        movies.forEach(x -> System.out.println(x + " "));
        System.out.println();
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
