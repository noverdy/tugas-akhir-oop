package classes;

public enum MovieGenre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    OTHER("Others");

    private final String type;

    MovieGenre(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
