package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.filter.Rating;
import at.ac.fhcampuswien.fhmdb.filter.Year;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

class HomeControllerTest {

    private static TextField searchField;
    private static JFXButton searchBtn;
    private static JFXButton resetFilterBtn;
    private static JFXButton sortBtn;
    private static JFXComboBox<Genre> genreComboBox;
    private static JFXComboBox<Year> releaseYearPicker;
    private static JFXComboBox<Rating> ratingComboBox;
    private static JFXListView<Movie> movieListView;
    private static FilteredList<Movie> filteredMovies;
    private static HomeController homeController;
    private static MockedStatic<MovieAPIService> movieAPIServiceMockedStatic;

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

        HomeControllerTest.searchBtn = new JFXButton();
        HomeControllerTest.resetFilterBtn = new JFXButton();
        HomeControllerTest.sortBtn = new JFXButton();
        HomeControllerTest.searchField = new TextField();
        HomeControllerTest.genreComboBox = new JFXComboBox<>();
        HomeControllerTest.releaseYearPicker = new JFXComboBox<>();
        HomeControllerTest.ratingComboBox = new JFXComboBox<>();
        HomeControllerTest.movieListView = new JFXListView<>();

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

        movieAPIServiceMockedStatic = Mockito.mockStatic(MovieAPIService.class);
    }

    @AfterEach
    void tearDown() {
        movieAPIServiceMockedStatic.close();
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
            assertEquals(Genre.NO_FILTER, ((JFXComboBox<Genre>) genreComboBox.get(homeController)).getValue());
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
            assertEquals(Year.NO_FILTER, ((JFXComboBox<Year>) releaseYearPicker.get(homeController)).getValue());
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
            assertEquals(Rating.NO_FILTER, ((JFXComboBox<Rating>) ratingComboBox.get(homeController)).getValue());
        }

        @Test
        void Sets_movies_when_the_api_returns_movies() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenReturn(filteredMovies);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(5, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }

        @Test
        void Sets_movies_to_an_empty_list_if_the_api_throws_an_exception() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenThrow(new IOException());

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(0, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }
    }

    @Nested
    class ResetFilter {
        @Test
        void Sets_the_right_default_value_for_the_genre_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            HomeControllerTest.genreComboBox.setValue(Genre.ACTION);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(Genre.NO_FILTER, ((JFXComboBox<Genre>) genreComboBox.get(homeController)).getValue());
        }

        @Test
        void Sets_the_right_default_value_for_the_release_year_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);
            HomeControllerTest.releaseYearPicker.setValue(Year.YEAR_1982);
            releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(Year.NO_FILTER, ((JFXComboBox<Year>) releaseYearPicker.get(homeController)).getValue());
        }

        @Test
        void Sets_the_right_default_value_for_the_rating_filter() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);
            HomeControllerTest.ratingComboBox.setValue(Rating.RATING_5_TO_MAX);
            ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(Rating.NO_FILTER, ((JFXComboBox<Rating>) ratingComboBox.get(homeController)).getValue());
        }

        @Test
        void Resets_the_movie_selection_to_filled_movie_list_when_api_returns_movies() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenReturn(filteredMovies);

            String searchCriteria = "story";

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            HomeControllerTest.searchField.setText(searchCriteria);
            searchField.set(homeController, HomeControllerTest.searchField);

            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);
            HomeControllerTest.releaseYearPicker.setValue(Year.NO_FILTER);
            releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);
            HomeControllerTest.ratingComboBox.setValue(Rating.NO_FILTER);
            ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(5, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }

        @Test
        void Resets_the_movie_selection_to_empty_movie_list_when_api_throws_an_exception() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenThrow(new IOException());

            String searchCriteria = "story";

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            HomeControllerTest.searchField.setText(searchCriteria);
            searchField.set(homeController, HomeControllerTest.searchField);

            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);
            HomeControllerTest.releaseYearPicker.setValue(Year.NO_FILTER);
            releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);
            HomeControllerTest.ratingComboBox.setValue(Rating.NO_FILTER);
            ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(0, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }

        @Test
        void Clears_the_search_field() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            //Arrange
            String searchCriteria = "story";

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            HomeControllerTest.searchField.setText(searchCriteria);
            searchField.set(homeController, HomeControllerTest.searchField);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals("", ((TextField) searchField.get(homeController)).getText());
        }
    }

    @Nested
    class SetFilter {
        @Test
        void Sets_the_movie_selection_to_filled_movie_list_when_api_returns_movies() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            movieAPIServiceMockedStatic.when(() -> MovieAPIService.getMoviesBy(anyString(), anyString(), anyString(), anyString())).thenReturn(filteredMovies);

            String searchCriteria = "story";

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            HomeControllerTest.searchField.setText(searchCriteria);
            searchField.set(homeController, HomeControllerTest.searchField);

            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);
            HomeControllerTest.releaseYearPicker.setValue(Year.NO_FILTER);
            releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);
            HomeControllerTest.ratingComboBox.setValue(Rating.NO_FILTER);
            ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method setFilter = HomeController.class.getDeclaredMethod("setFilter");
            setFilter.setAccessible(true);

            //Act
            setFilter.invoke(homeController);

            //Assert
            assertEquals(5, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }

        @Test
        void Sets_the_movie_selection_to_empty_movie_list_when_api_throws_an_exception() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenThrow(new IOException());

            String searchCriteria = "story";

            Field searchField = HomeController.class.getDeclaredField("searchField");
            searchField.setAccessible(true);
            HomeControllerTest.searchField.setText(searchCriteria);
            searchField.set(homeController, HomeControllerTest.searchField);

            Field genreComboBox = HomeController.class.getDeclaredField("genreComboBox");
            genreComboBox.setAccessible(true);
            HomeControllerTest.genreComboBox.setValue(Genre.NO_FILTER);
            genreComboBox.set(homeController, HomeControllerTest.genreComboBox);

            Field releaseYearPicker = HomeController.class.getDeclaredField("releaseYearPicker");
            releaseYearPicker.setAccessible(true);
            HomeControllerTest.releaseYearPicker.setValue(Year.NO_FILTER);
            releaseYearPicker.set(homeController, HomeControllerTest.releaseYearPicker);

            Field ratingComboBox = HomeController.class.getDeclaredField("ratingComboBox");
            ratingComboBox.setAccessible(true);
            HomeControllerTest.ratingComboBox.setValue(Rating.NO_FILTER);
            ratingComboBox.set(homeController, HomeControllerTest.ratingComboBox);

            Field filteredMovies = HomeController.class.getDeclaredField("filteredMovies");
            filteredMovies.setAccessible(true);

            Method setFilter = HomeController.class.getDeclaredMethod("setFilter");
            setFilter.setAccessible(true);

            //Act
            setFilter.invoke(homeController);

            //Assert
            assertEquals(0, ((FilteredList<Movie>) filteredMovies.get(homeController)).size());
        }
    }
}