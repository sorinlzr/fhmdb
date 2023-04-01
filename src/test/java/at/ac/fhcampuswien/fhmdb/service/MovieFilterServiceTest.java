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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovieFilterServiceTest {

    static Movie movie1;
    static Movie movie2;
    static Movie movie3;
    static Movie movie4;
    static Movie movie5;
    static ObservableList<Movie> observableMovies;
    static FilteredList<Movie> filteredMovies;

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

        movie1 = new Movie("1","Die Hard", "A story about a man who can't seem to die");
        movie1.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.DRAMA);
        }});

        movie2 = new Movie("2","Rush Hour", "Two detectives, one throws punches as fast as the other one can talk");
        movie2.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.COMEDY);
        }});

        movie3 = new Movie("3","Independence Day", "A movie inspired by true events");
        movie3.setGenres(new ArrayList<>() {{
            add(Genre.SCIENCE_FICTION);
            add(Genre.DOCUMENTARY);
        }});

        movie4 = new Movie("4","Star Trek", "Beam me up, Scotty! - Ay ay captain!");
        movie4.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.SCIENCE_FICTION);
        }});

        movie5 = new Movie("5","Lord of the rings", "A story of an odd group that spends around 9 hours returning jewelry");
        movie5.setGenres(new ArrayList<>() {{
            add(Genre.FANTASY);
            add(Genre.ADVENTURE);
        }});

        observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movie1, movie2, movie3, movie4, movie5);
        filteredMovies = new FilteredList<>(observableMovies);
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

            HomeController homeController = new HomeController();
            // TODO refactor test here
            homeController.searchAndFilterMovies();

            assertEquals(5, observableMovies.size());
            assertEquals(1, filteredMovies.size());
            assertEquals(searchFieldText, searchField.getText());

            // TODO refactor test here
            homeController.resetSearchAndFilterCriteria();
            assertTrue(genreComboBox.getSelectionModel().isEmpty());
            assertEquals(5, observableMovies.size());
            assertEquals(5, filteredMovies.size());
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
            assertEquals(5, filteredMovies.size());
        }

        @Test
        public void shouldSelectOnlySpecificGenre() {
            genreComboBox.setValue(action);
            assertEquals(5, observableMovies.size());

            MovieFilterService.filterMoviesByGenre(genreComboBox.getValue(), filteredMovies);
            assertEquals(3, filteredMovies.size());
        }
    }
}