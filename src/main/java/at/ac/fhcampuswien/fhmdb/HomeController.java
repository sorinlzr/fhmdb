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
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController {

    /**
     * Initializes the controller class.
     * <p>
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">JavaFX Initialize</a>
     * NOTE This interface has been superseded by automatic injection of location and resources properties into the controller.
     * FXMLLoader will now automatically call any suitably annotated no-arg initialize() method defined by the controller.
     * It is recommended that the injection approach be used whenever possible.
     */
    @FXML
    private URL location;

    /**
     * Initializes the controller class.
     * <p>
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">JavaFX Initialize</a>
     * NOTE This interface has been superseded by automatic injection of location and resources properties into the controller.
     * FXMLLoader will now automatically call any suitably annotated no-arg initialize() method defined by the controller.
     * It is recommended that the injection approach be used whenever possible.
     */
    @FXML
    private ResourceBundle resources;

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

    @FXML
    private JFXListView<Movie> movieListView;

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private final FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies);

    /**
     * Initializes the controller class.
     * <p>
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">JavaFX Initialize</a>
     * NOTE This interface has been superseded by automatic injection of location and resources properties into the controller.
     * FXMLLoader will now automatically call any suitably annotated no-arg initialize() method defined by the controller.
     * It is recommended that the injection approach be used whenever possible.
     */
    public void initialize() {
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

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setValue(Genre.NO_FILTER);

        releaseYearPicker.getItems().addAll(Year.values());
        releaseYearPicker.setValue(Year.NO_FILTER);

        ratingComboBox.getItems().addAll(Rating.values());
        ratingComboBox.setValue(Rating.NO_FILTER);

        searchBtn.setOnAction(actionEvent -> setFilter());
        sortBtn.setOnAction(actionEvent -> sortMoviesAndSetButtonText());
        resetFilterBtn.setOnAction(actionEvent -> resetFilter());
    }

    private void resetFilter() {
        observableMovies.clear();
        searchField.clear();

        sortBtn.setText(SORT_DEFAULT_TEXT_DESC);

        genreComboBox.setValue(Genre.NO_FILTER);
        releaseYearPicker.setValue(Year.NO_FILTER);
        ratingComboBox.setValue(Rating.NO_FILTER);

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

        String genre = genreComboBox.getValue() == Genre.NO_FILTER ? "" : genreComboBox.getValue().name();
        String releaseYear = releaseYearPicker.getValue() == Year.NO_FILTER ? "" : releaseYearPicker.getValue().toString();
        String ratingFrom = ratingComboBox.getValue() == Rating.NO_FILTER ? "" : String.valueOf(ratingComboBox.getValue().getRatingFrom());

        try {
            moviesWithFilter = MovieAPIService.getMoviesBy(searchField.getText(), genre, releaseYear, ratingFrom);
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