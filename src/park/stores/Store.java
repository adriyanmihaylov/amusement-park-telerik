package park.stores;
import park.products.FoodProduct;
import park.products.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Store {
    private String name;
    private CashDesk desk;
    private HashMap<Product,Integer> productsInStock;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
        productsInStock = new HashMap<>();
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


    public void addProducts(HashMap<FoodProduct, Integer> productsToAdd) {
        this.productsInStock.putAll(productsToAdd);
    }

    //TODO complete toString method
    @Override
    public String toString() {
        return String.format("Store " + this.name + "Budget %.2f ",this.desk );
    }
}