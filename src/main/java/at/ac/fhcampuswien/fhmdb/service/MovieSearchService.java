package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class MovieSearchService {

    private MovieSearchService() {
        throw new IllegalStateException("Utility class");
    }

    public static void searchInMovieTitleAndInMovieDescription(String searchTerm, FilteredList<Movie> filteredMovies) {
        Predicate<? super Movie> existingPredicate = filteredMovies.getPredicate();
        Predicate<Movie> movieTitlePredicate = getSearchInMovieTitlePredicate(searchTerm);
        Predicate<Movie> movieDescriptionPredicate = getSearchInMovieDescriptionPredicate(searchTerm);

        Predicate<Movie> combinedPredicate = movieTitlePredicate.or(movieDescriptionPredicate);

        if (existingPredicate != null){
            filteredMovies.setPredicate(combinedPredicate.and(existingPredicate));
        }else {
            filteredMovies.setPredicate(combinedPredicate);
        }
    }

    public static void searchInMovieTitle(String movieTitle, FilteredList<Movie> filteredMovies) {
        Predicate<? super Movie> existingPredicate = filteredMovies.getPredicate();
        Predicate<Movie> movieTitlePredicate = getSearchInMovieTitlePredicate(movieTitle);

        if (existingPredicate != null){
            filteredMovies.setPredicate(movieTitlePredicate.and(existingPredicate));
        }else{
            filteredMovies.setPredicate(movieTitlePredicate);
        }
    }

    private static Predicate<Movie> getSearchInMovieTitlePredicate(String movieTitle) {
        return movie -> movie.getTitle().toLowerCase().contains(movieTitle.trim().toLowerCase());
    }

    public static void searchInMovieDescription(String movieDescription, FilteredList<Movie> filteredMovies) {
        Predicate<? super Movie> existingPredicate = filteredMovies.getPredicate();
        Predicate<Movie> movieDescriptionPredicate = getSearchInMovieDescriptionPredicate(movieDescription);

        if (existingPredicate != null){
            filteredMovies.setPredicate(movieDescriptionPredicate.and(existingPredicate));
        }else{
            filteredMovies.setPredicate(movieDescriptionPredicate);
        }
    }

    private static Predicate<Movie> getSearchInMovieDescriptionPredicate(String movieDescription) {
        return movie -> movie.getDescription().toLowerCase().contains(movieDescription.trim().toLowerCase());
    }
}