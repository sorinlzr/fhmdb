package at.ac.fhcampuswien.fhmdb.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MovieCellController {
    @FXML
    private Label title;

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
