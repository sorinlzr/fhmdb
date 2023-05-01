package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.filter.Genre;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class MovieCellController {
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label genre;
    @FXML
    private Label year;
    @FXML
    private Label runtime;
    @FXML
    private Label rating;
    @FXML
    private Label writers;
    @FXML
    private Label directors;
    @FXML
    private Label mainCast;
    @FXML
    private JFXButton detailsButton;
    @FXML
    private JFXButton watchlistButton;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setGenre(List<Genre> genres) {
        this.genre.setText(genres.stream().map(Enum::name).sorted().reduce((a, b) -> a + ", " + b).orElse(""));
    }

    public void setYear(int year) {
        this.year.setText("Year: " + year);
    }

    public void setRuntime(int runtime) {
        this.runtime.setText("Runtime: " + runtime + " min");
    }

    public void setRating(double rating) {
        this.rating.setText("Rating: " + rating);
    }

    public void setWriters(List<String> writers) {
        this.writers.setText("Writers: " + formatListOfNames(writers));
    }

    public void setDirectors(List<String> directors) {
        this.directors.setText("Directors: " + formatListOfNames(directors));
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast.setText("Main Cast: " + formatListOfNames(mainCast));
    }

    private String formatListOfNames(List<String> list) {
        return list.stream().sorted().reduce((a, b) -> a + ", " + b).orElse("");
    }
}
