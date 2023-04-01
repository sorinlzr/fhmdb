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
        filteredMovies.setPredicate(getFilterMoviesByGenrePredicate(genre).and(filteredMovies.getPredicate()));
    }

    private static Predicate<Movie> getFilterMoviesByGenrePredicate(Genre genre) {
        return movie -> movie.getGenres().contains(genre);
    }
}
