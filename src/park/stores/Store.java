package park.stores;

import park.interfaces.AddMoney;
import park.interfaces.Statistic;
import park.products.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Store implements Statistic, AddMoney {
    private String name;
    private CashDesk desk;
    private HashMap<Product, Integer> productsInStock;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
        productsInStock = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void showProductsInStock() {
        if (productsInStock.size() < 1) {
            System.out.println("There are no products in store " + this.name + "!");
        } else {
            productsInStock
                    .forEach((product, quantity) ->
                            System.out.println(product.toString() + " Quantity: " + quantity));
        }
    }

    public void addProducts(HashMap<Product, Integer> productsToAdd) {
        for (Map.Entry<Product, Integer> entry : productsToAdd.entrySet()) {
            int quantity = entry.getValue();

            if(productsInStock.containsKey(entry.getKey())) {
                quantity += productsInStock.get(entry.getKey());
                System.out.printf("Product \"%s\" has been updated! Quantity now is %d\n", entry.getKey().getName(),quantity);
            }
            productsInStock.put(entry.getKey(),quantity);
        }
    }

    public List<String> getAllProductsNames() {
        return this.productsInStock.entrySet()
                .stream()
                .map(product -> product.getKey().getName())
                .collect(Collectors.toList());

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

    public void addMoney(double productPrice) {
        this.desk.addMoney(productPrice);
    }

    public void removeProduct(Product productToRemove) {
        this.productsInStock.remove(productToRemove);
    }

    public void removeOneProduct(Product productToRemove) {
        Integer productQuantity = productsInStock.get(productToRemove);

        productQuantity = productQuantity - 1;

        if (productQuantity == 0) {
            removeProduct(productToRemove);
            return;
        }

        productsInStock.put(productToRemove, productQuantity);
    }

    @Override
    public String toString() {
        return String.format("\nStore: %-10s|\tBudget: %-10.2f", this.name, this.desk.getMoneyInDesk());
    }
}