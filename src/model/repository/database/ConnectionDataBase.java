package src.model.repository.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {

    private static final String URL = "jdbc:mysql://localhost/gesTADS?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection currentConnection = null;

    public static Connection getCurrentConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");

        if(currentConnection == null){
            currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return currentConnection;
    }

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}