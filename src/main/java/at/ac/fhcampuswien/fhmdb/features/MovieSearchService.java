package at.ac.fhcampuswien.fhmdb.features;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieSearchService {
    private final List<Movie> movies;

    public MovieSearchService(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> searchInMovieTitle(String movieTitle) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieTitle.toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public List<Movie> searchInMovieDescription(String movieDescription) {
        List<Movie> searchResult = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDescription().toLowerCase().contains(movieDescription.toLowerCase())) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }
}
