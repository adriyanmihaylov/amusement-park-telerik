package park.products;

import park.interfaces.IConsumable;

public abstract class FoodProduct extends Product implements IConsumable {

    String exparationDate;

    public FoodProduct(String name, double price, String expirationDate) {
        super(name, price);
        this.exparationDate = expirationDate;
    }

    public String getExparationDate() {
        return exparationDate;
    }
}
