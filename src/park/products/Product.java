package park.products;

public abstract class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Product getProductByName(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("|\tProduct: %s\t|\tprice: %.2f\t|", name, price);
    }
}