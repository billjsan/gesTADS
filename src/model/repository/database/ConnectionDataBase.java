package src.model.repository.database;


import src.util.tools.GesLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {

    private static final String TAG = ConnectionDataBase.class.getSimpleName();
    private static final String URL = "jdbc:mysql://localhost/gesTADS?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection currentConnection = null;

    public static Connection getCurrentConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(currentConnection == null)
                currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {

            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "erro de recurso solicitado por um cliente não foi encontrado no servidor.: " + e.getMessage());


        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "erro de inserção de SQL: " + e.getMessage());
        }

        return currentConnection;
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "erro de inserção de SQL: " + e.getMessage());
        }

        return null;
    }

}
