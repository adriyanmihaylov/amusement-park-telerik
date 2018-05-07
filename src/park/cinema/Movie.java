package park.cinema;

public class Movie {
    private String name;
    private MovieGenre genre;

    public Movie(String name, MovieGenre genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return String.format("%-15s (%s)", name, genre);
    }
}
