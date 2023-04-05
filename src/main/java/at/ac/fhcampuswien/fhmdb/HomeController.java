package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.RatingOption;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    private JFXButton resetFilterBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton sortBtn;

    private static final String SORT_DEFAULT_TEXT_ASC = "Sort (asc)";
    private static final String SORT_DEFAULT_TEXT_DESC = "Sort (desc)";

    @FXML
    private TextField searchField;

    @FXML
    private JFXComboBox<Genre> genreComboBox;

    private static final String GENRE_DEFAULT_TEXT = "Filter by Genre";

    @FXML
    private JFXComboBox<Integer> releaseYearPicker;

    private static final String RELEASE_YEAR_DEFAULT_TEXT = "Filter by Release Year";

    @FXML
    private JFXComboBox<RatingOption> ratingComboBox;

    private static final String RATING_DEFAULT_TEXT = "Filter by Rating";

    @FXML
    private JFXListView<Movie> movieListView;

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private final FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Movie> allMovies;

        try {
            allMovies = MovieAPIService.getMovies();
        } catch (IOException e){
            allMovies = new ArrayList<>();
        }

        observableMovies.addAll(allMovies);
        sortMovies();

        movieListView.setItems(filteredMovies);
        movieListView.setCellFactory(e -> new MovieCell());

        genreComboBox.setPromptText(GENRE_DEFAULT_TEXT);
        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setValue(Genre.ALL);

        int currentYear = java.time.LocalDate.now().getYear();

        List<Integer> releaseYears = IntStream.rangeClosed(0, 80)
                .mapToObj(i -> currentYear - i)
                .toList();

        releaseYearPicker.setPromptText(RELEASE_YEAR_DEFAULT_TEXT);
        releaseYearPicker.getItems().addAll(releaseYears);

        ratingComboBox.setPromptText(RATING_DEFAULT_TEXT);
        ratingComboBox.getItems().addAll(RatingOption.values());

        searchBtn.setOnAction(actionEvent -> setFilter());
        sortBtn.setOnAction(actionEvent -> sortMoviesAndSetButtonText());
        resetFilterBtn.setOnAction(actionEvent -> resetFilter());
    }

    private void resetFilter() {
        observableMovies.clear();

        searchField.clear();

        sortBtn.setText(SORT_DEFAULT_TEXT_DESC);

        genreComboBox.setPromptText(GENRE_DEFAULT_TEXT);
        genreComboBox.getSelectionModel().clearSelection();
        genreComboBox.setValue(Genre.ALL);

        releaseYearPicker.setPromptText(RELEASE_YEAR_DEFAULT_TEXT);
        releaseYearPicker.getSelectionModel().clearSelection();

        List<Movie> allMovies;

        try {
            allMovies = MovieAPIService.getMovies();
        } catch (IOException e){
            allMovies = new ArrayList<>();
        }

        observableMovies.addAll(allMovies);
        sortMovies();
    }

    private void setFilter() {
        observableMovies.clear();

        List<Movie> moviesWithFilter;

        String genre = genreComboBox.getValue() == Genre.ALL ? "" : genreComboBox.getValue().name();
        String releaseYear = releaseYearPicker.getValue() == null ? "" : releaseYearPicker.getValue().toString();

        try {
            moviesWithFilter = MovieAPIService.getMoviesBy(searchField.getText(), genre, releaseYear);
        } catch (IOException e){
            moviesWithFilter = new ArrayList<>();
        }

        observableMovies.addAll(moviesWithFilter);
        sortMovies();
    }

    private void sortMovies(){
        if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
            observableMovies.sort(Collections.reverseOrder());
        } else {
            observableMovies.sort(Comparator.naturalOrder());
        }
    }

    private void sortMoviesAndSetButtonText(){
        if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
            observableMovies.sort(Comparator.naturalOrder());
            sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
        } else {
            observableMovies.sort(Collections.reverseOrder());
            sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
        }
    }
}