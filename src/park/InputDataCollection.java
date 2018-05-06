package park;

public class InputDataCollection {
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;

    public InputDataCollection(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = "";
        this.fifth = "";
    }

    public InputDataCollection(String first, String second, String third, String fourth) {
        this(first,second,third);
        this.fourth = fourth;
    }

    InputDataCollection(String first,String second,String third,String fourth,String fifth) {
        this(first, second, third, fourth);
        this.fifth = fifth;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getThird() {
        return third;
    }

    public String getFourth() {
        return fourth;
    }

    public String getFifth() {
        return fifth;
    }
}
