package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.*;

import java.util.List;

public class MovieTest {

    @Nested
    public class CompareTo {
        static Movie movie1;
        static Movie movie2;
        static Movie movie3;
        static Movie movie4;
        static Movie movie5;

        @BeforeAll
        public static void beforeAll() {
            movie1 = new Movie("Die Hard", "A movie about a hard death.");
            movie2 = new Movie("Die Easy", "A movie about an easy death.");
            movie3 = new Movie("A", "An A class movie!");
            movie4 = new Movie("Z", "A Z class movie!");
            movie5 = new Movie("", "");
        }

        @Test
        public void Movie_A_is_smaller_than_Movie_B_if_the_title_is_alphabetically_first() {
            Assertions.assertTrue(movie1.compareTo(movie4) < 0);

            Assertions.assertTrue(movie2.compareTo(movie1) < 0);
            Assertions.assertTrue(movie2.compareTo(movie4) < 0);

            Assertions.assertTrue(movie3.compareTo(movie1) < 0);
            Assertions.assertTrue(movie3.compareTo(movie2) < 0);
            Assertions.assertTrue(movie3.compareTo(movie4) < 0);

            Assertions.assertTrue(movie5.compareTo(movie1) < 0);
            Assertions.assertTrue(movie5.compareTo(movie2) < 0);
            Assertions.assertTrue(movie5.compareTo(movie3) < 0);
            Assertions.assertTrue(movie5.compareTo(movie4) < 0);
        }

        @Test
        public void Movie_A_is_greater_than_Movie_B_if_the_title_is_alphabetically_last() {
            Assertions.assertTrue(movie4.compareTo(movie1) > 0);

            Assertions.assertTrue(movie1.compareTo(movie2) > 0);
            Assertions.assertTrue(movie4.compareTo(movie2) > 0);

            Assertions.assertTrue(movie1.compareTo(movie3) > 0);
            Assertions.assertTrue(movie2.compareTo(movie3) > 0);
            Assertions.assertTrue(movie4.compareTo(movie3) > 0);

            Assertions.assertTrue(movie1.compareTo(movie5) > 0);
            Assertions.assertTrue(movie2.compareTo(movie5) > 0);
            Assertions.assertTrue(movie3.compareTo(movie5) > 0);
            Assertions.assertTrue(movie4.compareTo(movie5) > 0);
        }

        @Test
        public void Movie_A_is_at_the_same_position_as_Movie_B_if_the_title_is_alphabetically_the_same() {
            Assertions.assertEquals(0, movie1.compareTo(movie1));
            Assertions.assertEquals(0, movie2.compareTo(movie2));
            Assertions.assertEquals(0, movie3.compareTo(movie3));
            Assertions.assertEquals(0, movie4.compareTo(movie4));
            Assertions.assertEquals(0, movie5.compareTo(movie5));
        }

        @Test
        public void throws_an_exception_if_Movie_B_is_null() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> movie1.compareTo(null));
        }
    }

    @Nested
    public class InitializeMovies {
        @Test
        public void returns_a_list_of_movies_with_more_than_0_movies() {
            List<Movie> movies;

            movies = Movie.initializeMovies();

            Assertions.assertTrue(movies.size() > 0);
        }
    }
}
