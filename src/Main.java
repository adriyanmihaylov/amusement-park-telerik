import park.Park;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO create menu methods for each case
        Park park = new Park("Disneyland");
        mainMenu(Park park);
    }

    public static void mainMenu(Park park) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();
        switch (command) {
            case "1": // ticket menu
                // TODO call method..
                ticketMenu(park);
                break;
            case "2":
                // TODO call method..
                break;
            case "3":
                // TODO call method..
                break;
            case "exit":
                return;
            default:
                System.out.println("Not a valid command");
                break;
        }
        mainMenu(park);
    }

    public static void ticketMenu(Park park) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine();
        switch (command) {
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "exit":
                return;
            default:
                break;
        }
    }
}