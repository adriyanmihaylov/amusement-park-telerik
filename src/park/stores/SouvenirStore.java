package park.stores;
import java.util.*;

import park.interfaces.IUsable;
import park.products.*;

public class SouvenirStore extends Store implements IUsable {
    HashMap<Souvenir,Integer> productsInStock;

    public SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new HashMap<>();
    }

    public SouvenirStore(String name, CashDesk desk, HashMap<Souvenir,Integer> souvenirs) {
        super(name, desk);
        this.productsInStock.putAll(souvenirs);
    }
    //TODO getProductsInStock
    //TODO toString productsInStock

    @Override
    public void use() {

    }

    @Override
    public String toString() {
        return String.format(super.getName() + "\t| sells souvenirs");
    }
}