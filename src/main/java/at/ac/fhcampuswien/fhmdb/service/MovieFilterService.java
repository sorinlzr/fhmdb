package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

public class MovieFilterService {

    public static void resetFilterCriteria(JFXComboBox<Genre> genreComboBox,
                                           FilteredList<Movie> filteredList,
                                           TextField searchField) {
        searchField.clear();
        genreComboBox.setValue(Genre.ALL);
        if (genreComboBox.getValue() != null) {
            genreComboBox.getSelectionModel().clearSelection();
        }
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
