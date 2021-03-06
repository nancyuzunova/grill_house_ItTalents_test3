package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static final String DB_HOSTNAME = "192.168.7.50";
    private static final String DB_PORT = "3306";
    private static final String SCHEMA_NAME = "test3_java";
    private static final String USERNAME = "cadet";
    private static final String PASSWORD = "cadet";

    private static DBManager mInstance;

    private Connection connection;

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not loaded! " + e.getMessage());
        }
        connection = createConnection();
    }

    public Connection getConnection() throws SQLException {
        if(connection.isClosed()){
            connection = createConnection();
        }
        return connection;
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" +
                    DB_HOSTNAME + ":" + DB_PORT + "/" + SCHEMA_NAME, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed! " + e.getMessage());
            return null;
        }
    }

    public synchronized static DBManager getInstance() {
        if (mInstance == null) {
            mInstance = new DBManager();
        }
        return mInstance;
    }
}
