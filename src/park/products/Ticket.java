package park.products;

import park.interfaces.IUsable;
import park.products.Product;
import park.users.UserTypeOfTicket;

public class Ticket implements IUsable{
    private int ticketNumber;
    private double ticketPrice;
    private int ticketCredits;

    public Ticket(int ticketNumber, double ticketPrice) {
        this.ticketNumber = ticketNumber;
        this.ticketPrice = ticketPrice;
        this.ticketCredits = 10;
    }

    public int getTicketCredits() {
        return ticketCredits;
    }

    public int getTicketNumber() {
        return this.ticketNumber;
    }

    public void addCredits(int numberOfTickets) {
        this.ticketCredits += 10;
    }

    @Override
    public void use(int creditsCost) {
        this.ticketCredits -= creditsCost;
    }

}
