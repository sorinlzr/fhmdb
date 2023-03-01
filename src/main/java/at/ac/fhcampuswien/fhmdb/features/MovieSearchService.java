package at.ac.fhcampuswien.fhmdb.features;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieSearchService {
    private final List<Movie> movies;

    public MovieSearchService(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> searchInMovieTitle(String movieTitle) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieTitle.trim().toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public List<Movie> searchInMovieDescription(String movieDescription) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDescription().toLowerCase().contains(movieDescription.trim().toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public Set<Movie> searchForMovie(String searchTerm) {
        Set<Movie> searchResult = new HashSet<>();
        searchResult.addAll(this.searchInMovieTitle(searchTerm));
        searchResult.addAll(this.searchInMovieDescription(searchTerm));
        return searchResult;
    }
}
