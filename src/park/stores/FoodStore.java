package park.stores;

public class FoodStore extends Store {

    public FoodStore(String name, CashDesk desk) {
        super(name, desk);
    }

    public String toString() {
        return String.format(super.toString() + "\t|\tSells: foods");
    }

    @Override
    public void showStatistic() {
        System.out.printf("\t\tFood store %s\n\n", toString());
        showProductsInStock();
        System.out.println();
    }
}