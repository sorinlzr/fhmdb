package at.ac.fhcampuswien.fhmdb;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Nested
    public class GenreIsSelected {
        @Test
        public void whenGenreIsSelected_thenFilteredListShouldContainOnlyMoviesWithThatGenre() {
            fail("Not implemented yet");
        }
    }

    @Nested
    public class SearchFieldIsFilled {
        @Test
        public void whenSearchFieldIsFilled_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            fail("Not implemented yet");
        }
    }

    @Nested
    public class GenreIsSelectedAndSearchFieldIsFilled {
        @Test
        public void filteredListShouldContainOnlyMoviesThatMatchSearchCriteriaAndGenre() {
            fail("Not implemented yet");
        }

        @Test
        public void whenGenreIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            fail("Not implemented yet");
        }

        @Test
        public void whenSearchFieldIsReset_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            fail("Not implemented yet");
        }

        @Test
        public void SearchFieldIsResetAndThenGenreIsReset_thenFilteredListShouldContainAllMovies() {
            fail("Not implemented yet");
        }

        @Test
        public void SearchFieldIsResetAndThenGenreIsResetAndThenGenreIsSelectedAgain_thenFilteredListShouldContainOnlyMoviesThatMatchGenre() {
            fail("Not implemented yet");
        }

        @Test
        public void SearchFieldIsResetAndThenGenreIsResetAndThenSearchFieldIsFilledAgain_thenFilteredListShouldContainOnlyMoviesThatMatchSearchCriteria() {
            fail("Not implemented yet");
        }
    }

}