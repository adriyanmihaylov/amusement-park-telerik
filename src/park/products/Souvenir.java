package park.products;

import park.interfaces.IUsable;

public class Souvenir extends Product implements IUsable{
    public Souvenir(String name, double price) {
        super(name, price);
    }

    private String useProductString() {
        return String.format("Product %s with the price of: %.2f", this.getName(),  this.getPrice());
    }

    @Override
    public void use() {
        System.out.printf("%s was used.\n", this.useProductString());
    }
}
