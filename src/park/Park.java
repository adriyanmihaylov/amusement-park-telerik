package  park;

import park.funzone.Attraction;
import park.stores.Store;
import park.users.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Park {
    private String name;
    private Set<Store> stores;
    private List<User> users;
    private Cinema cinema;
    private Set<Attraction> attractions;

    public Park(String name) {
        setName(name);
        this.stores = new HashSet<>();
        this.users = new ArrayList<>();
        this.attractions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStores(List<Store> stores) {
        this.stores.addAll(stores);
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    public void addAttractions(List<Attraction> attractions) {
        this.attractions.addAll(attractions);
    }

    public void deleteStore(User user) {
        this.users.remove(user);
    }
    public void deleteAttraction(Attraction attraction) {
        this.attractions.remove(attraction);
    }

    public  void deleteUser(User user) {
        this.users.remove(user);
    }
}