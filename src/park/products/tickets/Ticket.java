package park.products.tickets;

import park.interfaces.IUsable;
import park.products.Product;

public class Ticket extends Product implements IUsable{
    private TicketType ticketType;
    private int ticketCredits;

    public Ticket(String name, double price, TicketType ticketType) {
        super(name, price);
        this.ticketType = ticketType;
        ticketCredits = 10;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getTicketCredits() {
        return ticketCredits;
    }

    @Override
    public void use() {
        this.ticketCredits --;
    }

    public void addCredits() {
        this.ticketCredits += 10;
    }

}
