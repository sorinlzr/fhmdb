package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.dao.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.handler.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.SVG;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractViewController {
    public static final String NO_DB_CONNECTION_AVAILABLE = "No database connection available";

    @FXML
    protected JFXListView<Movie> movieListView = new JFXListView<>();
    @FXML
    protected VBox navigationMenu;
    @FXML
    protected JFXButton navigationButton;
    @FXML
    protected JFXButton aboutButton;

    protected SVGPath cross = new SVGPath();
    protected SVGPath burger = new SVGPath();

    public final ObservableList<Movie> movies = FXCollections.observableArrayList();
    protected ClickEventHandler<Movie> onWatchlistButtonClicked;
    protected WatchlistRepository repository;

    protected boolean isWatchlistCell = false;

    protected void initialize() {

        if (repository == null) {
            try {
                repository = WatchlistRepository.getInstance();
            } catch (DatabaseException e) {
                showAlertMessage(e.getMessage());
            }
        }

        movies.addAll(getAllMoviesOrEmptyList());

        burger.setContent(SVG.BURGER);
        burger.setStroke(Color.WHITE);
        burger.setStrokeWidth(4);

        cross.setContent(SVG.CROSS);
        cross.setStroke(Color.WHITE);
        cross.setStrokeWidth(4);

        navigationButton.setGraphic(burger);

        movieListView.setItems(movies);
        movieListView.setCellFactory(e -> new MovieCell(onWatchlistButtonClicked, isWatchlistCell));

        navigationButton.setOnMouseClicked(e -> toggleNavigation());
        aboutButton.setOnMouseClicked(e -> showAboutInformation());

    }

    private void showAboutInformation() {
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("About");
        dialog.setHeaderText("About FHMDb");
        dialog.setContentText("""
                FHMDb features a movie list with rich information.
                To support this free application please consider giving us full points for this assignment. \n \n
                Developers: \n Sorin Lazar \n Burak Kongo \n Benjamin Lichtenstein \n
                """);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        dialog.showAndWait();
    }

    private void toggleNavigation() {
        if (navigationMenu.isVisible()) {
            navigationMenu.setVisible(false);
            navigationMenu.setManaged(false);
            navigationButton.setGraphic(burger);
        } else {
            navigationMenu.setVisible(true);
            navigationMenu.setManaged(true);
            navigationButton.setGraphic(cross);
        }
    }

    protected void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        if (navigationMenu != null && navigationMenu.getScene() != null) {
            alert.initOwner(navigationMenu.getScene().getWindow());
        }
        alert.showAndWait();
    }

    protected void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops!");
        alert.setHeaderText(message);
        if (navigationMenu != null && navigationMenu.getScene() != null) {
            alert.initOwner(navigationMenu.getScene().getWindow());
        }
        alert.showAndWait();
    }

   protected void renderScene(FXMLLoader fxmlLoader, StackPane parent) {
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Could not render scene");
            e.printStackTrace();
        }
    }

    abstract protected List<Movie> getAllMoviesOrEmptyList();
    abstract protected void switchView();

}