package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.TestBase;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieSearchServiceTest {

    static ObservableList<Movie> observableMovies;
    static FilteredList<Movie> filteredList;

    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();

        observableMovies = FXCollections.observableArrayList();
        observableMovies.addAll(
                new Movie("1","Harry Potter", "Guy without a nose has an unhealthy obsession with a teenager"),
                new Movie("2","Star Wars: Episode VI", "Father reunites with long lost son, wants him to take over the family business"),
                new Movie("3","Star Wars: Episode VII", "Boy runs away from home and joins gang of space pirates, then gets beat up by a girl who collects trash"),
                new Movie("4","Titanic", "So there's this huge boat..."),
                new Movie("5","Die Hard", "A story about a man who can't seem to die"),
                new Movie("6","Tenet", "To be honest, I am still trying to figure out what happened in this movie"),
                new Movie("7","Independence Day", "A movie inspired by true events")
        );

        filteredList = new FilteredList<>(observableMovies);
    }

    @Test
    void SearchInMovieTitleWithValidTerm() {
        MovieSearchService.searchInMovieTitle("Harry Potter", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithValidTerm() {
        MovieSearchService.searchInMovieDescription("nose", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithMultipleTerms() {
        MovieSearchService.searchInMovieTitle("Star Wars: Episode", filteredList);
        assertEquals(2, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleTerms() {
        MovieSearchService.searchInMovieDescription("without a nose", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithEmptyQuery() {
        MovieSearchService.searchInMovieTitle("", filteredList);
        assertEquals(observableMovies.size(), filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithEmptyQuery() {
        MovieSearchService.searchInMovieDescription("", filteredList);
        assertEquals(observableMovies.size(), filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithInvalidTerm() {
        MovieSearchService.searchInMovieTitle("Xyz", filteredList);
        assertEquals(0, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithInvalidTerm() {
        MovieSearchService.searchInMovieDescription("Xyz", filteredList);
        assertEquals(0, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithSpecialChars() {
        MovieSearchService.searchInMovieTitle("$#@!%", filteredList);
        assertEquals(0, filteredList.size());
    }
    @Test
    void SearchInMovieDescriptionWithSpecialChars() {
        MovieSearchService.searchInMovieDescription("$#@!%", filteredList);
        assertEquals(0, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithLeadingTrailingSpaces() {
        MovieSearchService.searchInMovieTitle("  Titanic  ", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithLeadingTrailingSpaces() {
        MovieSearchService.searchInMovieDescription("  nose  ", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithMultipleResults() {
        MovieSearchService.searchInMovieTitle("Star Wars", filteredList);
        assertEquals(2, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleResults() {
        MovieSearchService.searchInMovieDescription("movie", filteredList);
        assertEquals(2, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithMisspelledQuery() {
        MovieSearchService.searchInMovieTitle("Harri Pottr", filteredList);
        assertEquals(0, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithMisspelledQuery() {
        MovieSearchService.searchInMovieDescription("bqat", filteredList);
        assertEquals(0, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithUppercaseQuery() {
        MovieSearchService.searchInMovieTitle("TITANIC", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithUppercaseQuery() {
        MovieSearchService.searchInMovieDescription("BOAT", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithLowercaseQuery() {
        MovieSearchService.searchInMovieTitle("titanic", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieTitleWithMixedCaseQuery() {
        MovieSearchService.searchInMovieTitle("HaRRy pOTTer", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void SearchInMovieDescriptionWithMixedCaseQuery() {
        MovieSearchService.searchInMovieDescription("bOaT", filteredList);
        assertEquals(1, filteredList.size());
    }

    @Test
    void IfResultAppearsOnlyOnceInTheSetOfResults() {
        MovieSearchService.searchInMovieTitleAndInMovieDescription("die", filteredList);
        assertEquals(1, filteredList.size());
    }
}