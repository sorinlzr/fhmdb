package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class MovieSearchService {

    private MovieSearchService() {
        throw new IllegalStateException("Utility class");
    }

    public static void searchInMovieTitleAndInMovieDescription(String searchTerm, FilteredList<Movie> filteredMovies) {
        Predicate<Movie> combinedPredicate = getSearchInMovieTitlePredicate(searchTerm)
                .or(getSearchInMovieDescriptionPredicate(searchTerm));

        filteredMovies.setPredicate(combinedPredicate.and(filteredMovies.getPredicate()));
    }

    public static void searchInMovieTitle(String movieTitle, FilteredList<Movie> filteredMovies) {
        filteredMovies.setPredicate(getSearchInMovieTitlePredicate(movieTitle).and(filteredMovies.getPredicate()));
    }

    private static Predicate<Movie> getSearchInMovieTitlePredicate(String movieTitle) {
        return movie -> movie.getTitle().toLowerCase().contains(movieTitle.trim().toLowerCase());
    }

    public static void searchInMovieDescription(String movieDescription, FilteredList<Movie> filteredMovies) {
        filteredMovies.setPredicate(getSearchInMovieDescriptionPredicate(movieDescription).and(filteredMovies.getPredicate()));
    }

    private static Predicate<Movie> getSearchInMovieDescriptionPredicate(String movieDescription) {
        return movie -> movie.getDescription().toLowerCase().contains(movieDescription.trim().toLowerCase());
    }
}