package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;
import java.util.function.Predicate;

public class MovieFilterService {

    private MovieFilterService() {
        throw new IllegalStateException("Utility class");
    }

    public static void filterMoviesByGenre(Genre genre, FilteredList<Movie> filteredMovies) {
        Predicate<? super Movie> existingPredicate = filteredMovies.getPredicate();
        Predicate<Movie> genrePredicate = getFilterMoviesByGenrePredicate(genre);

        if (existingPredicate != null) {
            filteredMovies.setPredicate(genrePredicate.and(existingPredicate));
        } else {
            filteredMovies.setPredicate(genrePredicate);
        }
    }

    private static Predicate<Movie> getFilterMoviesByGenrePredicate(Genre genre) {
        return movie -> movie.getGenres().contains(genre);
    }
}
