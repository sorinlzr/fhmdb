package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Nested
    public class CompareTo {
        static Movie movie1;
        static Movie movie2;
        static Movie movie3;
        static Movie movie4;
        static Movie movie5;
        static Movie movie6;

        @BeforeAll
        public static void beforeAll() {
            movie1 = new Movie("Die Hard", "A movie about a hard death.");
            movie2 = new Movie("Die Easy", "A movie about an easy death.");
            movie3 = new Movie("A", "An A class movie!");
            movie4 = new Movie("Z", "A Z class movie!");
            movie5 = new Movie("", "");
            movie6 = new Movie("Die Hard", "A movie about a hard death.");
        }

        @Test
        public void Movie_A_is_smaller_than_Movie_B_if_the_title_is_alphabetically_first() {
            assertTrue(movie1.compareTo(movie4) < 0);

            assertTrue(movie2.compareTo(movie1) < 0);
            assertTrue(movie2.compareTo(movie4) < 0);

            assertTrue(movie3.compareTo(movie1) < 0);
            assertTrue(movie3.compareTo(movie2) < 0);
            assertTrue(movie3.compareTo(movie4) < 0);

            assertTrue(movie5.compareTo(movie1) < 0);
            assertTrue(movie5.compareTo(movie2) < 0);
            assertTrue(movie5.compareTo(movie3) < 0);
            assertTrue(movie5.compareTo(movie4) < 0);
        }

        @Test
        public void Movie_A_is_greater_than_Movie_B_if_the_title_is_alphabetically_last() {
            assertTrue(movie4.compareTo(movie1) > 0);

            assertTrue(movie1.compareTo(movie2) > 0);
            assertTrue(movie4.compareTo(movie2) > 0);

            assertTrue(movie1.compareTo(movie3) > 0);
            assertTrue(movie2.compareTo(movie3) > 0);
            assertTrue(movie4.compareTo(movie3) > 0);

            assertTrue(movie1.compareTo(movie5) > 0);
            assertTrue(movie2.compareTo(movie5) > 0);
            assertTrue(movie3.compareTo(movie5) > 0);
            assertTrue(movie4.compareTo(movie5) > 0);
        }

        @Test
        public void Movie_A_is_at_the_same_position_as_Movie_B_if_the_title_is_alphabetically_the_same() {
            assertEquals(0, movie1.compareTo(movie6));
            assertEquals(0, movie6.compareTo(movie1));
        }

        @Test
        public void throws_an_exception_if_Movie_B_is_null() {
            assertThrows(IllegalArgumentException.class, () -> movie1.compareTo(null));
        }
    }

    @Nested
    public class InitializeMovies {
        @Test
        public void returns_a_list_of_movies_with_more_than_0_movies() {
            List<Movie> movies;

            movies = Movie.initializeMovies();

            assertTrue(movies.size() > 0);
        }

        @Test
        public void contains_a_movie_with_all_properties_filled_out() throws NoSuchFieldException {
            List<Movie> movies;
            List<Genre> genresValues = new ArrayList<>();

            Field title = Movie.class.getDeclaredField("title");
            Field description = Movie.class.getDeclaredField("description");
            Field genres = Movie.class.getDeclaredField("genres");

            movies = Movie.initializeMovies();
            genresValues.add(Genre.ALL);

            title.setAccessible(true);
            description.setAccessible(true);
            genres.setAccessible(true);

            assertTrue(movies.stream().anyMatch(movie -> {
                try {
                    return title.get(movie) != null && !title.get(movie).toString().isBlank()
                            && description.get(movie) != null && !description.get(movie).toString().isBlank()
                            && genres.get(movie) != null && !genres.get(movie).equals(genresValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            }));
        }
    }
}
