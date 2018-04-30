package park.products;

import park.interfaces.IUsable;

public class Ticket extends Product implements IUsable{
    private String ticketNumber;
    private double ticketPrice;
    private int ticketCredits;

    public Ticket(String ticketNumber, double ticketPrice) {
        super(ticketNumber,ticketPrice);
        this.ticketCredits = 10;
    }
    public String getTicketNumber() {
        return this.ticketNumber;
    }
    public double getTicketPrice() {
        return this.ticketPrice;
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
