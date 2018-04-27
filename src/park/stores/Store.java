package stores;

public abstract class Store {
    private String name;
    private CashDesk desk;

    Store(String name, CashDesk desk) {
        setName(name);
        this.desk = desk;
    }

    public void setName(String name) {
        this.name = name;
    }
}