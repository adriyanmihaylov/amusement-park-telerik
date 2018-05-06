package park.funzone;

import park.products.Ticket;
import park.users.User;

public class Attraction {
    private String name;
    private AttractionDangerLevel dangerLevel;

    public Attraction(String name, AttractionDangerLevel dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public String getName() {
        return name;
    }

    public AttractionDangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public void visitAttraction(User user) {

        if (user.hasFoodProducts()) {
            System.out.println("Sorry you can't ride the attraction before you consume the food products you have!");
            return;
        }

        boolean isAllowed = isAttractionAllowed(user.getAge(), dangerLevel);
        if (isAllowed) {
            Ticket userTicket = user.getUserTicket();
            if(userTicket.hasCredits()) {
                userTicket.use();
                System.out.println(user.getName() + " has visited the " + name);
            } else {
                System.out.println("Sorry you don't have enough credits!");
            }
        } else {
            System.out.println("We are sorry, but " + name + " is considered dangerous.");
        }
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
                isAllowed = userAge >= 18 && userAge <= 60;
                break;
        }

        return isAllowed;
    }

    @Override
    public String toString() {
        return String.format("Attraction \"%s\", Danger Level- %s", name, dangerLevel);
    }
}
