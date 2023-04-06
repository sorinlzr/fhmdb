package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.filter.Rating;
import at.ac.fhcampuswien.fhmdb.filter.Year;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import kotlin.NotImplementedError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class HomeControllerTest {

    private static TextField searchField;
    private static JFXButton searchBtn;
    private static JFXButton resetFilterBtn;
    private static JFXButton sortBtn;
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
    void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
        homeController = new HomeController();

        HomeControllerTest.searchBtn = new JFXButton();
        HomeControllerTest.resetFilterBtn = new JFXButton();
        HomeControllerTest.sortBtn = new JFXButton();
        HomeControllerTest.searchField = new TextField();
        HomeControllerTest.genreComboBox = new JFXComboBox<>();
        HomeControllerTest.releaseYearPicker = new JFXComboBox<>();
        HomeControllerTest.ratingComboBox = new JFXComboBox<>();
        HomeControllerTest.movieListView = new JFXListView<>();

        HomeControllerTest.filteredMovies.setPredicate(null);

        Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
        filteredMovies.setAccessible(true);
        filteredMovies.set(homeController, HomeControllerTest.filteredMovies);

        Field searchBtn = HomeController.class.getDeclaredField("searchBtn");
        searchBtn.setAccessible(true);
        searchBtn.set(homeController, HomeControllerTest.searchBtn);

        Field resetFilterBtn = HomeController.class.getDeclaredField("resetFilterBtn");
        resetFilterBtn.setAccessible(true);
        resetFilterBtn.set(homeController, HomeControllerTest.resetFilterBtn);

        Field sortBtn = HomeController.class.getDeclaredField("sortBtn");
        sortBtn.setAccessible(true);
        sortBtn.set(homeController, HomeControllerTest.sortBtn);

        Field searchField = HomeController.class.getDeclaredField("searchField");
        searchField.setAccessible(true);
        searchField.set(homeController, HomeControllerTest.searchField);

        Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
        genreComboBox.setAccessible(true);
        genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

        Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
        releaseYearPicker.setAccessible(true);
        releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

        Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
        ratingComboBox.setAccessible(true);
        ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

        Field movieListView = HomeController.class.getDeclaredField("movieListView");
        movieListView.setAccessible(true);
        movieListView.set(homeController, HomeControllerTest.movieListView);
    }

    @Nested
    class Initialize {
        @Test
        void Sets_the_right_default_value_for_the_genre_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(Genre.NO_FILTER, ((JFXComboBox<Genre>)genreComboBox.get(homeController)).getValue());
        }

        @Test
        void Sets_the_right_default_value_for_the_release_year_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(Year.NO_FILTER, ((JFXComboBox<Integer>)releaseYearPicker.get(homeController)).getValue());
        }

        @Test
        void Sets_the_right_default_value_for_the_rating_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(Rating.NO_FILTER, ((JFXComboBox<Rating>)ratingComboBox.get(homeController)).getValue());
        }

        @Test
        void Sets_movies_when_the_api_returns_movies() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        @Test
        void Sets_movies_to_an_empty_list_if_the_api_throws_an_exception() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
            //Arrange
            //TODO: Why does this not work?
            MovieAPIService mock = mock(MovieAPIService.class);
            Mockito.when(mock.getMovies()).thenThrow(new IOException());

            Field filteredList = HomeController.class.getDeclaredField("filteredMovies");
            filteredList.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(0, ((FilteredList<Movie>) filteredList.get(homeController)).size());
        }
    }

    @Nested
    class ResetFilter {
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

            Method searchAndFilterMovies = HomeController.class.getDeclaredMethod("setFilter");
            searchAndFilterMovies.setAccessible(true);

            searchAndFilterMovies.invoke(homeController);

            assertEquals(1, HomeControllerTest.filteredMovies.size());
            assertEquals(searchCriteria, ((TextField) searchField.get(homeController)).getText());

            Method resetSearchAndFilterCriteria = HomeController.class.getDeclaredMethod("resetFilter");
            resetSearchAndFilterCriteria.setAccessible(true);

            //Act
            resetSearchAndFilterCriteria.invoke(homeController);

            //Assert
            assertEquals(((JFXComboBox<Genre>) genreComboBox.get(homeController)).getValue(), Genre.NO_FILTER);
            assertEquals(5, HomeControllerTest.filteredMovies.size());
            assertEquals("", ((TextField) searchField.get(homeController)).getText());
        }
    }

    @Nested
    public class SetFilter {
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

                HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
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
                HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
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
                HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
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
                HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
                genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

                // Act
                searchAndFilterMovies.invoke(homeController);

                // Assert
                assertEquals(5, filteredMovies.size());
            }
        }
    }
}