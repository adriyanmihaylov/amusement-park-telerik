package park.products;

import park.interfaces.IUsable;

public class Ticket extends Product implements IUsable{
    private int ticketCredits;

    public Ticket(String ticketNumber, double ticketPrice) {
        super(ticketNumber,ticketPrice);
        this.ticketCredits = 10;
    }
    public String getTicketNumber() {
        return this.name;
    }
    public double getTicketPrice() {
        return this.price;
    }
    public int getTicketCredits() {
        return ticketCredits;
    }

    public void addCredits(int numberOfTickets) {
        this.ticketCredits += numberOfTickets * 10;
    }

    @Override
    public void use(int creditsCost) {
        this.ticketCredits -= creditsCost;
    }

}
