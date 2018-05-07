package park.stores;

public class SouvenirStore extends Store{
    public SouvenirStore(String name, CashDesk desk) {
        super(name, desk);
    }

    public String toString() {
        return String.format(super.toString() + "\t|\tSells: souvenirs");
    }

    @Override
    public void showStatistic() {
        System.out.printf("Souvenir store %s\n\n", toString());
        showProductsInStock();
        System.out.println();
    }
}