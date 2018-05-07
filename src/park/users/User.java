package park.users;

import park.cinema.Cinema;
import park.cinema.Movie;
import park.funzone.Attraction;
import park.interfaces.IConsumable;
import park.interfaces.IUsable;
import park.interfaces.Statistic;
import park.products.FoodProduct;
import park.products.Product;
import park.products.Ticket;

import java.util.ArrayList;
import java.util.List;

public class User implements Statistic {
    private String name;
    private int age;
    private double budget;
    private UserTicketPrice userType;
    private Ticket ticket;
    private ArrayList<Product> boughtProducts;

    public User(String name, int age, double budget, UserTicketPrice type) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.ticket = null;
        this.userType = type;
        this.boughtProducts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public UserTicketPrice getUserType() {
        return this.userType;
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

    public void addTicket(String ticketNumber, double price) {
        this.ticket = new Ticket(ticketNumber, price);
    }

    public boolean hasFoodProducts() {
        boolean hasFood = boughtProducts.stream().anyMatch(product -> product instanceof IConsumable);
        System.out.println("Has food = " + hasFood);
        return hasFood;
    }

    public void visitAttraction(Attraction attraction) {
        if (this.hasFoodProducts()) {
            System.out.println("You can't bring food products on any attraction! You have to consume them!");
            return;
        }

        if (this.ticket.hasCredits()) {
            ticket.use();
            System.out.println(getName() + " has visited attraction " + attraction.getName());
            System.out.println("Remaining credits: " + ticket.getTicketCredits());
        } else {
            System.out.println("Sorry you don't have enough credits!");
        }
    }

    public Ticket getUserTicket() {
        return ticket;
    }

    public void addCredits(int numberOfTickets) {
        this.ticket.addCredits(numberOfTickets);
        this.budget -= numberOfTickets * this.getTicketPrice();
    }

    public void buyProduct(Product product) {
        this.boughtProducts.add(product);
        this.budget -= product.getPrice();
    }

    public List<String> getAllProductsNames() {
        List<String> productNames = new ArrayList<>();

        for (Product product : boughtProducts) {
            productNames.add(product.getName());
        }

        return productNames;

    }

    public Product getProductByName(String productName) {
        for (Product product : boughtProducts) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }

        return null;
    }

    public void consumeProduct(Product product) {
        boughtProducts.remove(product);

        if (product instanceof IConsumable) {
            ((IConsumable) product).consume();
            return;
        }

        if (product instanceof IUsable) {
            ((IUsable) product).use();
            return;
        }


    }

    public void showUserInfo() {
        System.out.println("\n\t\t\t\t\t---------- USER INFO ----------\n");
        System.out.printf("|\tUser %s\t|\t%d years old\t|\tbudget %.2f$\t|\tTicket \"%s\"\t|\tcredits left: %d\t|\n",
                this.name, this.age, this.budget, this.ticket.getTicketNumber(), this.ticket.getTicketCredits());

        System.out.println("\n\t\t\t\t\t---------- INVENTORY ----------\n");

        if (boughtProducts.size() == 0) {
            System.out.println("You don't have any bought products!");
        }

        for (Product product : boughtProducts) {
            System.out.println(product);
        }

        System.out.println("\n\t\t\t\t-------------------------------\n");
    }

    @Override
    public String toString() {
        return String.format("|\tUser %s\t|\t%d years old\t|\tbudget %.2f$\t|\tTicket \"%s\"\t|", this.name, this.age, this.budget, this.ticket.getTicketNumber());
    }

    public void watchMovie(Movie currentMovie) {
        if (this.ticket.hasCredits()) {
            System.out.println(getName() + " is watching " + currentMovie.getName());
            ticket.use();
        } else {
            System.out.println("Sorry you don't have enough credits!");
        }
    }

    @Override
    public void showStatistic() {
        System.out.printf("%-15s | Age %-5d | Ticket #%-5s credits %-5d | Ticket type %-15s price %-5.2f$\n",getName(),getAge(),
                ticket.getTicketNumber(),ticket.getTicketCredits(),userType, ticket.getTicketPrice());
    }
}