package park.stores;

import java.util.*;

import park.interfaces.IUsable;
import park.products.*;

public class SouvenirStore extends Store implements IUsable {
    public SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
    }

    public SouvenirStore(String name, CashDesk desk, HashMap<Product, Integer> souvenirs) {
        super(name, desk, souvenirs);
    }
    //TODO getProductsInStock
    //TODO toString productsInStock

    @Override
    public void use() {

    }

    @Override
    public String toString() {
        return String.format(super.toString() + "\t|\tSells: souvenirs");
    }

}