package park.stores;

import park.interfaces.IConsumable;
import park.products.*;
import java.util.*;

public class FoodStore extends Store implements IConsumable {
    List<FoodProduct> productsInStock;

    public FoodStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new ArrayList<FoodProduct>();
    }

    public FoodStore(String name, CashDesk desk, List<FoodProduct> products) {
        super(name, desk);
        this.productsInStock.addAll(products);
    }

    public void addProducts(List<FoodProduct> products) {
        this.productsInStock.addAll(products);
    }

    @Override
    public void consume() {

    }
}