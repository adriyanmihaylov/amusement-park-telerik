package park.funzone;

public class Attraction {
    private String name;
    private AttractionDangerLevel dangerLevel;
    private int availableSeats;

    public Attraction(String name, AttractionDangerLevel dangerLevel, int availableSeats) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.availableSeats = availableSeats;
    }

    public void visitAttraction(String userName, int userAge) {
        boolean isAllowed = isAttractionAllowed(userAge, dangerLevel);
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

    public AttractionDangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    private boolean isAttractionAllowed(int userAge, AttractionDangerLevel dangerLevel) {
        boolean isAllowed = false;
        switch (dangerLevel) {
            case LOW:
                isAllowed = true;
                break;

            case MEDIUM:
                isAllowed = userAge >= 10;
                break;

            case HIGH:
                isAllowed = userAge >= 16 && userAge <= 60;
                break;
        }

        return isAllowed;
    }

    private boolean hasEmptySeats(int availableSeats) {
        return availableSeats > 0;
    }
}
