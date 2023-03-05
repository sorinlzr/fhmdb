package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Comparable<Movie>{
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
        this.genres = new ArrayList<>();
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

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie("Die Hard", "A story about a man who can't seem to die");
        movie.addGenre(Genre.ACTION);
        movie.addGenre(Genre.DRAMA);
        movies.add(movie);

        movie = new Movie("Rush Hour", "Two detectives, one throws punches as fast as the other one can talk");
        movie.addGenre(Genre.ACTION);
        movie.addGenre(Genre.COMEDY);
        movies.add(movie);

        movie = new Movie("Titanic", "So there's this huge boat...");
        movie.addGenre(Genre.DRAMA);
        movie.addGenre(Genre.ROMANCE);
        movies.add(movie);

        movie = new Movie("Independence Day", "A movie inspired by true events");
        movie.addGenre(Genre.SCIENCE_FICTION);
        movie.addGenre(Genre.DOCUMENTARY);
        movies.add(movie);

        movie = new Movie("Star Trek", "Beam me up, Scotty! - Ay ay captain!");
        movie.addGenre(Genre.ACTION);
        movie.addGenre(Genre.SCIENCE_FICTION);
        movies.add(movie);

        movie = new Movie("Lord of the rings", "Odd group spends around 9 hours returning jewelry");
        movie.addGenre(Genre.FANTASY);
        movie.addGenre(Genre.ADVENTURE);
        movies.add(movie);

        movie = new Movie("Beauty and the Beast", "Stockholm Syndrome at its finest");
        movie.addGenre(Genre.FANTASY);
        movie.addGenre(Genre.ANIMATION);
        movie.addGenre(Genre.MUSICAL);
        movies.add(movie);

        movie = new Movie("Harry Potter", "Guy without a nose has an unhealthy obsession with a teenager");
        movie.addGenre(Genre.FANTASY);
        movie.addGenre(Genre.DRAMA);
        movies.add(movie);

        movie = new Movie("The Shining", "A family's first Airbnb experience goes horribly wrong");
        movie.addGenre(Genre.HORROR);
        movie.addGenre(Genre.MYSTERY);
        movies.add(movie);

        movie = new Movie("Batman v. Superman", "Paranoid billionaire afraid of a guy in a cape");
        movie.addGenre(Genre.SCIENCE_FICTION);
        movie.addGenre(Genre.ACTION);
        movies.add(movie);

        movie = new Movie("The Dark Knight", "A billionaire devotes his fortune to dress as bat and beat the mentally ill");
        movie.addGenre(Genre.BIOGRAPHY);
        movie.addGenre(Genre.WAR);
        movies.add(movie);

        movie = new Movie("Tenet", "To be honest, I am still trying to figure out what happened in this movie");
        movie.addGenre(Genre.THRILLER);
        movie.addGenre(Genre.ACTION);
        movies.add(movie);

        movie = new Movie("Star Wars: Episode VI", "Father reunites with long lost son, wants him to take over the family business");
        movie.addGenre(Genre.FAMILY);
        movie.addGenre(Genre.SCIENCE_FICTION);
        movies.add(movie);

        movie = new Movie("Star Wars: Episode VII", "Boy runs away from home and joins gang of space pirates, then gets beat up by a girl who collects trash");
        movie.addGenre(Genre.FAMILY);
        movie.addGenre(Genre.SCIENCE_FICTION);
        movies.add(movie);

        movie = new Movie("The Martian", "The universe's most expensive potato farm");
        movie.addGenre(Genre.DOCUMENTARY);
        movie.addGenre(Genre.WESTERN);
        movies.add(movie);

        movie = new Movie("The Matrix", "A group of friends decide to spend less time online and more time in the real world");
        movie.addGenre(Genre.DOCUMENTARY);
        movie.addGenre(Genre.ANIMATION);
        movies.add(movie);

        movie = new Movie("The Hunger Games", "A girl learns not to hate the players and only to hate the game");
        movie.addGenre(Genre.WAR);
        movie.addGenre(Genre.CRIME);
        movies.add(movie);

        return movies;
    }

    public int compareTo(Movie movie) {
        if (movie == null) throw new IllegalArgumentException("Movie cannot be null!");

        return this.title.compareTo(movie.title);
    }
}
