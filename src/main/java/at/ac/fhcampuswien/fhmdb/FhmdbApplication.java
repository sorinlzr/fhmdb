package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {

    private Database database;
    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";

    @Override
    public void start(Stage stage) throws IOException, DatabaseException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();

        try {
            database = Database.getInstance();
        } catch (DatabaseException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
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