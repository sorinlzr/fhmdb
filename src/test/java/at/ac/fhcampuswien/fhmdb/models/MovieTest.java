package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Nested
    class CompareTo {
        static Movie movie1;
        static Movie movie2;
        static Movie movie3;
        static Movie movie4;
        static Movie movie5;
        static Movie movie6;

        @BeforeAll
        static void beforeAll() {
            movie1 = new Movie("1","Die Hard", "A movie about a hard death.");
            movie2 = new Movie("2","Die Easy", "A movie about an easy death.");
            movie3 = new Movie("3","A", "An A class movie!");
            movie4 = new Movie("4","Z", "A Z class movie!");
            movie5 = new Movie("5","", "");
            movie6 = new Movie("6","Die Hard", "A movie about a hard death.");
        }

        @Test
        void Movie_A_is_smaller_than_Movie_B_if_the_title_is_alphabetically_first() {
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
        void Movie_A_is_greater_than_Movie_B_if_the_title_is_alphabetically_last() {
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
        void Movie_A_is_at_the_same_position_as_Movie_B_if_the_title_is_alphabetically_the_same() {
            assertEquals(0, movie1.compareTo(movie6));
            assertEquals(0, movie6.compareTo(movie1));
        }
    }
}
