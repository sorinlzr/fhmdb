package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.features.MovieSearchService;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.service.MovieFilterService;
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

        searchField.setOnKeyTyped(keyEvent -> {
            String searchTerm = searchField.getText();
            searchKeyword(searchTerm);
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
    }

    public void resetFilterCriteria() {
        MovieFilterService.resetFilterCriteria(genreComboBox, filteredList, searchField);
    }

    public void selectGenre() {
        MovieFilterService.selectSpecificGenre(genreComboBox, filteredList);
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
        searchResults.addAll(movieSearchService.searchForMovie(searchTerm));
        observableMovies.clear();
        observableMovies.addAll(searchResults);
    }
}