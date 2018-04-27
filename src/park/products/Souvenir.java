package park.products;

import interfaces.IUsable;

public class Souvenir extends Product implements IUsable{
    public Souvenir(String name, double price) {
        super(name, price);
    }

    @Override
    public void use() {
        System.out.printf("%s was used.\n", this);
    }
}
