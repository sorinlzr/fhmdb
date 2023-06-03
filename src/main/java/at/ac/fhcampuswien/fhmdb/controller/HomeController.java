package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.filter.Rating;
import at.ac.fhcampuswien.fhmdb.filter.Year;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import at.ac.fhcampuswien.fhmdb.subscription.Observer;
import at.ac.fhcampuswien.fhmdb.subscription.EventType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.stream.Collectors;

import static at.ac.fhcampuswien.fhmdb.subscription.EventType.ADD_TO_WATCHLIST;
import static at.ac.fhcampuswien.fhmdb.subscription.EventType.ALREADY_ON_WATCHLIST;

public class HomeController extends AbstractViewController implements Observer {

    @FXML
    private StackPane parent;
    @FXML
    protected JFXButton watchlistButton;
    @FXML
    public JFXButton navigationButton;

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

    public void initialize() {
        super.initialize();
        sortMovies();
        repository.subscribe(ADD_TO_WATCHLIST, this);
        repository.subscribe(ALREADY_ON_WATCHLIST, this);

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setValue(Genre.NO_FILTER);

        releaseYearPicker.getItems().addAll(Year.values());
        releaseYearPicker.setValue(Year.NO_FILTER);

        ratingComboBox.getItems().addAll(Rating.values());
        ratingComboBox.setValue(Rating.NO_FILTER);

        searchBtn.setOnAction(actionEvent -> setFilter());

        onWatchlistButtonClicked = (clickedItem) -> {
            try {
                if (repository == null) throw new DatabaseException(NO_DB_CONNECTION_AVAILABLE);
                repository.addToWatchlist(new WatchlistEntity(clickedItem));
                repository.notify(ADD_TO_WATCHLIST);
            } catch (DatabaseException e) {
                repository.notify(ALREADY_ON_WATCHLIST);
            }
        };

        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
                sortBtn.setText(SORT_DEFAULT_TEXT_DESC);
            } else {
                sortBtn.setText(SORT_DEFAULT_TEXT_ASC);
            }

            sortMovies();
        });

        watchlistButton.setOnMouseClicked(e -> {
            switchView();
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
        } catch (MovieApiException e) {
            showAlertMessage(e.getMessage());
            moviesWithFilter = new ArrayList<>();
        }

        movies.addAll(moviesWithFilter);
        sortMovies();
    }

    private void sortMovies() {
        if (sortBtn.getText().equals(SORT_DEFAULT_TEXT_ASC)) {
            movies.sort(Collections.reverseOrder());
        } else {
            movies.sort(Comparator.naturalOrder());
        }
    }

    protected List<Movie> getAllMoviesOrEmptyList() {
        List<Movie> allMovies;

        try {
            allMovies = MovieAPIService.getMovies();
        } catch (MovieApiException e) {
            showAlertMessage(e.getMessage());
            allMovies = new ArrayList<>();
        }

        return allMovies;
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

    public void switchView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("/at/ac/fhcampuswien/fhmdb/watchlist-view.fxml"));
        renderScene(fxmlLoader, parent);

    }

    @Override
    public void update(EventType eventType) {
        switch (eventType) {
            case ADD_TO_WATCHLIST -> showInfoMessage(eventType.getDescription());
            case ALREADY_ON_WATCHLIST -> showAlertMessage(eventType.getDescription());
        }
    }
}