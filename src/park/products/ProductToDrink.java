package park.products;

public class ProductToDrink extends FoodProduct {
    public ProductToDrink(String name, double price, String expirationDate) {
        super(name, price, expirationDate);
    }

    private String consumeProductString() {
        return String.format("Product %s with the price of: %.2f and expiration date: %s", this.getName(), this.getPrice(), this.getExpirationDate());
    }

    @Override
    public void consume() {
        System.out.printf("%10s was drank.\n", this.consumeProductString());
    }

    @Override
    public String toString() {
        return String.format("%20s\ttype: drink\t|", super.toString());
    }

}
