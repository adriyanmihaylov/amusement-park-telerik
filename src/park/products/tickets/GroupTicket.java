package park.products.tickets;

public class GroupTicket extends Ticket{

    public GroupTicket(String name, double price, TicketType ticketType) {
        super(name, price, ticketType);
    }

    @Override
    public void use() {

    }
}
