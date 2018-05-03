package park.cinema;

import park.products.*;
import park.stores.CashDesk;
import park.users.User;

import java.util.*;

public class Cinema {
    private String name;
    private CashDesk desk;
    private Map<Product, Integer> productsInStock;
    private Set<Movie> movies;


    public Cinema(String name) {
        this.name = name;
        this.desk = new CashDesk(500);
        this.productsInStock = new HashMap<>();
        this.movies = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public CashDesk getDesk() {
        return desk;
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

    //TODO make some restrictions here; add money to cashDesk; check user ticket
    public void watchMovie(User user, Movie movie) {
        System.out.println(user.getName() + "is watching " + movie.getName());
    }

    //TODO !!!!! FINISH THIS LATER
    public void addProducts(HashMap<Product, Integer> products) {

    }

    public void addSingleProduct(Product product) {
        boolean isContained = isFoodContained(productsInStock, product);
        if (isContained) {
            productsInStock.computeIfPresent(product, (k, v) -> v + 1); //TODO : test this
        } else {
            productsInStock.put(product, 0);
        }
    }

    private boolean isFoodContained(Map<Product, Integer> productsInStock, Product product) {
        return productsInStock.containsKey(product);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
