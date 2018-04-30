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

    public boolean getTicketNumber(int ticketNumber) {
        return this.ticket.getTicketNumber() == ticketNumber;
    }
        // UNDER 18 ,PENSIONER, DISABLED - price 15$
       // SMALLGROUP - price = 14$
        // BIGGROUP - price = 13$
    public void addTicket(int ticketNumber) {
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

    @Override
    public String toString() {
        return String.format("User %s\n%d years old\nbudget %.2f$", this.name, this.age, this.budget);
    }
}