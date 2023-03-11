package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieSearchService {

    public static List<Movie> searchInMovieTitle(String movieTitle, List<Movie> movies) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieTitle.trim().toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public static List<Movie> searchInMovieDescription(String movieDescription, List<Movie> movies) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDescription().toLowerCase().contains(movieDescription.trim().toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public static Set<Movie> searchForMovie(String searchTerm, List<Movie> movies) {
        Set<Movie> searchResult = new HashSet<>();
        searchResult.addAll(searchInMovieTitle(searchTerm, movies));
        searchResult.addAll(searchInMovieDescription(searchTerm, movies));
        return searchResult;
    }

    public static void searchKeyword(String searchTerm, FilteredList<Movie> filteredList, List<Movie> movies) {
        Set<Movie> searchResults = new HashSet<>(searchForMovie(searchTerm, movies));
        filteredList.setPredicate(searchResults::contains);
    }
}