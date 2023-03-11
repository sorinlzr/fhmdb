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

import static org.junit.jupiter.api.Assertions.*;

class MovieFilterServiceTest {

    static Movie movie1;
    static Movie movie2;
    static Movie movie3;
    static Movie movie4;
    static Movie movie5;
    static ObservableList<Movie> observableMovies;

    public TextField searchField;
    public JFXComboBox<Genre> genreComboBox;
    public JFXListView<Movie> movieListView;
    public FilteredList<Movie> filteredList;

    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();

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

        movie5 = new Movie("Lord of the rings", "Odd group spends around 9 hours returning jewelry");
        movie5.addGenre(Genre.FANTASY);
        movie5.addGenre(Genre.ADVENTURE);

        observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movie1, movie2, movie3, movie4, movie5);
    }

    @BeforeEach
    void setUp() {
        searchField = new TextField();
        genreComboBox = new JFXComboBox<>();
        movieListView = new JFXListView<>();
        filteredList = new FilteredList<>(observableMovies);
    }

    @Nested
    public class ResetFilterCriteria {
        @Test
        public void shouldResetSelectionAndClearInputField() {
            searchField.setText("test");
            genreComboBox.setValue(Genre.ACTION);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            assertEquals(5, observableMovies.size());
            assertEquals(3, filteredList.size());
            assertEquals("test", searchField.getText());

            HomeController.resetFilterCriteria(genreComboBox, filteredList, searchField);
            assertTrue(genreComboBox.getSelectionModel().isEmpty());
            assertEquals(5, observableMovies.size());
            assertEquals(5, filteredList.size());
            assertEquals("", searchField.getText());
        }

    }

    @Nested
    public class SelectGenre{
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
            genreComboBox.setValue(Genre.ACTION);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            assertEquals(5, observableMovies.size());
            assertEquals(3, filteredList.size());
        }

        @Test
        public void shouldSelectOnlySpecificGenreAfterChangingSelection() {
            genreComboBox.setValue(Genre.ACTION);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            genreComboBox.setValue(Genre.SCIENCE_FICTION);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            assertEquals(5, observableMovies.size());
            assertEquals(2, filteredList.size());
        }

        @Test
        public void shouldSelectAllMoviesAfterChangingSelectionToAll() {
            genreComboBox.setValue(Genre.ACTION);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            genreComboBox.setValue(Genre.ALL);
            MovieFilterService.selectSpecificGenre(genreComboBox.getValue(), filteredList);
            assertEquals(5, observableMovies.size());
            assertEquals(5, filteredList.size());
        }
    }
}