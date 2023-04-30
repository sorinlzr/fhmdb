package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class MovieCell extends ListCell<Movie> {
    private Node graphic;
    private MovieCellController controller;

    {
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
            setGraphic(graphic);
        }
    }

//    private static final String TEXT_WHITE = "text-white";
//    private static final String TEXT_YELLOW = "text-yellow";
//    private final Label title = new Label();
//    private final Label detail = new Label();
//    private final Label genre = new Label();
//    private final Label movieDetails = new Label();
//    private final Label producingCast = new Label();
//    private final Label directors = new Label();
//    private final Label mainCast = new Label();
//    private final VBox layout = new VBox(title, detail, genre, movieDetails, producingCast, mainCast);
//
//    @Override
//    protected void updateItem(Movie movie, boolean empty) {
//        super.updateItem(movie, empty);
//
//        if (empty || movie == null) {
//            setText(null);
//            setGraphic(null);
//        } else {
//            this.getStyleClass().add("movie-cell");
//            title.setText(movie.getTitle());
//            detail.setText(
//                    movie.getDescription() != null
//                            ? movie.getDescription()
//                            : "No description available"
//            );
//
//            genre.setText(movie.getGenres().stream().map(Enum::name).sorted().reduce((a, b) -> a + ", " + b).orElse(""));
//            genre.getStyleClass().add("text-genre");
//            movieDetails.setText("Year: " + movie.getReleaseYear() + " • Runtime: " + movie.getLengthInMinutes() + " min • Rating: " + movie.getRating());
//            producingCast.setText("Writers: " + formatListOfNames(movie.getWriters())
//                            + " • Directors: " + formatListOfNames(movie.getDirectors()));
//            mainCast.setText("Main Cast: " + formatListOfNames(movie.getMainCast()));
//
//            // color scheme
//            title.getStyleClass().add(TEXT_YELLOW);
//            detail.getStyleClass().add(TEXT_WHITE);
//            movieDetails.getStyleClass().add(TEXT_WHITE);
//            producingCast.getStyleClass().add(TEXT_WHITE);
//            directors.getStyleClass().add(TEXT_WHITE);
//            mainCast.getStyleClass().add(TEXT_WHITE);
//            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
//
//            // layout
//            title.fontProperty().set(title.getFont().font(20));
//            detail.setMaxWidth(this.getScene().getWidth() - 30);
//            detail.setWrapText(true);
//            layout.setPadding(new Insets(10));
//            layout.spacingProperty().set(10);
//            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
//            setGraphic(layout);
//        }
//    }
//
//    private String formatListOfNames(List<String> list) {
//        return list.stream().sorted().reduce((a, b) -> a + ", " + b).orElse("");
//    }
}

