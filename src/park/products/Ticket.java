package park.products;

import park.interfaces.IUsable;
import park.products.Product;
import park.users.UserTypeOfTicket;

public class Ticket extends Product implements IUsable{
    private UserTypeOfTicket userTypeOfTicket;
    private int ticketCredits;

    public Ticket(String name, double price, UserTypeOfTicket userTypeOfTicket) {
        super(name, price);
        this.userTypeOfTicket = userTypeOfTicket;
        ticketCredits = 10;
    }

    public UserTypeOfTicket getUserTypeOfTicket() {
        return userTypeOfTicket;
    }

    public int getTicketCredits() {
        return ticketCredits;
    }

    public void addCredits(int numberOfTickets) {
        this.ticketCredits += 10;
    }


    @Override
    public void use(int creditsCost) {
        this.ticketCredits -= creditsCost;
    }

}
