package at.ac.fhcampuswien.fhmdb.database;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:h2:file:./db/fhmdb";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private ConnectionSource connectionSource;
    private Dao<WatchlistEntity, Long> dao;

    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";

    private static Database instance;

    public Database() throws DatabaseException {
        createConnectionSource();
        createTables();
    }

    // synchronized - to make the method thread-safe
    public static synchronized Database getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void createConnectionSource() throws DatabaseException{
        try {
            connectionSource = new JdbcConnectionSource(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    private void createTables() throws DatabaseException{
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    public Dao<WatchlistEntity, Long> getWatchlistDao() throws DatabaseException {
        if(dao != null){
            return dao;
        } else {
            try {
                dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
                return dao;
            } catch (SQLException e) {
                throw new DatabaseException(CONNECTION_ERROR_MESSAGE);
            }
        }
    }
}
