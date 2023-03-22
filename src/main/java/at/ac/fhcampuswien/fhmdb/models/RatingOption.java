package at.ac.fhcampuswien.fhmdb.models;

public enum RatingOption {
    RATING_9_TO_10(9.0, 10.0),
    RATING_8_TO_9(8.0, 9.0),
    RATING_7_TO_8(7.0, 8.0),
    RATING_6_TO_7(6.0, 7.0),
    RATING_5_TO_6(5.0, 6.0),
    RATING_4_TO_5(4.0, 5.0),
    RATING_3_TO_4(3.0, 4.0),
    RATING_2_TO_3(2.0, 3.0),
    RATING_1_TO_2(1.0, 2.0),
    RATING_0_TO_1(0.0, 1.0);

    private final double minRating;
    private final double maxRating;

    RatingOption(double minRating, double maxRating) {
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public boolean contains(double rating) {
        return rating >= minRating && rating < maxRating;
    }

    @Override
    public String toString() {
        return String.format("%.1f to %.1f stars", minRating, maxRating);
    }
}
