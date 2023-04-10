package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.filter.Rating;
import at.ac.fhcampuswien.fhmdb.filter.Year;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;

public class HomeController {
    @FXML
    private TextField searchField;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton resetFilterBtn;

    @FXML
    private JFXButton sortBtn;

    private static final String SORT_DEFAULT_TEXT_ASC = "Sort (asc)";
    private static final String SORT_DEFAULT_TEXT_DESC = "Sort (desc)";

    @FXML
    private JFXComboBox<Genre> genreComboBox;

    @FXML
    private JFXComboBox<Year> releaseYearPicker;

    @FXML
    private JFXComboBox<Rating> ratingComboBox;

    private static final String NO_FILTER = "";

    @FXML
    private JFXListView<Movie> movieListView;

    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void initialize() {
        movies.addAll(getAllMoviesOrEmptyList());
        sortMovies();

        movieListView.setItems(movies);
        movieListView.setCellFactory(e -> new MovieCell());

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setValue(Genre.NO_FILTER);

        releaseYearPicker.getItems().addAll(Year.values());
        releaseYearPicker.setValue(Year.NO_FILTER);

        ratingComboBox.getItems().addAll(Rating.values());
        ratingComboBox.setValue(Rating.NO_FILTER);

        searchBtn.setOnAction(actionEvent -> setFilter());

        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
                sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
            } else {
                sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
            }

            sortMovies();
        });

        resetFilterBtn.setOnAction(actionEvent -> resetFilter());
    }

    private void resetFilter() {
        movies.clear();
        searchField.clear();

        sortBtn.setText(SORT_DEFAULT_TEXT_DESC);

        genreComboBox.setValue(Genre.NO_FILTER);
        releaseYearPicker.setValue(Year.NO_FILTER);
        ratingComboBox.setValue(Rating.NO_FILTER);

        movies.addAll(getAllMoviesOrEmptyList());
        sortMovies();
    }

    private void setFilter() {
        movies.clear();

        List<Movie> moviesWithFilter;

        String genre = genreComboBox.getValue() == Genre.NO_FILTER ? NO_FILTER : genreComboBox.getValue().name();
        String releaseYear = releaseYearPicker.getValue() == Year.NO_FILTER ? NO_FILTER : releaseYearPicker.getValue().toString();
        String ratingFrom = ratingComboBox.getValue() == Rating.NO_FILTER ? NO_FILTER : String.valueOf(ratingComboBox.getValue().getRatingFrom());

        try {
            moviesWithFilter = MovieAPIService.getMoviesBy(searchField.getText(), genre, releaseYear, ratingFrom);
        } catch (IOException e){
            moviesWithFilter = new ArrayList<>();
        }

        movies.addAll(moviesWithFilter);
        sortMovies();
    }

    private void sortMovies(){
        if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
            movies.sort(Collections.reverseOrder());
        } else {
            movies.sort(Comparator.naturalOrder());
        }
    }

    private List<Movie> getAllMoviesOrEmptyList() {
        List<Movie> allMovies;

        try {
            allMovies = MovieAPIService.getMovies();
        } catch (IOException e){
            allMovies = new ArrayList<>();
        }

        return allMovies;
    }
}