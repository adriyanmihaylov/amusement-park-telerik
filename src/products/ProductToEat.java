package products;

import interfaces.IConsumable;

public class ProductToEat extends FoodProduct implements IConsumable{
    public ProductToEat(String name, double price, String exparationDate) {
        super(name, price, exparationDate);
    }

    @Override
    public void consume() {
        System.out.printf("%s was eaten.\n", this);
    }
}
