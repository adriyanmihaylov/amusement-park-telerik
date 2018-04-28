package park.funzone;

public class Attraction {
    private String name;
    private AttractionType type;
    private int availableSeats;

    public Attraction(String name, AttractionType type, int availableSeats) {
        this.name = name;
        this.type = type;
        this.availableSeats = availableSeats;
    }

    public void visitAttraction(String userName, int userAge) {
        boolean isAllowed = isAttractionAllowed(userAge, type);
        if (isAllowed) {
            System.out.println(userName + " is having good time on " + name);
            // TODO : check ticket
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
}
