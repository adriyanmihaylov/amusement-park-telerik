package park.stores;

import park.interfaces.IConsumable;
import park.products.*;
import java.util.*;

public class FoodStore extends Store implements IConsumable {
    HashMap<FoodProduct,Integer> productsInStock;

    public FoodStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new HashMap<>();
    }

    public FoodStore(String name, CashDesk desk, HashMap<FoodProduct,Integer> products) {
        super(name, desk);
        this.productsInStock.putAll(products);
    }

    @Override
    public void consume() {

    }

    @Override
    public String toString() {
        return String.format(super.getName() + "\t| sells foods");
    }

}