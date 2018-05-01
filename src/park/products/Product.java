package park.products;

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
        return String.format("Product %s\tPrice %.2f\t", name, price);
    }
}