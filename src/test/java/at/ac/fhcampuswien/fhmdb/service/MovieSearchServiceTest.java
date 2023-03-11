package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.TestBase;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieSearchServiceTest {

    static List<Movie> movies;


    @BeforeAll
    public static void beforeAll() {
        TestBase.setUpJavaFX();
        movies = List.of(
                new Movie("Harry Potter", "Guy without a nose has an unhealthy obsession with a teenager"),
                new Movie("Star Wars: Episode VI", "Father reunites with long lost son, wants him to take over the family business"),
                new Movie("Star Wars: Episode VII", "Boy runs away from home and joins gang of space pirates, then gets beat up by a girl who collects trash"),
                new Movie("Titanic", "So there's this huge boat..."),
                new Movie("Die Hard", "A story about a man who can't seem to die"),
                new Movie("Tenet", "To be honest, I am still trying to figure out what happened in this movie"),
                new Movie("Independence Day", "A movie inspired by true events")
        );
    }

    @Test
    void SearchInMovieTitleWithValidTerm() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("Harry Potter", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithValidTerm() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("nose", movies);
        assertEquals(1, result.size());
    }


    @Test
    void SearchInMovieTitleWithMultipleTerms() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("Star Wars: Episode", movies);
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleTerms() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("without a nose", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithEmptyQuery() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("", movies);
        assertEquals(movies.size(), result.size());
    }

    @Test
    void SearchInMovieDescriptionWithEmptyQuery() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("", movies);
        assertEquals(movies.size(), result.size());
    }

    @Test
    void SearchInMovieTitleWithInvalidTerm() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("Xyz", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithInvalidTerm() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("Xyz", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithSpecialChars() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("$#@!%", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithSpecialChars() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("$#@!%", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithLeadingTrailingSpaces() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("  Titanic  ", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithLeadingTrailingSpaces() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("  nose  ", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithMultipleResults() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("Star Wars", movies);
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleResults() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("movie", movies);
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieTitleWithMisspelledQuery() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("Harri Pottr", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMisspelledQuery() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("bqat", movies);
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithUppercaseQuery() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("TITANIC", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithUppercaseQuery() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("BOAT", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithLowercaseQuery() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("titanic", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithMixedCaseQuery() {
        List<Movie> result = MovieSearchService.searchInMovieTitle("HaRRy pOTTer", movies);
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMixedCaseQuery() {
        List<Movie> result = MovieSearchService.searchInMovieDescription("bOaT", movies);
        assertEquals(1, result.size());
    }

    @Test
    void IfResultAppearsOnlyOnceInTheSetOfResults() {
        Set<Movie> result = MovieSearchService.searchForMovie("die", movies);
        assertEquals(1, result.size());
    }
}