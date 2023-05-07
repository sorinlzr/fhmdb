package at.ac.fhcampuswien.fhmdb.dao;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class WatchlistRepository {
    private final Dao<WatchlistEntity, Long> dao;
    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";
    private static final String MOVIE_ALREADY_IN_THE_WATCHLIST = "Selected movie is already in the watchlist";

    public WatchlistRepository(Dao<WatchlistEntity, Long> dao) {
        this.dao = dao;
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws DatabaseException {
        try {
            String apiId = movie.getApiId();
            if(dao != null){
                DeleteBuilder<WatchlistEntity, Long> deleteBuilder = dao.deleteBuilder();
                if(deleteBuilder != null){
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
            if (dao != null)
            {
                if(dao.queryForEq("apiId", movie.getApiId()).size() < 1) {
                    dao.create(movie);
                }
                else throw new DatabaseException(MOVIE_ALREADY_IN_THE_WATCHLIST);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }
}
