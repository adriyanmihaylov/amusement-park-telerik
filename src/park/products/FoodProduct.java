package park.products;

import park.interfaces.IConsumable;

public abstract class FoodProduct extends Product implements IConsumable {
    private String expirationDate;

    public FoodProduct(String name, double price, String expirationDate) {
        super(name, price);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return String.format("%25s\texpiration date: %10s\t|", super.toString(), this.expirationDate);
    }
}