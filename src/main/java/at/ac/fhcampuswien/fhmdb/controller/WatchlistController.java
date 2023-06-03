package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.subscription.EventType;
import at.ac.fhcampuswien.fhmdb.subscription.Observer;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static at.ac.fhcampuswien.fhmdb.subscription.EventType.REMOVE_FROM_WATCHLIST;

public class WatchlistController extends AbstractViewController implements Observer {

    @FXML
    private StackPane parent;
    @FXML
    protected JFXButton homeButton;

    @Override
    public void initialize() {
        this.isWatchlistCell = true;
        super.initialize();
        repository.subscribe(REMOVE_FROM_WATCHLIST, this);

        this.onWatchlistButtonClicked = (clickedItem) -> {
            try {
                repository.removeFromWatchlist(new WatchlistEntity(clickedItem));
                repository.notify(REMOVE_FROM_WATCHLIST);
                reloadView();
            } catch (DatabaseException e) {
                showInfoMessage(e.getMessage());
            }
        };

        homeButton.setOnMouseClicked(e -> {
            switchView();
        });
    }

    @Override
    protected List<Movie> getAllMoviesOrEmptyList() {
        List<Movie> allMovies = null;
        if (repository == null) return Collections.emptyList();

        try {
            allMovies = repository.getAll().stream().map(Movie::new).collect(Collectors.toList());
        } catch (DatabaseException e) {
            showInfoMessage(e.getMessage());
        }

        return allMovies;
    }

    public void reloadView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("/at/ac/fhcampuswien/fhmdb/watchlist-view.fxml"));
        renderScene(fxmlLoader, parent);
    }

    public void switchView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("/at/ac/fhcampuswien/fhmdb/home-view.fxml"));
        renderScene(fxmlLoader, parent);
    }

    @Override
    public void update(EventType eventType) {
        showInfoMessage(eventType.getDescription());
    }
}
