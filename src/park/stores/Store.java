package park.stores;
import park.products.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class Store {
    private String name;
    private CashDesk desk;
    private List<Product> productsInStock;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
        this.productsInStock = new ArrayList<>();
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
        System.out.println("STORE -> showProductsInStock() NOT FINISHED!");

        if (productsInStock.size() < 1) {
            System.out.println("There is no products in store " + this.name);
        } else {
            productsInStock.stream().map(Product::toString).forEach(System.out::println);
        }
    }


    public void addProducts(List<Product> productsToAdd) {
        this.productsInStock.addAll(productsToAdd);
    }

    //TODO complete toString method
    @Override
    public String toString() {
        return String.format("Store " + this.name + "Budget %.2f ",this.desk );
    }
}