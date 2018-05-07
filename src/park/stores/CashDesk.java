package park.stores;
import park.interfaces.AddMoney;

public class CashDesk implements AddMoney {
    private double moneyInDesk;

    public CashDesk(double money) {
        setMoneyInDesk(money);
    }

    double getMoneyInDesk() {
        return moneyInDesk;
    }

    private void setMoneyInDesk(double moneyInDesk) {
        this.moneyInDesk = moneyInDesk;
    }

    public void addMoney(double money) {
        if (money > 0) {
            this.moneyInDesk += money;
        }
    }
}