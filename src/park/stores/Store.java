package park.stores;

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

    @Override
    public String toString() {
        return String.format("Store " + this.name + "\tMoney in cash desk = %.2f" + this.desk.getMoneyInDesk());
    }
}