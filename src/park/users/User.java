package park.users;

public class User {
    private String name;
    private int age;
    private double budget;
    private UserTypeOfTicket type;
    private int ticketNumber;

    public User(String name, int age, double budget, UserTypeOfTicket type) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.type = type;
        ticketNumber = 0;
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
        return this.ticketNumber == ticketNumber;
    }

    //TODO complete addTicket - remove else statement
    public void addTicket(int ticketNumber) {
        if(this.ticketNumber == 0) {
            this.ticketNumber = ticketNumber;
        } else {
            System.out.println("This person already has a ticket!");
        }
    }

    @Override
    public String toString() {
        return String.format("User %s\n%d years old\nbudget %.2f$", this.name, this.age, this.budget);
    }
}