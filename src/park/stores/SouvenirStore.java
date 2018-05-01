package park.stores;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import park.interfaces.IUsable;
import park.products.*;

public class SouvenirStore extends Store implements IUsable {
    List<Souvenir> productsInStock;

    public SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
        this.productsInStock = new ArrayList<Souvenir>();
    }

    public SouvenirStore(String name, CashDesk desk, List<Souvenir> souvenirs) {
        super(name,desk);
        this.productsInStock.addAll(souvenirs);
    }
    //TODO getProductsInStock
    //TODO toString productsInStock


    public void addProducts(List<Souvenir> souvenirs) {
        this.productsInStock.addAll(souvenirs);
    }

    @Override
    public void use(int creditsCost) {

    }
}