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
    private static final String DB_URL = "jdbc:h2:mem:fhmdb;DB_CLOSE_DELAY=-1"; // For in-memory database
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private ConnectionSource connectionSource;
    private Dao<WatchlistEntity, Long> dao;

    private static final String CONNECTION_ERROR_MESSAGE = "Failed to create a connection to the database";
    private static final String CONNECTION_SOURCE_ERROR = "Connection Source is null";
    private static final String CONNECTION_DAO_ERROR = "Database Access Object is null";

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
            // Show exception dialog
        }
    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    public ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            throw new NullPointerException(CONNECTION_SOURCE_ERROR);
            // Show exception dialog
        }
        return connectionSource;
    }

    private void createTables() throws DatabaseException{
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
            dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DatabaseException(CONNECTION_ERROR_MESSAGE, e);
            // Show exception dialog
        }
    }

    public Dao<WatchlistEntity, Long> getWatchlistDao() {
        if(dao != null){
            return dao;
        }
        else throw new NullPointerException(CONNECTION_DAO_ERROR);
    }

    // Left it open bc I weren't sure how to implement the closing method of the database iterator:
    // should we implement a method for that or should we build it in with a try-catch-finally block?
    // dao.closeLastIterator();
}
