package park.products;

import park.interfaces.IConsumable;

import java.util.List;

public class ProductToEat extends FoodProduct {
    public ProductToEat(String name, double price, String exparationDate) {
        super(name, price, exparationDate);
    }

    @Override
    public void consume() {
        System.out.printf("%s was eaten.\n", this);
    }

}
