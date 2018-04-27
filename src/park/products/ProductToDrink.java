package park.products;

import interfaces.IConsumable;

public class ProductToDrink extends FoodProduct {
    public ProductToDrink(String name, double price, String expirationDate) {
        super(name, price, expirationDate);
    }

    @Override
    public void consume() {
        System.out.printf("%s was drank.\n", this);
    }
}
