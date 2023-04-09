package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.RatingOption;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton resetFilterBtn;

    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXComboBox<Integer> releaseYearPicker;

    @FXML
    public JFXComboBox<RatingOption> ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    private final FilteredList<Movie> filteredList = new FilteredList<>(observableMovies);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);

        movieListView.setItems(filteredList);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        releaseYearPicker.setPromptText("Filter by Release Year");
        releaseYearPicker.getItems().addAll(getYears());

        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(RatingOption.values());

        searchBtn.setOnAction(actionEvent -> searchForMovie(searchField.getText().trim(), genreComboBox.getValue(), filteredList, observableMovies));

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
        filteredList.setPredicate(movie -> true);
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

    private List<Integer> getYears() {
        int currentYear = java.time.LocalDate.now().getYear();
        return IntStream.rangeClosed(0, 80)
                .mapToObj(i -> currentYear - i)
                .collect(Collectors.toList());
    }


    public String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())

                // Group actors by their name and count their occurrences using the Collectors.groupingBy method
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))

                // Create a stream of the entry set of the grouped actors
                .entrySet().stream()

                // Find the actor with the maximum count using an optimized custom comparator
                .max((e1, e2) -> Long.compare(e1.getValue(), e2.getValue()) != 0 ? Long.compare(e1.getValue(), e2.getValue()) : -1)

                // Get the name of the most popular actor by mapping the entry to its key (actor name)
                .map(Map.Entry::getKey)

                // Return the name of the most popular actor or a default message if not found
                .orElse("No popular actor found");
    }


    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                // Get the movie with the longest title by comparing the length of the titles using a Comparator (a functional interface that compares two objects)
                .max(Comparator.comparingInt(movie -> movie.getTitle().length()))

                // Get the length of the longest title by mapping the movie object to the length of its title
                .map(movie -> movie.getTitle().length())

                // Return the length of the longest title, or return 0 if the list is empty
                .orElse(0);
    }

    // Returns the number of movies directed by the given director
    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                // Filter the movies directed by the given director using the contains method on the list of directors
                .filter(movie -> movie.getDirectors().contains(director))

                // Count the filtered movies and return the result (count() always returns long)
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                // Filter the movies released between the given years using a logical AND condition
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)

                // Collect the filtered movies into a list and return the result
                .collect(Collectors.toList());
    }
}