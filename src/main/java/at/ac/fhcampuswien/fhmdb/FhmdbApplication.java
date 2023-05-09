package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.dao.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FhmdbApplication extends Application {

    private Database database;
    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 620);
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();

        try {
            database = Database.getInstance();
            //Testing to see if the data is persisted in the DB
            Movie movie1 = new Movie("4", "Die Hard_4", "A movie about a hard death.");
            movie1.setId ("a734d21d-43b7-4f90-b390-2131231");
            WatchlistEntity watchlistEntity = new WatchlistEntity (movie1);
            WatchlistRepository dao = new WatchlistRepository();
            System.out.println ("DB object should have been created");
            dao.addToWatchlist (watchlistEntity);
            List<WatchlistEntity> items = dao.getAll();
            System.out.println ("List contains " + items.size () + " elements");
        } catch (DatabaseException e) {
            System.out.println("------- WARNING: " + e.getMessage());
            // Show exception dialog
        }
    }

    @Override
    public void stop() {
        try {
            database.closeConnection();
        } catch (Exception e) {
            // Show exception dialog
        }
    }

    public static void main(String[] args) {
        launch();
    }
}