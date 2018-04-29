package park.products;

import park.interfaces.IConsumable;

public abstract class FoodProduct extends Product implements IConsumable {

    String expirationDate;

    public FoodProduct(String name, double price, String expirationDate) {
        super(name, price);
        this.expirationDate = expirationDate;
    }

    public String getExparationDate() {
        return expirationDate;
    }
}
