package stores;

import products.Souvenirs;

import java.util.HashSet;
import java.util.Set;

public class SouvenirStore extends Store {
    Set<Souvenirs> souvenirsInStock;

    SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
        this.souvenirsInStock = new HashSet<>();
    }
}