package park.users;

import park.cinema.Movie;
import park.products.tickets.Ticket;
import park.products.tickets.TicketType;

public class User {
    private String name;
    private int age;
    private double budget;
    private TicketType type;
    private int credits;

    public User(String name, int age, double budget, TicketType type) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.type = type;
        this.credits = 10;
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

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return String.format("User %s\n%d years old\nbudget %.2f$", this.name, this.age, this.budget);
    }

}