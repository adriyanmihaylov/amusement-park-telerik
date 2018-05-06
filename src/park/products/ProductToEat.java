package park.products;

import park.interfaces.IConsumable;

import java.util.List;

public class ProductToEat extends FoodProduct {
    public ProductToEat(String name, double price, String expirationDate) {
        super(name, price, expirationDate);
    }

    private String consumeProductString() {
        return String.format("Product %s with the price of: %.2f and expiration date: %s", this.getName(), this.getPrice(), this.getExparationDate());
    }

    @Override
    public void consume() {
        System.out.printf("%s was eaten.\n", this.consumeProductString());
    }

    @Override
    public String toString() {
        return String.format("%s\ttype: food", super.toString());
    }

}
