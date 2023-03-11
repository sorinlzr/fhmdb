package at.ac.fhcampuswien.fhmdb;

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

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    public JFXButton resetFilterBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    private final FilteredList<Movie> filteredList = new FilteredList<>(observableMovies);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(filteredList);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        genreComboBox.setOnAction(actionEvent -> searchForMovie(searchField.getText().trim(), genreComboBox.getValue(), filteredList, observableMovies));

        searchField.setOnKeyTyped(keyEvent -> {
            String searchTerm = searchField.getText().trim();
            searchForMovie(searchTerm, genreComboBox.getValue(), filteredList, observableMovies);
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                observableMovies.sort(Comparator.naturalOrder());
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.sort(Collections.reverseOrder());
                sortBtn.setText("Sort (asc)");
            }
        });

        resetFilterBtn.setOnAction(actionEvent -> resetFilterCriteria(genreComboBox, filteredList, searchField));
    }

    public static void resetFilterCriteria(JFXComboBox<Genre> genreComboBox,
                                           FilteredList<Movie> filteredList,
                                           TextField searchField) {
        searchField.clear();
        genreComboBox.setValue(Genre.ALL);
        if (genreComboBox.getValue() != null) {
            genreComboBox.getSelectionModel().clearSelection();
        }
        MovieFilterService.resetFilterCriteria(filteredList);
    }

    public static void searchForMovie(String searchTerm, Genre genre, FilteredList<Movie> filteredList, List<Movie> movies) {
        Set<Movie> searchResults = new HashSet<>();
        Set<Movie> keywordSearchResults = MovieSearchService.searchKeyword(searchTerm, movies);
        List<Movie> genreSearchResults = MovieFilterService.filterMoviesByGenre(genre, movies);

        for (Movie movie : keywordSearchResults) {
            if (genreSearchResults.contains(movie)) {
                searchResults.add(movie);
            }
        }

        filteredList.setPredicate(searchResults::contains);
    }
}