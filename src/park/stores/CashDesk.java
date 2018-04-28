package park.stores;

public class CashDesk {
    private double moneyInDesk;

    CashDesk(double money) {
        setMoneyInDesk(money);
    }

    public double getMoneyInDesk() {
        return moneyInDesk;
    }

    public void setMoneyInDesk(double moneyInDesk) {
        this.moneyInDesk = moneyInDesk;
    }
}