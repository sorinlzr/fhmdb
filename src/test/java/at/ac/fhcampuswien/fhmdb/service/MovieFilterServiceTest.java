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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovieFilterServiceTest {
    private static TextField searchField;
    private static JFXComboBox<Genre> genreComboBox;
    private static JFXComboBox<Integer> releaseYearPicker;
    private static JFXComboBox<Integer> ratingComboBox;
    private static JFXListView<Movie> movieListView;
    private static FilteredList<Movie> filteredMovies;
    private static HomeController homeController;

    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();

        // 5 movies in total
        // 3 movies with the same genre
        // 2 movies with the word "story" in their description
        // 1 movie with the word "story" and the genre "Action"
        // 1 movie with the word "story" and the genre "Fantasy"

        Movie movie1 = new Movie("1", "Die Hard", "A story about a man who can't seem to die");
        movie1.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.DRAMA);
        }});

        Movie movie2 = new Movie("2", "Rush Hour", "Two detectives, one throws punches as fast as the other one can talk");
        movie2.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.COMEDY);
        }});

        Movie movie3 = new Movie("3", "Independence Day", "A movie inspired by true events");
        movie3.setGenres(new ArrayList<>() {{
            add(Genre.SCIENCE_FICTION);
            add(Genre.DOCUMENTARY);
        }});

        Movie movie4 = new Movie("4", "Star Trek", "Beam me up, Scotty! - Ay ay captain!");
        movie4.setGenres(new ArrayList<>() {{
            add(Genre.ACTION);
            add(Genre.SCIENCE_FICTION);
        }});

        Movie movie5 = new Movie("5", "Lord of the rings", "A story of an odd group that spends around 9 hours returning jewelry");
        movie5.setGenres(new ArrayList<>() {{
            add(Genre.FANTASY);
            add(Genre.ADVENTURE);
        }});

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(movie1, movie2, movie3, movie4, movie5);
        filteredMovies = new FilteredList<>(observableMovies);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        homeController = new HomeController();

        MovieFilterServiceTest.searchField = new TextField();
        MovieFilterServiceTest.genreComboBox = new JFXComboBox<>();
        MovieFilterServiceTest.releaseYearPicker = new JFXComboBox<>();
        MovieFilterServiceTest.ratingComboBox = new JFXComboBox<>();
        MovieFilterServiceTest.movieListView = new JFXListView<>();

        MovieFilterServiceTest.filteredMovies.setPredicate(null);

        Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
        filteredMovies.setAccessible(true);
        filteredMovies.set(homeController, MovieFilterServiceTest.filteredMovies);
    }

    //TODO: Implement tests for the following methods
    //    @Nested
//    class SelectGenre{
//        Genre action = Genre.ACTION;
//
//        @Test
//        void selectionIsEmptyByDefault() {
//            assertTrue(genreComboBox.getSelectionModel().isEmpty());
//        }
//
//        @Test
//        void observableAndFilteredListContainAllMoviesByDefault() {
//            assertEquals(5, observableMovies.size());
//            assertEquals(5, filteredMovies.size());
//        }
//
//        @Test
//        void shouldSelectOnlySpecificGenre() {
//            genreComboBox.setValue(action);
//            assertEquals(5, observableMovies.size());
//
//            MovieFilterService.filterMoviesByGenre(genreComboBox.getValue(), filteredMovies);
//            assertEquals(3, filteredMovies.size());
//        }
//    }
//
    @Nested
    class FilterMoviesByGenre {
        @Test
        void ImplementSomeTestsHere() {
            throw new UnsupportedOperationException("Implement some tests here");
        }
    }
}