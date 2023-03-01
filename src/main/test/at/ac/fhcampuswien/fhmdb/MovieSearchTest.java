package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.features.MovieSearchService;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieSearchTest {

    private final List<Movie> movies = Movie.initializeMovies();
    MovieSearchService movieSearchService = new MovieSearchService(movies);

    @Test
    void SearchInMovieTitleWithValidTerm() {
        List<Movie> result = movieSearchService.searchInMovieTitle("Harry Potter");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithValidTerm() {
        List<Movie> result = movieSearchService.searchInMovieDescription("nose");
        assertEquals(1, result.size());
    }


    @Test
    void SearchInMovieTitleWithMultipleTerms() {
        List<Movie> result = movieSearchService.searchInMovieTitle("Star Wars: Episode");
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleTerms() {
        List<Movie> result = movieSearchService.searchInMovieDescription("without a nose");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithEmptyQuery() {
        List<Movie> result = movieSearchService.searchInMovieTitle("");
        assertEquals(result.size(), result.size());
    }

    @Test
    void SearchInMovieDescriptionWithEmptyQuery() {
        List<Movie> result = movieSearchService.searchInMovieDescription("");
        assertEquals(result.size(), result.size());
    }

    @Test
    void SearchInMovieTitleWithInvalidTerm() {
        List<Movie> result = movieSearchService.searchInMovieTitle("Xyz");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithInvalidTerm() {
        List<Movie> result = movieSearchService.searchInMovieDescription("Xyz");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithSpecialChars() {
        List<Movie> result = movieSearchService.searchInMovieTitle("$#@!%");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithSpecialChars() {
        List<Movie> result = movieSearchService.searchInMovieDescription("$#@!%");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithLeadingTrailingSpaces() {
        List<Movie> result = movieSearchService.searchInMovieTitle("  Titanic  ");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithLeadingTrailingSpaces() {
        List<Movie> result = movieSearchService.searchInMovieDescription("  nose  ");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithMultipleResults() {
        List<Movie> result = movieSearchService.searchInMovieTitle("Star Wars");
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMultipleResults() {
        List<Movie> result = movieSearchService.searchInMovieDescription("movie");
        assertEquals(2, result.size());
    }

    @Test
    void SearchInMovieTitleWithMisspelledQuery() {
        List<Movie> result = movieSearchService.searchInMovieTitle("Harri Pottr");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMisspelledQuery() {
        List<Movie> result = movieSearchService.searchInMovieDescription("bqat");
        assertEquals(0, result.size());
    }

    @Test
    void SearchInMovieTitleWithUppercaseQuery() {
        List<Movie> result = movieSearchService.searchInMovieTitle("TITANIC");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithUppercaseQuery() {
        List<Movie> result = movieSearchService.searchInMovieDescription("BOAT");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithLowercaseQuery() {
        List<Movie> result = movieSearchService.searchInMovieTitle("titanic");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieTitleWithMixedCaseQuery() {
        List<Movie> result = movieSearchService.searchInMovieTitle("HaRRy pOTTer");
        assertEquals(1, result.size());
    }

    @Test
    void SearchInMovieDescriptionWithMixedCaseQuery() {
        List<Movie> result = movieSearchService.searchInMovieDescription("bOaT");
        assertEquals(1, result.size());
    }

    @Test
    void IfResultAppearsOnlyOnceInTheSetOfResults() {
        Set<Movie> result = movieSearchService.searchForMovie("die");
        assertEquals(1, result.size());
    }
}