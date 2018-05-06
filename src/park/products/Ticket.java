package park.products;

import park.interfaces.IUsable;

public class Ticket extends Product implements IUsable {
    private int ticketCredits;

    public Ticket(String ticketNumber, double ticketPrice) {
        super(ticketNumber, ticketPrice);
        this.ticketCredits = 10;
    }

    public String getTicketNumber() {
        return this.getName();
    }

    public double getTicketPrice() {
        return this.getPrice();
    }

    public int getTicketCredits() {
        return ticketCredits;
    }

    public void addCredits(int numberOfTickets) {
        this.ticketCredits += numberOfTickets * 10;
    }

    public boolean hasCredits() {
        return ticketCredits > 0;
    }

    @Override
    public void use() {
        this.ticketCredits -= 1;
    }

}
