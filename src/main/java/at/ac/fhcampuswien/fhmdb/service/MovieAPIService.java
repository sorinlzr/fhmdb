package at.ac.fhcampuswien.fhmdb.service;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieAPIService {

    private static final String API = "https://prog2.fh-campuswien.ac.at/";

    public static List<Movie> getMovies() throws IOException {
        Request request = new Request.Builder()
                .url(API.concat("movies"))
                .header("User-Agent", "http.agent")
                .method("GET", null)
                .build();

        return makeMovieRequest(request);
    }

    public static List<Movie> getMoviesBy(String text, String genre, String releaseYear) throws IOException {
        Request request = new Request.Builder()
                .url(API.concat("movies?query=").concat(text)
                        .concat("&genre=").concat(genre)
                        .concat("&releaseYear=").concat(releaseYear))
                .header("User-Agent", "http.agent")
                .method("GET", null)
                .build();

        return makeMovieRequest(request);
    }

    private static List<Movie> makeMovieRequest(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Response response = client.newCall(request).execute();

        if (response.body() != null){
            ObjectMapper mapper = new ObjectMapper();
            Movie[] movies = mapper.readValue(response.body().string(), Movie[].class);

            response.close();

            return List.of(movies);
        }

        response.close();

        return new ArrayList<>();
    }
}
