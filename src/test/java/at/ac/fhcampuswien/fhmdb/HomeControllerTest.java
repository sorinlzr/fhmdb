package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeControllerTest {

    static Movie movie1;
    static Movie movie2;
    static Movie movie3;
    static Movie movie4;
    static Movie movie5;
    static ObservableList<Movie> observableMovies;

    public HomeController homeController;

    public TextField searchField;
    public JFXComboBox<Genre> genreComboBox;
    public FilteredList<Movie> filteredList;

    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();

        // 5 movies in total
        // 3 movies with the same genre
        // 2 movies with the word "story" in their description
        // 1 movie with the word "story" and the genre "Action"

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
    }

    @BeforeEach
    void setUp() {
        searchField = new TextField();
        genreComboBox = new JFXComboBox<>();
        filteredList = new FilteredList<>(observableMovies);
        homeController = new HomeController();
    }

    @Nested
    public class GenreIsSelected {
        @Test
        public void whenGenreIsSelected_thenFilteredListShouldContainOnlyMoviesWithThatGenre() {
            genreComboBox.setValue(Genre.ACTION);

            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);

            assertEquals(5, observableMovies.size());
            assertEquals(3, filteredList.size());
        }
    }

    @Nested
    public class SearchFieldIsFilled {
        @Test
        public void whenSearchFieldIsFilled_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            String searchCriteria = "story";
            searchField.setText(searchCriteria);

            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);

            assertEquals(5, observableMovies.size());
            assertEquals(2, filteredList.size());
            assertEquals(searchCriteria, searchField.getText());
        }
    }

    @Nested
    public class GenreIsSelectedAndSearchFieldIsFilled {
        String searchCriteria = "story";

        @BeforeEach
        void setUp() {
            genreComboBox.setValue(Genre.ACTION);
            searchField.setText(searchCriteria);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
        }

        @Test
        public void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() {
            assertEquals(5, observableMovies.size());
            assertEquals(searchCriteria, searchField.getText());
            for (Movie movie : filteredList) {
                assertTrue(movie.getGenres().contains(genreComboBox.getValue()));
            }
            assertEquals(1, filteredList.size());
        }

        @Test
        public void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            assertEquals(5, observableMovies.size());
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(searchCriteria, searchField.getText());
            assertEquals(1, filteredList.size());
            genreComboBox.setValue(Genre.ALL);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(2, filteredList.size());
        }

        @Test
        public void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            searchField.setText("");
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(3, filteredList.size());
        }

        @Test
        public void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() {
            searchField.setText("");
            genreComboBox.setValue(Genre.ALL);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(5, filteredList.size());
        }
    }

    @Nested
    public class SearchFieldIsFilledAndGenreIsSelected {
        String searchCriteria = "story";
        Genre genre = Genre.ACTION;

        @BeforeEach
        void setUp() {
            searchField.setText(searchCriteria);
            genreComboBox.setValue(genre);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
        }

        @Test
        public void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() {
            assertEquals(5, observableMovies.size());
            assertEquals(searchCriteria, searchField.getText());
            for (Movie movie : filteredList) {
                assertTrue(movie.getGenres().contains(genreComboBox.getValue()));
            }
            assertEquals(1, filteredList.size());
        }

        @Test
        public void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            assertEquals(5, observableMovies.size());
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(searchCriteria, searchField.getText());
            assertEquals(1, filteredList.size());
            genreComboBox.setValue(Genre.ALL);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(2, filteredList.size());
        }

        @Test
        public void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            searchField.setText("");
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(3, filteredList.size());
        }

        @Test
        public void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() {
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(1, filteredList.size());
            searchField.setText("");
            genreComboBox.setValue(Genre.ALL);
            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(5, filteredList.size());
        }
    }

}