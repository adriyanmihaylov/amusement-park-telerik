package  park;

import park.cinema.Cinema;
import park.funzone.Attraction;
import park.stores.Store;
import park.users.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Park {
    private String name;
    protected String password;
    private Set<Store> stores;
    private List<User> users;
    private Set<Cinema> cinemas;
    private Set<Attraction> attractions;
    private int ticketsCounter;

    public Park(String name,String password) {
        setName(name);
        this.password = password;
        this.stores = new HashSet<>();
        this.users = new ArrayList<>();
        this.cinemas = new HashSet<>();
        this.attractions = new HashSet<>();
        this.ticketsCounter = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicketsCounter() {
        return this.ticketsCounter;
    }
    public String setTicketsCounter() {
        this.ticketsCounter++;
        return "";
    }

    //TODO make a stream()
    public int findUserIndex(String name,String ticketNumber) {
        if(this.users.size() < 1) {
            return -1;
        }
        for (User user: users) {
            if(user.getName().equals(name) && user.getTicketNumber(ticketNumber)) {
                return this.users.indexOf(user);
            }
        }
       return -1;
    }
    public User getUserByIndex(int userIndex) {
        return this.users.get(userIndex);
    }

    public void addStores(List<Store> stores) {
        this.stores.addAll(stores);
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }

    public void addAttractions(List<Attraction> attractions) {
        this.attractions.addAll(attractions);
    }

    public void addCinemas(HashSet<String> cinemas) {
        Set<Cinema> cinemasToAdd = cinemas.stream()
                .map(x -> new Cinema(x))
                .collect(Collectors.toSet());
        this.cinemas.addAll(cinemasToAdd);
    }

    public void deleteStore(Store store) {
        this.stores.remove(store);
    }
    public void deleteAttraction(Attraction attraction) {
        this.attractions.remove(attraction);
    }

    public  void deleteUser(User user) {
        this.users.remove(user);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updateUser(int index,User currentUser) {
        users.set(index,currentUser);
    }
}