package park.funzone;

import park.stores.CashDesk;

public class Attraction {
    private String name;
    private AttractionType type;
    private CashDesk desk;
    private int availableSeats;

    public Attraction(String name, AttractionType type, int availableSeats, CashDesk desk) {
        this.name = name;
        this.type = type;
        this.availableSeats = availableSeats;
        this.desk = desk;
    }

    public void visitAttraction(String userName, int userAge) {
        boolean isAllowed = isAttractionAllowed(userAge, type);
        boolean hasEmptySeats = hasEmptySeats(availableSeats);
        if (isAllowed && hasEmptySeats) {
            System.out.println(userName + " has visited the " + name);
            availableSeats--;
            // TODO : check ticket(subtract credits)
        } else {
            System.out.println("We are sorry, but " + name + " is considered dangerous.");
        }
    }

    public String getName() {
        return name;
    }

    public AttractionType getType() {
        return type;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    private boolean isAttractionAllowed(int userAge, AttractionType type) {
        return (userAge >= 15 && userAge <= 65) || type != AttractionType.DANGER;
    }

    private boolean hasEmptySeats(int availableSeats) {
        return availableSeats > 0;
    }
}
