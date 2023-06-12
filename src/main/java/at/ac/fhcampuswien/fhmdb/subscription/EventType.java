package at.ac.fhcampuswien.fhmdb.subscription;

public enum EventType {
    ADD_TO_WATCHLIST ("Movie successfully added to watchlist"),
    ALREADY_ON_WATCHLIST("Movie already on watchlist"),
    REMOVE_FROM_WATCHLIST("Movie successfully removed from watchlist");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
