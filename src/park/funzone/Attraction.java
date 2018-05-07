package park.funzone;

import park.interfaces.Statistic;

public class Attraction implements Statistic {
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
        return String.format("Attraction %-10s Danger Level %-10s", name, dangerLevel);
    }

    @Override
    public void showStatistic() {
        System.out.println(toString());
    }
}