package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import at.ac.fhcampuswien.fhmdb.models.MovieAPIRequestBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieAPIService {
    private static final String BASE = "https://prog2.fh-campuswien.ac.at/movies";
    public static final String API_FETCH_ERROR = "Error while fetching movies from API";

    private MovieAPIService() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Movie> getMovies() throws MovieApiException {
        String url = new MovieAPIRequestBuilder(BASE).build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .method("GET", null)
                .build();

        return makeMovieRequest(request);
    }

    public static List<Movie> getMoviesBy(String query, String genre, String releaseYear, String ratingFrom) throws MovieApiException {
        String url = new MovieAPIRequestBuilder(BASE)
                .query(query)
                .genre(genre)
                .releaseYear(releaseYear)
                .ratingFrom(ratingFrom)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .method("GET", null)
                .build();

        return makeMovieRequest(request);
    }

    private static List<Movie> makeMovieRequest(Request request) throws MovieApiException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                ObjectMapper mapper = new ObjectMapper();
                Movie[] movies = mapper.readValue(response.body().string(), Movie[].class);

                return List.of(movies);
            }

            return new ArrayList<>();
        } catch (IOException e) {
            throw new MovieApiException(API_FETCH_ERROR, e);
        }
    }
}
