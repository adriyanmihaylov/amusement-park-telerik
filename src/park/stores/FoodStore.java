package park.stores;

import park.products.*;
import java.util.*;

public class FoodStore extends Store {
    Set<FoodProduct> productsInStock;

    public FoodStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new HashSet<>();
    }

    public FoodStore(String name, CashDesk desk, List<FoodProduct> products) {
        super(name, desk);
        this.productsInStock.addAll(products);
    }

    public String getProductsInStock() {
        return String.join(" ",productsInStock.toString());
    }
}