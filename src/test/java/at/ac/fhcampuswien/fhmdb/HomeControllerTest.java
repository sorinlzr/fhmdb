package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.filter.Genre;
import at.ac.fhcampuswien.fhmdb.filter.Rating;
import at.ac.fhcampuswien.fhmdb.filter.Year;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.service.MovieAPIService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
    private static List<Movie> movies;
    private static HomeController homeController;
    private static MockedStatic<MovieAPIService> movieAPIServiceMockedStatic;
    private static List<Movie> streamTestMovies;

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

        movies = new ArrayList<>() {{
            add(movie1);
            add(movie2);
            add(movie3);
            add(movie4);
            add(movie5);
        }};

        movie1.setMainCast(List.of("Bruce Willis", "Alan Rickman"));
        movie1.setDirectors(List.of("John McTiernan"));
        movie1.setReleaseYear(1988);

        movie2.setMainCast(List.of("Jackie Chan", "Chris Tucker"));
        movie2.setDirectors(List.of("Brett Ratner"));
        movie2.setReleaseYear(1998);

        movie3.setMainCast(List.of("Will Smith", "Bill Pullman"));
        movie3.setDirectors(List.of("Roland Emmerich"));
        movie3.setReleaseYear(1996);

        movie4.setMainCast(List.of("Chris Pine", "Zachary Quinto"));
        movie4.setDirectors(List.of("J.J. Abrams"));
        movie4.setReleaseYear(2009);

        movie5.setMainCast(List.of("Elijah Wood", "Zachary Quinto"));
        movie5.setDirectors(List.of("Peter Jackson"));
        movie5.setReleaseYear(2001);

        streamTestMovies = List.of(movie1, movie2, movie3, movie4, movie5);
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
    class GetMostPopularActor {
        @Test
        void Gets_the_right_actor() {
            // Arrange takes place is in beforeAll

            // Act
            String mostPopularActor = homeController.getMostPopularActor(streamTestMovies);

            // Assert
            assertEquals("Zachary Quinto", mostPopularActor);
        }
    }

    @Nested
    class GetLongestMovieTitle {
        @Test
        void Gets_the_correct_length() {
            // Arrange is in beforeAll

            // Act
            int longestMovieTitle = homeController.getLongestMovieTitle(streamTestMovies);

            // Assert
            assertEquals(17, longestMovieTitle);
        }
    }

    @Nested
    class CountMoviesFromSelectedDirector {
        @Test
        void Counts_correctly_for_existing_director() {
            // Arrange takes place is in beforeAll

            // Act
            long moviesFromJohnMcTiernan = homeController.countMoviesFrom(streamTestMovies, "John McTiernan");

            // Assert
            assertEquals(1, moviesFromJohnMcTiernan);
        }

        @Test
        void Counts_correctly_for_non_existent_director() {
            // Arrange takes place is in beforeAll

            // Act
            long moviesFromNonExistentDirector = homeController.countMoviesFrom(streamTestMovies, "Non-existent Director");

            // Assert
            assertEquals(0, moviesFromNonExistentDirector);
        }
    }

    @Nested
    class GetMoviesBetweenYears {
        @Test
        void Gets_correct_movies_between_1988_and_1998() {
            // Arrange takes place is in beforeAll

            // Act
            List<Movie> moviesBetween1988And1998 = homeController.getMoviesBetweenYears(streamTestMovies, 1988, 1998);

            // Assert
            assertEquals(3, moviesBetween1988And1998.size());
        }

        @Test
        void Gets_correct_movies_between_2000_and_2010() {
            // Arrange takes place is in beforeAll

            // Act
            List<Movie> moviesBetween2000And2010 = homeController.getMoviesBetweenYears(streamTestMovies, 2000, 2010);

            // Assert
            assertEquals(2, moviesBetween2000And2010.size());
        }

        @Test
        void Gets_correct_movies_between_2020_and_2030() {
            // Arrange takes place is in beforeAll

            // Act
            List<Movie> moviesBetween2020And2030 = homeController.getMoviesBetweenYears(streamTestMovies, 2020, 2030);

            // Assert
            assertEquals(0, moviesBetween2020And2030.size());
        }
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
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenReturn(movies);

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(5, ((ObservableList<Movie>) movies.get(homeController)).size());
        }

        @Test
        void Sets_movies_to_an_empty_list_if_the_api_throws_an_exception() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
            //Arrange
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenThrow(new IOException());

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method initialize = HomeController.class.getDeclaredMethod("initialize");
            initialize.setAccessible(true);

            //Act
            initialize.invoke(homeController);

            //Assert
            assertEquals(0, ((List<Movie>) movies.get(homeController)).size());
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
            movieAPIServiceMockedStatic.when(MovieAPIService::getMovies).thenReturn(movies);

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

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(5, ((ObservableList<Movie>) movies.get(homeController)).size());
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

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method resetFilter = HomeController.class.getDeclaredMethod("resetFilter");
            resetFilter.setAccessible(true);

            //Act
            resetFilter.invoke(homeController);

            //Assert
            assertEquals(0, ((ObservableList<Movie>) movies.get(homeController)).size());
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
            movieAPIServiceMockedStatic.when(() -> MovieAPIService.getMoviesBy(anyString(), anyString(), anyString(), anyString())).thenReturn(movies);

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

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method setFilter = HomeController.class.getDeclaredMethod("setFilter");
            setFilter.setAccessible(true);

            //Act
            setFilter.invoke(homeController);

            //Assert
            assertEquals(5, ((ObservableList<Movie>) movies.get(homeController)).size());
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

            Field movies = HomeController.class.getDeclaredField("movies");
            movies.setAccessible(true);

            Method setFilter = HomeController.class.getDeclaredMethod("setFilter");
            setFilter.setAccessible(true);

            //Act
            setFilter.invoke(homeController);

            //Assert
            assertEquals(0, ((ObservableList<Movie>) movies.get(homeController)).size());
        }
    }
}