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

import java.util.ArrayList;

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

        movie1 = new Movie("1", "Die Hard", "A story about a man who can't seem to die");
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
    }

    @BeforeEach
    void setUp() {
        searchField = new TextField();
        genreComboBox = new JFXComboBox<>();
        filteredList = new FilteredList<>(observableMovies);
        homeController = new HomeController();
    }

    @Nested
    class GenreIsSelected {
        @Test
        void whenGenreIsSelected_thenFilteredListShouldContainOnlyMoviesWithThatGenre() {
            genreComboBox.setValue(Genre.ACTION);

//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);

            assertEquals(5, observableMovies.size());
            assertEquals(3, filteredList.size());
        }
    }
    @Nested
    class SearchFieldIsFilled {
        @Test
        void whenSearchFieldIsFilled_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            String searchCriteria = "story";
            searchField.setText(searchCriteria);

//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);

            assertEquals(5, observableMovies.size());
            assertEquals(2, filteredList.size());
            assertEquals(searchCriteria, searchField.getText());
        }
    }
    @Nested
    class GenreIsSelectedAndSearchFieldIsFilled {
        String searchCriteria = "story";

        @BeforeEach
        void setUp() {
            genreComboBox.setValue(Genre.ACTION);
            searchField.setText(searchCriteria);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
        }

        @Test
        void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() {
            assertEquals(5, observableMovies.size());
            assertEquals(searchCriteria, searchField.getText());
            for (Movie movie : filteredList) {
                assertTrue(movie.getGenres().contains(genreComboBox.getValue()));
            }
            assertEquals(1, filteredList.size());
        }

        @Test
        void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            assertEquals(5, observableMovies.size());
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(searchCriteria, searchField.getText());
            assertEquals(1, filteredList.size());
            genreComboBox.setValue(Genre.ALL);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(2, filteredList.size());
        }

        @Test
        void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            searchField.setText("");
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(3, filteredList.size());
        }

        @Test
        void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() {
            searchField.setText("");
            genreComboBox.setValue(Genre.ALL);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(5, filteredList.size());
        }
    }
    @Nested
    class SearchFieldIsFilledAndGenreIsSelected {
        String searchCriteria = "story";
        Genre genre = Genre.ACTION;

        @BeforeEach
        void setUp() {
            searchField.setText(searchCriteria);
            genreComboBox.setValue(genre);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
        }

        @Test
        void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() {
            assertEquals(5, observableMovies.size());
            assertEquals(searchCriteria, searchField.getText());
            for (Movie movie : filteredList) {
                assertTrue(movie.getGenres().contains(genreComboBox.getValue()));
            }
            assertEquals(1, filteredList.size());
        }

        @Test
        void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            assertEquals(5, observableMovies.size());
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(searchCriteria, searchField.getText());
            assertEquals(1, filteredList.size());
            genreComboBox.setValue(Genre.ALL);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(2, filteredList.size());
        }

        @Test
        void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            searchField.setText("");
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(3, filteredList.size());
        }

        @Test
        void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() {
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(1, filteredList.size());
            searchField.setText("");
            genreComboBox.setValue(Genre.ALL);
//            HomeController.searchForMovie(searchField.getText(), genreComboBox.getValue(), filteredList, observableMovies);
            assertEquals(5, filteredList.size());
        }
    }
}