package park.stores;
import java.util.HashSet;
import java.util.Set;

import park.products.*;

public class SouvenirStore extends Store {
    Set<Souvenir> souvenirsInStock;

    SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
        this.souvenirsInStock = new HashSet<>();
    }
}