package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.TestBase;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieFilterServiceTest {

    static Movie movie1;
    static Movie movie2;
    static Movie movie3;
    static Movie movie4;
    static Movie movie5;
    static ObservableList<Movie> observableMovies;
    static FilteredList<Movie> filteredList;

    public TextField searchField;
    public JFXComboBox<Genre> genreComboBox;
    public JFXListView<Movie> movieListView;

    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();

        // 5 movies in total
        // 3 movies with the same genre
        // 2 movies with the word "story" in their description
        // 1 movie with the word "story" and the genre "Action"
        // 1 movie with the word "story" and the genre "Fantasy"

        movie1 = new Movie("Die Hard", "A story about a man who can't seem to die");
        movie1.addGenre(Genre.ACTION);
        movie1.addGenre(Genre.DRAMA);

        movie2 = new Movie("Rush Hour", "Two detectives, one throws punches as fast as the other one can talk");
        movie2.addGenre(Genre.ACTION);
        movie2.addGenre(Genre.COMEDY);

        movie3 = new Movie("Independence Day", "A movie inspired by true events");
        movie3.addGenre(Genre.SCIENCE_FICTION);
        movie3.addGenre(Genre.DOCUMENTARY);

        movie4 = new Movie("Star Trek", "Beam me up, Scotty! - Ay ay captain!");
        movie4.addGenre(Genre.ACTION);
        movie4.addGenre(Genre.SCIENCE_FICTION);

        movie5 = new Movie("Lord of the rings", "A story of an odd group that spends around 9 hours returning jewelry");
        movie5.addGenre(Genre.FANTASY);
        movie5.addGenre(Genre.ADVENTURE);

        observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movie1, movie2, movie3, movie4, movie5);
        filteredList = new FilteredList<>(observableMovies);
    }

    @BeforeEach
    void setUp() {
        searchField = new TextField();
        genreComboBox = new JFXComboBox<>();
        movieListView = new JFXListView<>();
    }

    @Nested
    public class ResetFilterCriteria {
        String searchFieldText = "story";

        @Test
        public void shouldResetSelectionAndClearInputField() {
            searchField.setText(searchFieldText);
            genreComboBox.setValue(Genre.ACTION);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(5, observableMovies.size());
            assertEquals(1, filteredList.size());
            assertEquals(searchFieldText, searchField.getText());

            HomeController.resetFilterCriteria(genreComboBox, filteredList, searchField);
            assertTrue(genreComboBox.getSelectionModel().isEmpty());
            assertEquals(5, observableMovies.size());
            assertEquals(5, filteredList.size());
            assertEquals("", searchField.getText());
        }

    }

    @Nested
    public class SelectGenre{
        Genre action = Genre.ACTION;

        @Test
        public void selectionIsEmptyByDefault() {
            assertTrue(genreComboBox.getSelectionModel().isEmpty());
        }

        @Test
        public void observableAndFilteredListContainAllMoviesByDefault() {
            assertEquals(5, observableMovies.size());
            assertEquals(5, filteredList.size());
        }

        @Test
        public void shouldSelectOnlySpecificGenre() {
            genreComboBox.setValue(action);
            assertEquals(5, observableMovies.size());
            List<Movie> filteredMovies = MovieFilterService.filterMoviesByGenre(genreComboBox.getValue(), observableMovies);
            assertEquals(3, filteredMovies.size());
        }
    }
}