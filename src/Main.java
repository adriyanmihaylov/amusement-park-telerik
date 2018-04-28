import park.Park;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO create menu methods for each case
        Park park = new Park("Disneyland");
        mainMenu(park);
    }

    public static void mainMenu(Park park) throws IOException {
        String[] options = {"Buy ticket", "Go shopping", "Watch a movie", "Ride some attraction", "Exit"};
        printOptions(options);

        switch (readCommand()) {
            case "buy": // ticket menu
                // TODO call method..
                ticketMenu(park);
                break;
            case "go":
                // TODO call method..
                break;
            case "watch":
                // TODO call method..
                break;
            case "ride":
                break;
            case "exit":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        
        mainMenu(park);
    }

    public static void ticketMenu(Park park) throws IOException {
        String[] options = {"SingleTicket", "GroupTicket", "Exit"};

        printOptions(options);

        switch (readCommand()) {
            case "single":
                System.out.println("Create person + ticket + ticketType");
                break;
            case "group":
                System.out.println("");
                break;
            case "exit":
                return;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public static String readCommand() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = reader.readLine().toLowerCase();
        return command;
    }

    public static void printOptions(String[] options) {
        IntStream.range(0, options.length)
                .mapToObj(i -> (i + 1) + "." + options[i])
                .forEach(System.out::println);
    }
}