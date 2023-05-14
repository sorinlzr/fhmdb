package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.handler.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MovieCell extends ListCell<Movie> {

    private final ClickEventHandler<Movie> clickEventHandler;
    private final boolean isWatchlistCell;

    public MovieCell(ClickEventHandler<Movie> clickEventHandler, boolean isWatchlistCell) {
        super();
        this.clickEventHandler = clickEventHandler;
        this.isWatchlistCell = isWatchlistCell;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            Node graphic;
            MovieCellController controller;

            try {
                FXMLLoader loader = new FXMLLoader(MovieCell.class.getResource("movie-cell.fxml"));
                graphic = loader.load();
                controller = loader.getController();

                controller.getAddToWatchlistButton().setOnMouseClicked(mouseEvent -> clickEventHandler.onClick(getItem()));
                controller.getRemoveFromWatchlistButton().setOnMouseClicked(mouseEvent -> clickEventHandler.onClick(getItem()));
                controller.setTitle(movie.getTitle());
                controller.setDescription(movie.getDescription());
                controller.setGenre(movie.getGenres());
                controller.setYear(movie.getReleaseYear());
                controller.setRuntime(movie.getLengthInMinutes());
                controller.setRating(movie.getRating());
                controller.setWriters(movie.getWriters());
                controller.setDirectors(movie.getDirectors());
                controller.setMainCast(movie.getMainCast());
                if (isWatchlistCell) controller.addToWatchlist();
                setGraphic(graphic);
            } catch (IOException e) {
                System.out.println("Error rendering movie cell");
                e.printStackTrace();
            }
        }
    }
}

