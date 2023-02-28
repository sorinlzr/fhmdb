package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.features.MovieSearchService;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        searchField.setOnKeyTyped(keyEvent -> {
            String searchTerm = searchField.getText().trim();
            searchKeyword(searchTerm);
        });


        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
            }
        });


    }

    /**
     * This method performs a search operation on a list of movies based on a given search term.
     * It updates the observableMovies list with the search results.
     *
     * @param searchTerm The keyword to search in the movie titles and descriptions.
     */
    private void searchKeyword(String searchTerm) {
        observableMovies.setAll(allMovies);
        MovieSearchService movieSearchService = new MovieSearchService(observableMovies);
        Set<Movie> searchResults = new HashSet<>();
        searchResults.addAll(movieSearchService.searchInMovieTitle(searchTerm));
        searchResults.addAll(movieSearchService.searchInMovieDescription(searchTerm));
        observableMovies.clear();
        observableMovies.addAll(searchResults);
    }
}