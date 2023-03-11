package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.transformation.FilteredList;

public class MovieFilterService {

    public static void resetFilterCriteria(FilteredList<Movie> filteredList) {
        filteredList.setPredicate(movie -> true);
    }

    public static void selectSpecificGenre(Genre genre,
                                           FilteredList<Movie> filteredList) {
        filteredList.setPredicate(movie -> {
            if (Genre.ALL.equals(genre)) {
                return true;
            } else {
                return movie.getGenres().contains(genre);
            }
        });
    }
}
