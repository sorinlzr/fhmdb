package at.ac.fhcampuswien.fhmdb.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie implements Comparable<Movie> {
    private String id;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> writers;
    private List<String> mainCast;
    private double rating;
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie() {
        this.genres = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.writers = new ArrayList<>();
        this.mainCast = new ArrayList<>();
    }

    public Movie(String id, String title, String description) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int compareTo(@NotNull Movie movie) {
        return this.title.compareTo(movie.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (releaseYear != movie.releaseYear) return false;
        if (lengthInMinutes != movie.lengthInMinutes) return false;
        if (Double.compare(movie.rating, rating) != 0) return false;
        if (!id.equals(movie.id)) return false;
        if (!Objects.equals(imgUrl, movie.imgUrl)) return false;
        if (!Objects.equals(directors, movie.directors)) return false;
        if (!Objects.equals(writers, movie.writers)) return false;
        if (!Objects.equals(mainCast, movie.mainCast)) return false;
        if (!Objects.equals(title, movie.title)) return false;
        if (!Objects.equals(description, movie.description)) return false;
        return Objects.equals(genres, movie.genres);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + releaseYear;
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + lengthInMinutes;
        result = 31 * result + (directors != null ? directors.hashCode() : 0);
        result = 31 * result + (writers != null ? writers.hashCode() : 0);
        result = 31 * result + (mainCast != null ? mainCast.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        return result;
    }
}
