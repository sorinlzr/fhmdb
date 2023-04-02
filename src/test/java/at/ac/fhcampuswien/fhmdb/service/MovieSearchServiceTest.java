package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.TestBase;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieSearchServiceTest {

    static ObservableList<Movie> observableMovies;
    static FilteredList<Movie> filteredMovies;

    @BeforeAll
    static void beforeAll() {
        TestBase.setUpJavaFX();

        observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(
                new Movie("1", "Harry Potter", "Guy without a nose has an unhealthy obsession with a teenager"),
                new Movie("2", "Star Wars: Episode VI", "Father reunites with long lost son, wants him to take over the family business"),
                new Movie("3", "Star Wars: Episode VII", "Boy runs away from home and joins gang of space pirates, then gets beat up by a girl who collects trash"),
                new Movie("4", "Titanic", "So there's this huge boat..."),
                new Movie("5", "Die Hard", "A story about a man who can't seem to die"),
                new Movie("6", "Tenet", "To be honest, I am still trying to figure out what happened in this movie"),
                new Movie("7", "Independence Day", "A movie inspired by true events")
        );

        filteredMovies = new FilteredList<>(observableMovies);
    }

    @BeforeEach
    void beforeEach() {
        filteredMovies.setPredicate(null);
    }

    @Nested
    class SearchInMovieTitle {
        //TODO test with and without existing predicate.
        @Test
        void SearchInMovieTitleWithValidTerm() {
            MovieSearchService.searchInMovieTitle("Harry Potter", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithMultipleTerms() {
            MovieSearchService.searchInMovieTitle("Star Wars: Episode", filteredMovies);
            assertEquals(2, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithEmptyQuery() {
            MovieSearchService.searchInMovieTitle("", filteredMovies);
            assertEquals(observableMovies.size(), filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithInvalidTerm() {
            MovieSearchService.searchInMovieTitle("Xyz", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithSpecialChars() {
            MovieSearchService.searchInMovieTitle("$#@!%", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithLeadingTrailingSpaces() {
            MovieSearchService.searchInMovieTitle("  Titanic  ", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithMultipleResults() {
            MovieSearchService.searchInMovieTitle("Star Wars", filteredMovies);
            assertEquals(2, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithMisspelledQuery() {
            MovieSearchService.searchInMovieTitle("Harri Pottr", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithUppercaseQuery() {
            MovieSearchService.searchInMovieTitle("TITANIC", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithLowercaseQuery() {
            MovieSearchService.searchInMovieTitle("titanic", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieTitleWithMixedCaseQuery() {
            MovieSearchService.searchInMovieTitle("HaRRy pOTTer", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void IfResultAppearsOnlyOnceInTheSetOfResults() {
            MovieSearchService.searchInMovieTitleAndInMovieDescription("die", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }
    }

    @Nested
    class SearchInMovieDescription {
        //TODO test with and without existing predicate.
        @Test
        void SearchInMovieDescriptionWithValidTerm() {
            MovieSearchService.searchInMovieDescription("nose", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithMultipleTerms() {
            MovieSearchService.searchInMovieDescription("without a nose", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithEmptyQuery() {
            MovieSearchService.searchInMovieDescription("", filteredMovies);
            assertEquals(observableMovies.size(), filteredMovies.size());
        }


        @Test
        void SearchInMovieDescriptionWithInvalidTerm() {
            MovieSearchService.searchInMovieDescription("Xyz", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }


        @Test
        void SearchInMovieDescriptionWithSpecialChars() {
            MovieSearchService.searchInMovieDescription("$#@!%", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithLeadingTrailingSpaces() {
            MovieSearchService.searchInMovieDescription("  nose  ", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithMultipleResults() {
            MovieSearchService.searchInMovieDescription("movie", filteredMovies);
            assertEquals(2, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithMisspelledQuery() {
            MovieSearchService.searchInMovieDescription("bqat", filteredMovies);
            assertEquals(0, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithUppercaseQuery() {
            MovieSearchService.searchInMovieDescription("BOAT", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }

        @Test
        void SearchInMovieDescriptionWithMixedCaseQuery() {
            MovieSearchService.searchInMovieDescription("bOaT", filteredMovies);
            assertEquals(1, filteredMovies.size());
        }
    }

    @Nested
    class SearchInMovieTitleAndInMovieDescription{
        //TODO implement tests for searchInMovieTitleAndInMovieDescription
        //TODO test with and without existing predicate.
        @Test
        void ImplementSomeTestsHere() {
            throw new UnsupportedOperationException("Implement some tests here");
        }
    }
}