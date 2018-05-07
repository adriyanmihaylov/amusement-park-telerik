package park.products;

public class ProductToEat extends FoodProduct {
    public ProductToEat(String name, double price, String expirationDate) {
        super(name, price, expirationDate);
    }

    private String consumeProductString() {
        return String.format("Product %s with the price of: %.2f and expiration date: %s", this.getName(), this.getPrice(), this.getExpirationDate());
    }

    @Override
    public void consume() {
        System.out.printf("%s was eaten.\n", this.consumeProductString());
    }

    @Override
    public String toString() {
        return String.format("%s\ttype: food\t|", super.toString());
    }

}
