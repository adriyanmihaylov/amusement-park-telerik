package park.users;

import park.products.Ticket;

public class User {
    private String name;
    private int age;
    private double budget;
    private UserTypeOfTicket type;
    private Ticket ticket;

    public User(String name, int age, double budget, UserTypeOfTicket type) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBudget() {
        return budget;
    }

    public boolean getTicketNumber(String ticketNumber) {
        return this.ticket.getTicketNumber().equals(ticketNumber);
    }
    public double getTicketPrice() {
        return this.ticket.getTicketPrice();
    }
    public int getUserTicketCredits() {
        return ticket.getTicketCredits();
    }
        // UNDER 18 ,PENSIONER, DISABLED - price 15$
       // SMALLGROUP - price = 14$
        // BIGGROUP - price = 13$
    public void addTicket(String ticketNumber) {
        if (this.type == UserTypeOfTicket.UNDER18 || this.type == UserTypeOfTicket.PENSIONER || this.type == UserTypeOfTicket.DISABLED) {
            this.ticket = new Ticket(ticketNumber, 15);
        } else if (this.type == UserTypeOfTicket.ADULT) {
            this.ticket = new Ticket(ticketNumber, 20);
        } else if (this.type == UserTypeOfTicket.SMALLGROUP) {
            this.ticket = new Ticket(ticketNumber, 14);
        } else {
            this.ticket = new Ticket(ticketNumber, 13);
        }
    }

    public void addCredits(int numberOfTickets) {
        this.ticket.addCredits(numberOfTickets);
        this.budget -= numberOfTickets * this.getTicketPrice();
    }

    @Override
    public String toString() {
        return String.format("User %s\n%d years old\nbudget %.2f$", this.name, this.age, this.budget);
    }
}