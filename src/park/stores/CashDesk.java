package park.stores;

import park.interfaces.AddMoney;

public class CashDesk implements AddMoney{
    private double moneyInDesk;

    public CashDesk(double money) {
        setMoneyInDesk(money);
    }

    public double getMoneyInDesk() {
        return moneyInDesk;
    }

    public void setMoneyInDesk(double moneyInDesk) {
        this.moneyInDesk = moneyInDesk;
    }

    public  void addMoney(double money) {
        this.moneyInDesk += money;
    }
}