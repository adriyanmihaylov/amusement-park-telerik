package park.stores;

import park.products.*;
import java.util.*;

public class FoodStore extends Store {
    Set<FoodProduct> productsInStock;

    FoodStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new HashSet<>();
    }

    FoodStore(String name, CashDesk desk, Set<FoodProduct> products) {
        super(name, desk);
        this.productsInStock.addAll(products);
    }
}