package at.ac.fhcampuswien.fhmdb.filter;

public enum Rating {
    NO_FILTER(-1.0),
    RATING_9_TO_MAX(9.0),
    RATING_8_TO_MAX(8.0),
    RATING_7_TO_MAX(7.0),
    RATING_6_TO_MAX(6.0),
    RATING_5_TO_MAX(5.0),
    RATING_4_TO_MAX(4.0),
    RATING_3_TO_MAX(3.0),
    RATING_2_TO_MAX(2.0),
    RATING_1_TO_MAX(1.0),
    RATING_0_TO_MAX(0.0);

    private static final double maxRating = 10.0;
    private final double ratingFrom;

    public double getRatingFrom() {
        return ratingFrom;
    }

    Rating(double ratingFrom) {
        this.ratingFrom = ratingFrom;
    }

    @Override
    public String toString() {
        if (this == NO_FILTER) return "Filter by Rating";

        return String.format("%.1f to %.1f stars", ratingFrom, maxRating);
    }
}
