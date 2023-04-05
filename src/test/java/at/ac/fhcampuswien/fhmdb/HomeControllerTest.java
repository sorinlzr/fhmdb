package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeControllerTest {

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

        HomeControllerTest.searchField = new TextField();
        HomeControllerTest.genreComboBox = new JFXComboBox<>();
        HomeControllerTest.releaseYearPicker = new JFXComboBox<>();
        HomeControllerTest.ratingComboBox = new JFXComboBox<>();
        HomeControllerTest.movieListView = new JFXListView<>();

        HomeControllerTest.filteredMovies.setPredicate(null);

        Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
        filteredMovies.setAccessible(true);
        filteredMovies.set(homeController, HomeControllerTest.filteredMovies);
    }

    @Nested
    class ResetSearchAndFilterCriteria {
        @Test
        void shouldResetSelectionAndClearInputField() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            //Arrange
            String searchCriteria = "story";

            HomeControllerTest.searchField.setText(searchCriteria);
            HomeControllerTest.genreComboBox.setValue(Genre.ACTION);

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            searchField.set(homeController, HomeControllerTest.searchField);

            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method searchAndFilterMovies = HomeController.class.getDeclaredMethod("searchAndFilterMovies");
            searchAndFilterMovies.setAccessible(true);

            searchAndFilterMovies.invoke(homeController);

            assertEquals(1, HomeControllerTest.filteredMovies.size());
            assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());

            Method resetSearchAndFilterCriteria = HomeController.class.getDeclaredMethod("resetSearchAndFilterCriteria");
            resetSearchAndFilterCriteria.setAccessible(true);

            //Act
            resetSearchAndFilterCriteria.invoke(homeController);

            //Assert
            assertTrue(((JFXComboBox<Genre>) genreComboBox.get(homeController)).getSelectionModel().isEmpty());
            assertEquals(5, HomeControllerTest.filteredMovies.size());
            assertEquals("", ((TextField) searchField.get(homeController)).getText());
        }
    }

    @Nested
    public class SearchAndFilterMovies {
        private static Method searchAndFilterMovies;

        @BeforeAll
        public static void beforeAll() throws NoSuchMethodException {
            searchAndFilterMovies = HomeController.class.getDeclaredMethod("searchAndFilterMovies");
            searchAndFilterMovies.setAccessible(true);
        }

        @Nested
        class GenreIsSelected {
            @Test
            void whenGenreIsSelected_thenFilteredListShouldContainOnlyMoviesWithThatGenre() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
                // Arrange
                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(Genre.ACTION);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                searchField.set(homeController, HomeControllerTest.searchField);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(3, HomeControllerTest.filteredMovies.size());
            }
        }

        @Nested
        class SearchFieldIsFilled {
            @Test
            void whenSearchFieldIsFilled_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
                // Arrange
                String searchCriteria = "story";

                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText(searchCriteria);
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(2, HomeControllerTest.filteredMovies.size());
                assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());
            }
        }

        @Nested
        class GenreIsSelectedAndSearchFieldIsFilled {
            private final String searchCriteria = "story";

            @BeforeEach
            void setUp() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText(searchCriteria);
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(Genre.ACTION);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                searchAndFilterMovies.invoke(homeController);
            }

            @Test
            void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() throws NoSuchFieldException, IllegalAccessException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act in Setup

                // Assert
                assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());

                for (Movie movie : filteredMovies) {
                    assertTrue(movie.getGenres().contains(((JFXComboBox<Genre>) genreComboBox.get(homeController)).getValue()));
                }

                assertEquals(1, filteredMovies.size());
            }

            @Test
            void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());
                assertEquals(1, HomeControllerTest.filteredMovies.size());

                HomeControllerTest.genreComboBox.setValue(Genre.ALL);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(2, filteredMovies.size());
            }

            @Test
            void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText("");
                searchField.set(homeController, HomeControllerTest.searchField);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(3, filteredMovies.size());
            }

            @Test
            void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText("");
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(Genre.ALL);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(5, filteredMovies.size());
            }
        }

        @Nested
        class SearchFieldIsFilledAndGenreIsSelected {
            private final String searchCriteria = "story";
            private final Genre genre = Genre.ACTION;

            @BeforeEach
            void setUp() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText(searchCriteria);
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(genre);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                searchAndFilterMovies.invoke(homeController);
            }

            @Test
            void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() throws NoSuchFieldException, IllegalAccessException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);

                // Act in Setup

                // Assert
                assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());

                for (Movie movie : filteredMovies) {
                    assertTrue(movie.getGenres().contains(((JFXComboBox<Genre>) genreComboBox.get(homeController)).getValue()));
                }

                assertEquals(1, filteredMovies.size());
            }

            @Test
            void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);

                assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());
                assertEquals(1, filteredMovies.size());

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(Genre.ALL);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(2, filteredMovies.size());
            }

            @Test
            void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
                // Arrange
                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText("");
                searchField.set(homeController, HomeControllerTest.searchField);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(3, filteredMovies.size());
            }

            @Test
            void whenSearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
                // Arrange
                assertEquals(1, filteredMovies.size());

                Field searchField = HomeController.class.getDeclaredField("searchField");
                searchField.setAccessible(true);
                HomeControllerTest.searchField.setText("");
                searchField.set(homeController, HomeControllerTest.searchField);

                Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
                genreComboBox.setAccessible(true);
                HomeControllerTest.genreComboBox.setValue(Genre.ALL);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(5, filteredMovies.size());
            }
        }
    }
}