package park.funzone;

import park.users.User;

public class Attraction {
    private String name;
    private AttractionDangerLevel dangerLevel;

    public Attraction(String name, AttractionDangerLevel dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public void visitAttraction(User user) {
        boolean isAllowed = isAttractionAllowed(user.getAge(), dangerLevel);
        if (isAllowed) {
            System.out.println(user.getName() + " has visited the " + name);
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

}
