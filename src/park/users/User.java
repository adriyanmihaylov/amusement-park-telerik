package park.users;

import park.products.tickets.TicketType;

public class User {
    private String name;
    private int age;
    private double budget;
    private TicketType type;

    public User(String name, int age, double budget, TicketType type) {
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

    @Override
    public String toString() {
        return String.format("User %s\n%d years old\nbudget %.2f$", this.name, this.age, this.budget);
    }

}