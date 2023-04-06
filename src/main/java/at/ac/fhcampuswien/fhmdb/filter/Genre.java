package at.ac.fhcampuswien.fhmdb.filter;

public enum Genre {
    NO_FILTER("Filter by Genre"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BIOGRAPHY("Biography"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    DOCUMENTARY("Documentary"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    HISTORY("History"),
    HORROR("Horror"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science Fiction"),
    SPORT("Sport"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    private final String genre;

    Genre(String genre){
        this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }
}
