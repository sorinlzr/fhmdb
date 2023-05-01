package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MovieCell extends ListCell<Movie> {
    private final Node graphic;
    private final MovieCellController controller;

    public MovieCell() {
        try {
            FXMLLoader loader = new FXMLLoader(MovieCell.class.getResource("movie-cell.fxml"));
            graphic = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            controller.setTitle(movie.getTitle());
            controller.setDescription(movie.getDescription());
            controller.setGenre(movie.getGenres());
            controller.setYear(movie.getReleaseYear());
            controller.setRuntime(movie.getLengthInMinutes());
            controller.setRating(movie.getRating());
            controller.setWriters(movie.getWriters());
            controller.setDirectors(movie.getDirectors());
            controller.setMainCast(movie.getMainCast());
            setGraphic(graphic);
        }
    }
}

