package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 620);
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/ac/fhcampuswien/fhmdb/ui/icons/fhmdb.png"))));
        stage.show();
    }

    @Override
    public void stop() {
        try {
           Database.closeConnection();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(e.getMessage());

            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}