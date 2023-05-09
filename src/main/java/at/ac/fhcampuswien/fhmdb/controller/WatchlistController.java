package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistController extends DefaultController {

    @FXML
    private StackPane parent;
    @FXML
    protected JFXButton homeButton;

    public WatchlistController() {
        this.onWatchlistButtonClicked = (clickedItem) -> {
            System.out.println("Movie removed from watchlist");
            // TODO add logic to remove from DB
        };
    }

    @Override
    public void initialize() {
        super.initialize();

        homeButton.setOnMouseClicked(e -> {
            System.out.println("Home view button clicked");
            showHomeView();
        });
    }

    @Override
    protected List<Movie> getAllMoviesOrEmptyList() {
        List<Movie> allMovies = new ArrayList<>();

        Movie movie2 = new Movie("2", "Rush Hour", "Two detectives, one throws punches as fast as the other one can talk");
        movie2.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.COMEDY);
        }});

        allMovies.add(movie2);

        return allMovies;
    }

    public void showHomeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("/at/ac/fhcampuswien/fhmdb/home-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Could not render scene");
        }

    }
}