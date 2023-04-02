package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.TestBase;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.function.Predicate;

class MovieFilterServiceTest {
    private static FilteredList<Movie> filteredMovies;

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
    void beforeEach() {
        filteredMovies.setPredicate(null);
    }

    @Nested
    class FilterMoviesByGenre {

        @Test
        void Filtered_movies_contain_only_action_movies_when_the_filter_is_set_to_action() {
            // Arrange
            Genre genre = Genre.ACTION;

            // Act
            MovieFilterService.filterMoviesByGenre(genre, filteredMovies);

            // Assert
            Assertions.assertEquals(3, filteredMovies.size());
        }

        @Test
        void Filtered_movies_contain_no_movies_when_the_filter_does_not_match_any_movie() {
            // Arrange
            Genre genre = Genre.HORROR;

            // Act
            MovieFilterService.filterMoviesByGenre(genre, filteredMovies);

            // Assert
            Assertions.assertEquals(0, filteredMovies.size());
        }

        @Test
        void Filters_movies_when_there_is_another_filter_set() {
            // Arrange
            Predicate<? super Movie> existingPredicate = movie -> movie.getDescription().contains("story");
            filteredMovies.setPredicate(existingPredicate);

            Genre genre = Genre.FANTASY;

            // Act
            MovieFilterService.filterMoviesByGenre(genre, filteredMovies);

            // Assert
            Assertions.assertEquals(1, filteredMovies.size());
        }
    }
}