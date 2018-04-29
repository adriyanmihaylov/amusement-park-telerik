package park.products.tickets;

public class SingleTicket extends Ticket{

    public SingleTicket(String name, double price, TicketType ticketType) {
        super(name, price, ticketType);
    }

    @Override
    public void use() {

    }
}
