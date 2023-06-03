package at.ac.fhcampuswien.fhmdb.dao;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.subscription.EventType;
import at.ac.fhcampuswien.fhmdb.subscription.Observable;
import at.ac.fhcampuswien.fhmdb.subscription.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchlistRepository implements Observable {
    private final Dao<WatchlistEntity, Long> dao;
    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";
    private static final String MOVIE_ALREADY_IN_THE_WATCHLIST = "Selected movie is already in the watchlist";
    private static WatchlistRepository instance = null;

    private final Map<EventType, List<Observer>> listeners = new HashMap<>();

    private WatchlistRepository() throws DatabaseException {
        this.dao = Database.getInstance().getWatchlistDao();
    }

    public static WatchlistRepository getInstance() throws DatabaseException {
        if(instance == null){
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws DatabaseException {
        try {
            String apiId = movie.getApiId();
            if (dao != null) {
                DeleteBuilder<WatchlistEntity, Long> deleteBuilder = dao.deleteBuilder();
                if (deleteBuilder != null) {
                    deleteBuilder.where().eq("apiId", apiId);
                    dao.delete(deleteBuilder.prepare());
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    public List<WatchlistEntity> getAll() throws DatabaseException {
        try {
            if (dao != null)
                return dao.queryForAll();
            else
                return Collections.emptyList();
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    public void addToWatchlist(WatchlistEntity movie) throws DatabaseException {
        try {
            if (dao != null) {
                if (dao.queryForEq("apiId", movie.getApiId()).size() < 1) {
                    dao.create(movie);
                } else throw new DatabaseException(MOVIE_ALREADY_IN_THE_WATCHLIST);
            }
        } catch (SQLException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    public void subscribe(EventType eventType, Observer listener) {
        List<Observer> users = listeners.computeIfAbsent(eventType, k -> new java.util.ArrayList<>());
        users.add(listener);
    }

    public void notify(EventType eventType) {
        List<Observer> users = listeners.get(eventType);
        for (Observer listener : users) {
            listener.update(eventType);
        }
    }
}
