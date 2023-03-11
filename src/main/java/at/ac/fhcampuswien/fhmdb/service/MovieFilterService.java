package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class MovieFilterService {

    public static void resetFilterCriteria(FilteredList<Movie> filteredList) {
        filteredList.setPredicate(movie -> true);
    }

    public static List<Movie> filterMoviesByGenre(Genre genre, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        if (Genre.ALL.equals(genre) || genre == null) {
            return movies;
        }

        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}
