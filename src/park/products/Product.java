package products;

public abstract class Product {
    String name;
    double price;

    public Product (String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s, with the price of: %.2f", name, price);
    }
}
