package park.products.tickets;

import park.interfaces.IUsable;
import park.products.Product;

public abstract class Ticket extends Product implements IUsable{
    private TicketType ticketType;

    public Ticket(String name, double price, TicketType ticketType) {
        super(name, price);
        this.ticketType = ticketType;
    }

    public TicketType getTicketType() {
        return ticketType;
    }
}
