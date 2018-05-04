package park.stores;
import park.products.FoodProduct;
import park.products.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Store {
    private String name;
    private CashDesk desk;
    protected HashMap<Product,Integer> productsInStock;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
        productsInStock = new HashMap<>();
    }

    Store(String name,CashDesk desk, HashMap<Product,Integer> productsToAdd) {
        this(name,desk);
        productsInStock.putAll(productsToAdd);
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getName() {
        return this.name;
    }

    // TODO test if it's working
    // TODO not finished
    public void showProductsInStock() {
        if (productsInStock.size() < 1) {
            System.out.println("There is no products in store " + this.name);
        } else {
           productsInStock
                   .forEach((product,quantity) ->
                           System.out.println("Product: " + product.getName() + " Quantity: " + quantity));
        }
    }

    public void addProducts(HashMap<Product, Integer> productsToAdd) {
        this.productsInStock.putAll(productsToAdd);
    }

    public Product getProductByName(String foodName) {
        List<Product> product = this.productsInStock
                .entrySet()
                .stream()
                .filter(p -> p.getKey().getName().equals(foodName))
                .map(p -> p.getKey())
                .collect(Collectors.toList());

        if (product.size() < 1) {
            return null;
        } else {
            return product.get(0);
        }
    }

    public void removeProduct(Product productToRemove) {
        this.productsInStock.remove(productToRemove);
    };

    //TODO complete toString method
    @Override
    public String toString() {
        return String.format("Store " + this.name + "Budget %.2f ",this.desk );
    }

}