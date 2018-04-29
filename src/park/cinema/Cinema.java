package park.cinema;

import park.products.*;
import park.stores.CashDesk;

import java.util.*;

public class Cinema {
    private String name;
    private CashDesk desk;
    private Map<Product, Integer> productsInStock;
    private Map<String, Double> movies;


    public Cinema(String name, CashDesk desk) {
        this.name = name;
        this.desk = desk;
        this.productsInStock = new HashMap<>();
        this.movies = new HashMap<>();
    }

    public Cinema(String name,CashDesk desk,Map<Product,Integer> productsInStock) {
        this(name,desk);
        this.productsInStock.putAll(productsInStock);
    }

    public void watchMovie(String userName, int userAge, String movieName) {
        //TODO : add checks
        //TODO : desk.setMoney() according to the ticket price for the given movieName;
    }

    public void addProduct(Product product) {
        boolean isContained = isFoodContained(productsInStock, product);
        if (isContained) {
            productsInStock.computeIfPresent(product, (k, v) -> v + 1); //TODO : test this
        } else {
            productsInStock.put(product, 0);
        }
    }

    public void addMovie(String movieName, double ticketPrice) {
        //or new Ticket()
        movies.put(movieName, ticketPrice);
    }

    public void displayMovies() {
        System.out.println("Movies you can watch:\n");
        movies.forEach((x, v) -> System.out.print(x + ", "));
    }

    public String getName() {
        return name;
    }

    public CashDesk getDesk() {
        return desk;
    }

    public Map getMovies() {
        return movies;
    }

    private boolean isFoodContained(Map<Product, Integer> productsInStock, Product product) {
        return productsInStock.containsKey(product);
    }
}
