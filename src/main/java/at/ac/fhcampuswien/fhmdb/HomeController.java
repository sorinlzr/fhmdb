package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.RatingOption;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import at.ac.fhcampuswien.fhmdb.service.MovieFilterService;
import at.ac.fhcampuswien.fhmdb.service.MovieSearchService;
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

    @FXML
    private TextField searchField;

    @FXML
    private JFXComboBox<Genre> genreComboBox;

    @FXML
    private JFXComboBox<Integer> releaseYearPicker;

    @FXML
    private JFXComboBox<RatingOption> ratingComboBox;

    @FXML
    private JFXListView<Movie> movieListView;

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private final FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Movie> allMovies;

        try {
            allMovies = MovieAPIService.getMovies();
        } catch (IOException e) {
            allMovies = new ArrayList<>();
        }

        observableMovies.addAll(allMovies);

        movieListView.setItems(filteredMovies);
        movieListView.setCellFactory(e -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        int currentYear = java.time.LocalDate.now().getYear();

        List<Integer> releaseYears = IntStream.rangeClosed(0, 80)
                .mapToObj(i -> currentYear - i)
                .toList();

        releaseYearPicker.setPromptText("Filter by Release Year");
        releaseYearPicker.getItems().addAll(releaseYears);

        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(RatingOption.values());

        searchBtn.setOnAction(actionEvent -> searchAndFilterMovies());

        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                observableMovies.sort(Comparator.naturalOrder());
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.sort(Collections.reverseOrder());
                sortBtn.setText("Sort (asc)");
            }
        });

        resetFilterBtn.setOnAction(actionEvent -> resetSearchAndFilterCriteria());
    }

    private void searchAndFilterMovies() {
        filteredMovies.setPredicate(movie -> true);

        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) MovieSearchService.searchInMovieTitleAndInMovieDescription(searchQuery, filteredMovies);

        Genre genre = genreComboBox.getValue();
        if (genre != null && genre != Genre.ALL) MovieFilterService.filterMoviesByGenre(genre, filteredMovies);
    }

    private void resetSearchAndFilterCriteria() {
        searchField.clear();

        genreComboBox.setValue(Genre.ALL);
        genreComboBox.getSelectionModel().clearSelection();

        filteredMovies.setPredicate(movie -> true);
    }

    //TODO move to different class??
    private String getMostPopularActor(List<Movie> movies) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //TODO move to different class??
    private int getLongestMovieTitle(List<Movie> movies) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //TODO move to different class??
    private long countMoviesFrom(List<Movie> movies, String director) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //TODO move to different class??
    private List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}