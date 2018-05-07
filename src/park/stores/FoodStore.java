package park.stores;

import park.interfaces.IConsumable;
import park.products.*;

import java.util.*;

public class FoodStore extends Store {

    public FoodStore(String name, CashDesk desk) {
        super(name, desk);
    }

    public FoodStore(String name, CashDesk desk, HashMap<Product, Integer> products) {
        super(name, desk, products);
    }


    @Override
    public String toString() {
        return String.format(super.toString() + "\t|\tSells: foods");
    }

    @Override
    public void showStatistic() {
        System.out.printf("\t\tFood store %10s\n", getName().toUpperCase());
        showProductsInStock();
        System.out.println();
    }
}