package park.stores;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import park.products.*;

public class SouvenirStore extends Store {
    Set<Souvenir> souvenirsInStock;

    public SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
        this.souvenirsInStock = new HashSet<>();
    }

    public SouvenirStore(String name, CashDesk desk, List<Souvenir> souvenirs) {
        super(name,desk);
        this.souvenirsInStock.addAll(souvenirs);
    }
    //TODO getSouvenirsInStock
    //TODO toString souvenirsInStock
    public String getSouvenirsInStock() {
        return String.join(souvenirsInStock.toString());
    }
}