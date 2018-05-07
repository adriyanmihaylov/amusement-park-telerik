package park.funzone;

public class Attraction {
    private String name;
    private AttractionLevel dangerLevel;

    public Attraction(String name, AttractionLevel dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public String getName() {
        return name;
    }

    public AttractionLevel getDangerLevel() {
        return dangerLevel;
    }

    @Override
    public String toString() {
        return String.format("Attraction \"%s\", Danger Level- %s", name, dangerLevel);
    }
}
