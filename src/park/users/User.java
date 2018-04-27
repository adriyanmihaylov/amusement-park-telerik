package park.users;

public class User {
    private String name;
    private int age;
    private float budget;
    private UserType type;

    public User(String name, int age, float budget, UserType type) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //TODO exception handling for name
            this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        //TODO exception handling for age
        this.age = age;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}
