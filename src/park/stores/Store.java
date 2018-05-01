package park.stores;
import park.products.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class Store {
    private String name;
    private CashDesk desk;
    private List<Product> productsInStock;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
        this.productsInStock = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public  String getName() {
        return this.name;
    }


    // TODO test if it's working
    public String getProductsInStock() {
        return "Store -> getProductsInStock - NOT COMPLETED!!!!11";
    }

    //TODO complete toString method
    @Override
    public String toString() {
        return String.format(this.name + " ");
    }
}